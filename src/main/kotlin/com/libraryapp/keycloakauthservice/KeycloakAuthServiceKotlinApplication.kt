package com.libraryapp.keycloakauthservice

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition
@SpringBootApplication
class KeycloakAuthServiceKotlinApplication

fun main(args: Array<String>) {
    runApplication<KeycloakAuthServiceKotlinApplication>(*args)
}
