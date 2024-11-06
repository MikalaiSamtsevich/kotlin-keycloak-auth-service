package com.libraryapp.keycloakauthservice

import com.libraryapp.keycloakauthservice.UserDataProvider.testUsers
import com.libraryapp.keycloakauthservice.UserDataProvider.testUsersWithDuplicates
import com.libraryapp.keycloakauthservice.model.NewUserRecord
import com.libraryapp.keycloakauthservice.model.UserLoginRecord
import kotlinx.coroutines.*
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodilessEntity
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KeycloakAuthServiceKotlinApplicationTests {

    private val log = LoggerFactory.getLogger(this.javaClass)

    private val webClient = WebClient.builder()
        .baseUrl("http://localhost:8092")
        .build()

    @AfterEach
    fun cleanup(): Unit = runBlocking(Dispatchers.IO) {
        log.info("START CLEANUP")
        getToken("admin", "admin")?.let { token ->
            testUsers.forEach {
                getUserByUsername(it.username)?.let { user ->
                    launch {
                        deleteUser(user.id, token)
                        log.info("deleting user: {}", user.username)
                    }
                }
            }
        }
    }

    // Dispatchers IO has a large thread pool, making it suitable for blocking tasks that don’t heavily use CPU.
    @Test
    fun `should create a new users Dispatchers IO`(): Unit = runBlocking(Dispatchers.IO) {
        supervisorScope {
            testUsers.map { user ->
                launch {
                    registerUser(user)

                    val userToken = getToken(user.username, user.password)
                    assertNotNull(userToken)
                    log.info("${user.username} get token")
                }
            }
        }
    }


    // Dispatchers.Default works for parallelism but may be slower for network tasks due to limited threads.
    @Test
    fun `should create a new users Dispatchers Default`(): Unit = runBlocking(Dispatchers.Default) {
        testUsers.map { user ->
            launch {
                registerUser(user)

                val userToken = getToken(user.username, user.password)
                assertNotNull(userToken)
                log.info("${user.username} get token")
            }
        }
    }


    @Test
    fun `should create a new users using async`(): Unit = runBlocking(Dispatchers.IO) {
        val responses = testUsers.map { user ->
            async {
                val resp = registerUser(user)
                val userToken = getToken(user.username, user.password)
                assertNotNull(userToken)
                log.info("${user.username} get token")
                resp
            }
        }
        assertEquals(testUsers.size, responses.awaitAll().size)
    }


    @Test
    fun `should complete all requests even if some fail`(): Unit = runBlocking(Dispatchers.IO) {
        val exceptionHandler = CoroutineExceptionHandler { _, e ->
            log.error("Error creating user {} ", e.message, e)
        }
        supervisorScope {
            testUsersWithDuplicates.map { user ->
                launch(exceptionHandler) {
                    registerUser(user)
                    val userToken = getToken(user.username, user.password)
                    assertNotNull(userToken)
                    log.info("${user.username} get token")
                }
            }
        }
    }

    @Test
    fun `should atomically create users and retrieve tokens`(): Unit = runBlocking(Dispatchers.IO) {
        val exceptionHandler = CoroutineExceptionHandler { _, e ->
            log.error("Error creating user: {}", e.message)
        }
        supervisorScope {
            testUsers.map { user ->
                launch(exceptionHandler) {
                    try {
                        if (registerUser(user).statusCode == HttpStatus.CREATED) {
                            if (user.username == "alexsmith3") {
                                log.info("Cancelling coroutine for user ${user.username}")
                                cancel()
                            }
                            ensureActive()
                            val token = getToken(user.username, user.password)
                            assertNotNull(token)
                            log.info("Successfully created user ${user.username} and obtained token")
                        } else {
                            log.warn("User registration failed for ${user.username}")
                        }
                    } finally {
                        if (!isActive) {
                            withContext(NonCancellable) {
                                log.info("Attempting to clean up user ${user.username}")
                                deleteUserByUsername(user.username)
                                log.info("Cancelled and cleaned up user ${user.username}")
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun registerUser(user: NewUserRecord): ResponseEntity<Void> {
        return webClient.post()
            .uri("/auth/register")
            .bodyValue(user)
            .retrieve()
            .onStatus({ it.isError }) { resp ->
                resp.bodyToMono<String>()
                    .flatMap { body ->
                        Mono.error(
                            ResponseStatusException(
                                resp.statusCode(),
                                "username ${user.username}, error $body"
                            )
                        )
                    }
            }
            .awaitBodilessEntity()
    }

    private suspend fun deleteUserByUsername(username: String) {
        getUserByUsername(username)?.let { user ->
            log.info("USER ID {}", user.id)
            getToken("admin", "admin")?.let {
                log.info("TOKEN {}", it)
                deleteUser(user.id, it)
            }
        }
    }

    private suspend fun getUserByUsername(username: String): UserRepresentation? {
        log.info("TRY TO GET: {}", username)
        val users = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/users")
                    .queryParam("username", username)
                    .build()
            }
            .retrieve()
            .awaitBody<List<UserRepresentation>>()
        return users.getOrNull(0)
    }

    private suspend fun getToken(username: String, password: String): String? {
        val user = UserLoginRecord(username, password)
        return webClient.post()
            .uri("/auth/login")
            .bodyValue(user)
            .retrieve()
            .bodyToMono(AccessTokenResponse::class.java)
            .awaitSingleOrNull()
            ?.token
    }

    private suspend fun deleteUser(userId: String, token: String) {
        webClient.delete()
            .uri("/users/${userId}")
            .header("Authorization", "Bearer $token")
            .retrieve()
            .awaitBodilessEntity()
    }

}