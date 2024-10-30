package com.libraryapp.keycloakauthservice.util

import org.springframework.web.reactive.function.BodyInserters

fun buildKeycloakFormBody(
    clientId: String,
    clientSecret: String,
    grantType: String,
    username: String,
    password: String
): BodyInserters.FormInserter<String> {
    return BodyInserters.fromFormData("client_id", clientId)
        .with("client_secret", clientSecret)
        .with("username", username)
        .with("password", password)
        .with("grant_type", grantType)
}

fun buildKeycloakFormBody(
    clientId: String,
    clientSecret: String,
    grantType: String,
): BodyInserters.FormInserter<String> {
    return BodyInserters.fromFormData("client_id", clientId)
        .with("client_secret", clientSecret)
        .with("grant_type", grantType)
}