package com.libraryapp.keycloakauthservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfig (
    @Value("\${app.keycloak.user-endpoint}")
    var userEndpoint: String,

    @Value("\${app.keycloak.roles-endpoint}")
    var rolesEndpoint: String,

    @Value("\${app.keycloak.admin-grant-type}")
    var adminGrantType: String,

    @Value("\${app.keycloak.admin-client-id}")
    var adminClientId: String,

    @Value("\${app.keycloak.user-client-id}")
    var userClientId: String,

    @Value("\${app.keycloak.client-secret}")
    var clientSecret: String,

    @Value("\${app.keycloak.server-url}")
    var serverUrl: String,

    @Value("\${app.keycloak.grant-type}")
    var grantType: String,

    @Value("\${app.keycloak.token-uri}")
    var tokenUri: String
)