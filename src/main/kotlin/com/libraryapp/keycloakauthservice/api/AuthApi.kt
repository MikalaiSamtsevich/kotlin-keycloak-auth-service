package com.libraryapp.keycloakauthservice.api

import com.libraryapp.keycloakauthservice.model.NewUserRecord
import com.libraryapp.keycloakauthservice.model.UserLoginRecord
import com.libraryapp.keycloakauthservice.service.UserService
import jakarta.validation.Valid
import org.keycloak.representations.AccessTokenResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthApi(val userService: UserService) {


    @PostMapping("/login")
    suspend fun login(@Valid @RequestBody userLoginRecord: UserLoginRecord): AccessTokenResponse =
        userService.getAccessToken(userLoginRecord)

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createUser(@Valid @RequestBody newUserRecord: NewUserRecord) =
        userService.createUser(newUserRecord)
}