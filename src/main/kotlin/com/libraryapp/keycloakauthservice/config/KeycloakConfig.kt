package com.libraryapp.keycloakauthservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfig {
    @Value("\${app.keycloak.user-endpoint}")
    lateinit var userEndpoint: String

    @Value("\${app.keycloak.roles-endpoint}")
    lateinit var rolesEndpoint: String

    @Value("\${app.keycloak.admin-grant-type}")
    lateinit var adminGrantType: String

    @Value("\${app.keycloak.admin-client-id}")
    lateinit var adminClientId: String

    @Value("\${app.keycloak.user-client-id}")
    lateinit var userClientId: String

    @Value("\${app.keycloak.client-secret}")
    lateinit var clientSecret: String

    @Value("\${app.keycloak.server-url}")
    lateinit var serverUrl: String

    @Value("\${app.keycloak.grant-type}")
    lateinit var grantType: String

    @Value("\${app.keycloak.token-uri}")
    lateinit var tokenUri: String
}