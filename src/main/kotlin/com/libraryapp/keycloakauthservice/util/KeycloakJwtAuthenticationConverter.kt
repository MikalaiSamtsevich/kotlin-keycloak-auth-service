package com.libraryapp.keycloakauthservice.util

import lombok.SneakyThrows
import org.keycloak.TokenVerifier
import org.keycloak.representations.AccessToken
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@Component
class KeycloakJwtAuthenticationConverter : Converter<Jwt, Mono<out AbstractAuthenticationToken>> {
    @SneakyThrows
    override fun convert(jwt: Jwt): Mono<out AbstractAuthenticationToken> {
        var authorities: MutableCollection<GrantedAuthority>
        val token = TokenVerifier.create<AccessToken>(jwt.tokenValue, AccessToken::class.java).getToken()
        authorities = token.getRealmAccess().getRoles().stream()
            .map<SimpleGrantedAuthority> { role: String -> SimpleGrantedAuthority("ROLE_$role") }
            .collect(Collectors.toSet())
        return Mono.just<AbstractAuthenticationToken>(
            JwtAuthenticationToken(
                jwt,
                authorities,
                jwt.getClaim<String>(JwtClaimNames.SUB)
            )
        )
    }
}
