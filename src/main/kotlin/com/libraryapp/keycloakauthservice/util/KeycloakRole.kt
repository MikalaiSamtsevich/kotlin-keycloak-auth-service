package com.libraryapp.keycloakauthservice.util

import lombok.Getter

@Getter
enum class KeycloakRole {
    ADMIN,
    USER;

    private val roleName: String? = null
}