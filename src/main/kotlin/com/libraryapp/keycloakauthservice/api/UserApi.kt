package com.libraryapp.keycloakauthservice.api

import com.libraryapp.keycloakauthservice.service.UserService
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserApi(val userService: UserService) {

    @GetMapping
    suspend fun getUsers(
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) idpAlias: String?,
        @RequestParam(required = false) idpUserId: String?,
        @RequestParam(required = false) max: Int?,
        @RequestParam(required = false) username: String?
    ): List<UserRepresentation> {
        return userService.getUsers(
            email,
            idpAlias,
            idpUserId,
            max,
            username
        )
    }

    @GetMapping("/{id}")
    suspend fun getUser(@PathVariable id: String): ResponseEntity<UserRepresentation> {
        return ResponseEntity.ok(userService.findUserById(id))
    }

    @DeleteMapping("/{id}")
    suspend fun deleteUser(@PathVariable id: String): ResponseEntity<Unit> {
        userService.deleteUser(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build<Unit>()
    }

    @PutMapping("/reset-password-email")
    suspend fun forgotPassword(@RequestParam username: String): ResponseEntity<Unit> {
        userService.forgotPassword(username)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build<Unit>()
    }

    @PutMapping("/{id}/send-verify-email/")
    suspend fun sendVerifyEmail(@PathVariable id: String): ResponseEntity<Unit> {
        userService.sendVerificationEmail(id)
        return ResponseEntity.ok().build<Unit>()
    }
}
