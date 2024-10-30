package com.libraryapp.keycloakauthservice.config

import com.libraryapp.keycloakauthservice.util.KeycloakJwtAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
class WebSecurityConfig(private val keycloakJwtAuthenticationConverter: KeycloakJwtAuthenticationConverter) {

    @Bean
    fun securityFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        return httpSecurity
            .authorizeExchange { customizer ->
                customizer
                    .pathMatchers("/auth/login").permitAll()
                    .pathMatchers("/auth/register").permitAll()
                    .pathMatchers("/users/reset-password-email").permitAll()
                    .pathMatchers("/users/{id}/send-verification-email").permitAll()
                    .pathMatchers("/keycloak-auth-service-docs/**").permitAll()
                    .pathMatchers(HttpMethod.GET,"/users").permitAll()
                    .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .pathMatchers(HttpMethod.DELETE, "/groups/*").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.PUT, "/groups/*").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.DELETE, "/roles/*").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.PUT, "/roles/*").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.POST, "/roles/{userId}").hasRole("ADMIN")
                    .anyExchange().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(keycloakJwtAuthenticationConverter)
                }
            }
            .csrf { csrf -> csrf.disable() }
            .cors { _ -> corsFilter() }
            .build()
    }

    @Bean
    fun corsFilter(): CorsWebFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("http://localhost:8091")
        config.addAllowedOriginPattern("http://localhost:8091/**")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsWebFilter(source)
    }

}