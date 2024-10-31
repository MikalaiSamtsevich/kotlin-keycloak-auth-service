package com.libraryapp.keycloakauthservice.util

import lombok.Getter

@Getter
enum class KeycloakEvent(val event: String) {
    UPDATE_PASSWORD("UPDATE_PASSWORD"),
    VERIFY_EMAIL("VERIFY_EMAIL");

    override fun toString(): String {
        return event
    }
}
