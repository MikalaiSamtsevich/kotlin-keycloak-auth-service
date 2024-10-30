package com.libraryapp.keycloakauthservice.api

import com.libraryapp.keycloakauthservice.model.NewUserRecord
import com.libraryapp.keycloakauthservice.model.UserLoginRecord
import com.libraryapp.keycloakauthservice.service.UserService
import jakarta.validation.Valid
import org.keycloak.representations.AccessTokenResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthApi(val userService: UserService) {


    @PostMapping("/login")
    suspend fun login(@RequestBody userLoginRecord: @Valid UserLoginRecord): AccessTokenResponse {
        return userService.getAccessToken(userLoginRecord)
    }

    @PostMapping("/register")
    suspend fun createUser(@RequestBody newUserRecord: @Valid NewUserRecord): ResponseEntity<*> {
        userService.createUser(newUserRecord)
        return ResponseEntity.status(HttpStatus.CREATED).build<Any>()
    }
}