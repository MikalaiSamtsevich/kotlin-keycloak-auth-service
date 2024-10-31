package com.libraryapp.keycloakauthservice.service

import com.libraryapp.keycloakauthservice.model.NewUserRecord
import com.libraryapp.keycloakauthservice.model.UserLoginRecord
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.UserRepresentation

interface UserService {
    suspend fun createUser(newUserRecord: NewUserRecord)

    suspend fun getAccessToken(userLoginRecord: UserLoginRecord): AccessTokenResponse

    suspend fun sendVerificationEmail(userId: String)

    suspend fun findUserById(userId: String): UserRepresentation

    suspend fun forgotPassword(username: String)

    suspend fun deleteUser(userId: String)

    suspend fun getUsers(
        email: String? = null,
        idpAlias: String? = null,
        idpUserId: String? = null,
        max: Int? = null,
        username: String? = null
    ): List<UserRepresentation>

}
