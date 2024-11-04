package com.libraryapp.keycloakauthservice

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.keycloak.jose.jws.JWSHeader
import org.keycloak.representations.AccessToken
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition
@SpringBootApplication
@RegisterReflectionForBinding(AccessToken::class, JWSHeader::class)
class KeycloakAuthServiceKotlinApplication

fun main(args: Array<String>) {
    runApplication<KeycloakAuthServiceKotlinApplication>(*args)
}
