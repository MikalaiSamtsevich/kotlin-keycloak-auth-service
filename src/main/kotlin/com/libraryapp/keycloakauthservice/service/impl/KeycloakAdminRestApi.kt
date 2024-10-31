package com.libraryapp.keycloakauthservice.service.impl

import com.libraryapp.keycloakauthservice.config.KeycloakConfig
import com.libraryapp.keycloakauthservice.util.buildKeycloakFormBody
import org.keycloak.representations.AccessTokenResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class KeycloakAdminRestApi(private val keycloakConfig: KeycloakConfig) {

    val webClient = WebClient.builder()
        .baseUrl(keycloakConfig.serverUrl)
        .build()

    suspend fun getAccessToken(): AccessTokenResponse {
        return try {
            val body = buildKeycloakFormBody(
                keycloakConfig.adminClientId,
                keycloakConfig.clientSecret,
                keycloakConfig.adminGrantType,
            )
            return webClient.post()
                .uri(keycloakConfig.tokenUri)
                .body(body)
                .retrieve()
                .awaitBody<AccessTokenResponse>()
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
            throw e
        }
    }
}
