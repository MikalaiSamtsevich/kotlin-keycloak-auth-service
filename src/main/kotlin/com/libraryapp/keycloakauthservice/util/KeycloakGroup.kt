package com.libraryapp.keycloakauthservice.util

enum class KeycloakGroup(val groupName: String) {
    CUSTOMER("CUSTOMER"),
    MANAGER("MANAGER"), ;

    override fun toString(): String {
        return groupName
    }
}
