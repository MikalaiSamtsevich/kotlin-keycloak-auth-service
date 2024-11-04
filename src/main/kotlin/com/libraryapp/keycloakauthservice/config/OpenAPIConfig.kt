package com.libraryapp.keycloakauthservice.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {
    @Bean
    fun customOpenAPI(
        @Value("\${openapi.service.title}") serviceTitle: String,
        @Value("\${openapi.service.version}") serviceVersion: String,
        @Value("\${openapi.service.url}") url: String
    ): OpenAPI {
        return OpenAPI()
            .servers(listOf<Server>(Server().url(url)))
            .info(
                Info()
                    .title(serviceTitle).version(serviceVersion)
                    .description(
                        ("Keycloak Authentication Service API for managing users, roles, and groups. " +
                                "This service connects to Keycloak via the admin CLI to handle authentication, authorization, " +
                                "and JWT token retrieval for secure API access. "
                                + "No roles are required for user registration or login, while the ADMIN role is required for managing groups, roles, or users.")
                    )
            )
            .addSecurityItem(SecurityRequirement().addList("Bearer Authentication"))
            .components(
                Components()
                    .addSecuritySchemes(
                        "Bearer Authentication",  // Имя схемы безопасности
                        SecurityScheme()
                            .name("Authorization")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
    }
}