package com.libraryapp.keycloakauthservice.service.impl

import com.libraryapp.keycloakauthservice.config.KeycloakConfig
import com.libraryapp.keycloakauthservice.model.NewUserRecord
import com.libraryapp.keycloakauthservice.model.UserLoginRecord
import com.libraryapp.keycloakauthservice.service.UserService
import com.libraryapp.keycloakauthservice.util.KeycloakGroup
import com.libraryapp.keycloakauthservice.util.buildKeycloakFormBody
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import java.util.Optional


@Service
class UserServiceImpl(
    private val keycloakConfig: KeycloakConfig,
    private val keycloakAdminRestApi: KeycloakAdminRestApi,
) : UserService {

    val webClient = WebClient.builder()
        .baseUrl(keycloakConfig.serverUrl)
        .build()

    override suspend fun createUser(newUserRecord: NewUserRecord) {
        val tokenResp = keycloakAdminRestApi.getAccessToken()
        val userRepresentation = userDtoToRepresentation(newUserRecord)
        userRepresentation.groups = listOf<String>(KeycloakGroup.CUSTOMER.toString())
        webClient.post()
            .uri(keycloakConfig.userEndpoint)
            .header("Authorization", "Bearer ${tokenResp.token}")
            .bodyValue(userRepresentation)
            .retrieve()
            .onStatus({ status -> status.isError }, { resp ->
                resp.bodyToMono<String>()
                    .flatMap { body -> Mono.error(ResponseStatusException(resp.statusCode(), body)) }
            })
            .awaitBodyOrNull<Unit>()
    }

    override suspend fun findUserById(userId: String): UserRepresentation {
        val tokenResp = keycloakAdminRestApi.getAccessToken()
        return webClient.get()
            .uri("${keycloakConfig.userEndpoint}/$userId")
            .header("Authorization", "Bearer ${tokenResp.token}")
            .retrieve()
            .onStatus({ status -> status.isError }, { resp ->
                resp.bodyToMono<String>()
                    .flatMap { body -> Mono.error(ResponseStatusException(resp.statusCode(), body)) }
            })
            .awaitBody<UserRepresentation>()
    }

    override suspend fun getAccessToken(userLoginRecord: UserLoginRecord): AccessTokenResponse {
        val body = buildKeycloakFormBody(
            keycloakConfig.userClientId,
            keycloakConfig.grantType,
            userLoginRecord.username,
            userLoginRecord.password
        )
        return webClient.post()
            .uri(keycloakConfig.tokenUri)
            .body(body)
            .retrieve()
            .onStatus({ status -> status.isError }, { resp ->
                resp.bodyToMono<String>()
                    .flatMap { body -> Mono.error(ResponseStatusException(resp.statusCode(), body)) }
            })
            .awaitBody<AccessTokenResponse>()
    }

    fun userDtoToRepresentation(newUserRecord: NewUserRecord): UserRepresentation {
        val userRepresentation: UserRepresentation = UserRepresentation().apply {
            isEnabled = true
            username = newUserRecord.username
            email = newUserRecord.email
            firstName = newUserRecord.username
            lastName = newUserRecord.lastname
            createdTimestamp = System.currentTimeMillis()
            isEmailVerified = false
        }

        val credentialRepresentation: CredentialRepresentation = CredentialRepresentation().apply {
            value = newUserRecord.password
            type = CredentialRepresentation.PASSWORD
        }

        userRepresentation.credentials = listOf(credentialRepresentation)
        return userRepresentation
    }


    override suspend fun sendVerificationEmail(userId: String) {
        val tokenResp = keycloakAdminRestApi.getAccessToken()
        return webClient.put()
            .uri("${keycloakConfig.userEndpoint}/$userId/send-verify-email")
            .header("Authorization", "Bearer ${tokenResp.token}")
            .retrieve()
            .onStatus({ status -> status.isError }, { resp ->
                resp.bodyToMono<String>()
                    .flatMap { body -> Mono.error(ResponseStatusException(resp.statusCode(), body)) }
            })
            .awaitBody<Unit>()
    }

    override suspend fun deleteUser(userId: String) {
        val tokenResp = keycloakAdminRestApi.getAccessToken()
        return webClient.delete()
            .uri("${keycloakConfig.userEndpoint}/$userId")
            .header("Authorization", "Bearer ${tokenResp.token}")
            .retrieve()
            .onStatus({ status -> status.isError }, { resp ->
                resp.bodyToMono<String>()
                    .flatMap { body -> Mono.error(ResponseStatusException(resp.statusCode(), body)) }
            })
            .awaitBody<Unit>()
    }

    override suspend fun getUsers(
        email: String?,
        idpAlias: String?,
        idpUserId: String?,
        max: Int?,
        username: String?
    ): List<UserRepresentation> {
        val token = keycloakAdminRestApi.getAccessToken()  // Получаем токен

        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path(keycloakConfig.userEndpoint)
                    .queryParamIfPresent("email", Optional.ofNullable(email))
                    .queryParamIfPresent("idpAlias", Optional.ofNullable(idpAlias))
                    .queryParamIfPresent("idpUserId", Optional.ofNullable(idpUserId))
                    .queryParamIfPresent("max", Optional.ofNullable(max))
                    .queryParamIfPresent("username", Optional.ofNullable(username))
                    .build()
            }
            .header("Authorization", "Bearer ${token.token}")
            .retrieve()
            .awaitBodyOrNull<List<UserRepresentation>>() ?: emptyList()
    }


    override suspend fun forgotPassword(username: String) {
        val token = keycloakAdminRestApi.getAccessToken()  // Получаем токен
        val userId = getUsers(username = username)[0].id
        webClient.put()
            .uri("${keycloakConfig.userEndpoint}/$userId/reset-password-email")
            .header("Authorization", "Bearer ${token.token}")
            .retrieve()
            .awaitBodyOrNull<Unit>()
    }

}
