package com.libraryapp.keycloakauthservice.service

import org.keycloak.representations.idm.RoleRepresentation

interface RoleService {

    suspend fun assignRoleToUser(userId: String, roleName: String)
    suspend fun getRoleByName(roleName: String): RoleRepresentation
}