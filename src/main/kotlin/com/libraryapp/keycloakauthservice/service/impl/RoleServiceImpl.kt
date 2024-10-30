package com.libraryapp.keycloakauthservice.service.impl

import com.libraryapp.keycloakauthservice.config.KeycloakConfig
import com.libraryapp.keycloakauthservice.service.RoleService
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Service
class RoleServiceImpl(val keycloakConfig: KeycloakConfig, val keycloakAdminRestApi: KeycloakAdminRestApi) : RoleService {

    val webClient = WebClient.builder()
        .baseUrl(keycloakConfig.serverUrl)
        .build()


    override suspend fun assignRoleToUser(userId: String, roleName: String) {
        val tokenResp = keycloakAdminRestApi.getAccessToken()
        val role = getRoleByName(roleName)
        webClient.post()
            .uri("${keycloakConfig.userEndpoint}/$userId/role-mappings/realm")
            .bodyValue(listOf(role))
            .header("Authorization", "Bearer ${tokenResp.token}")
            .retrieve()
            .onStatus({ status -> status.isError }, { resp ->
                resp.bodyToMono<String>()
                    .flatMap { body -> Mono.error(ResponseStatusException(resp.statusCode(), body)) }
            })
            .awaitBodyOrNull<Unit>()
    }

    override suspend fun getRoleByName(roleName: String): RoleRepresentation {
        val token = keycloakAdminRestApi.getAccessToken()
        return webClient.get()
            .uri("${keycloakConfig.rolesEndpoint}/$roleName")
            .header("Authorization", "Bearer ${token.token}")
            .retrieve()
            .onStatus({ status -> status.isError }, { resp ->
                resp.bodyToMono<String>()
                    .flatMap { body -> Mono.error(ResponseStatusException(resp.statusCode(), body)) }
            })
            .awaitBody<RoleRepresentation>()
    }

}