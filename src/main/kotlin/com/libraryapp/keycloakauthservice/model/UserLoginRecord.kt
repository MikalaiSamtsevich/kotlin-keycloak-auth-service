package com.libraryapp.keycloakauthservice.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class UserLoginRecord(
    @field:NotBlank(message = "Username is required")
    @field:Schema(description = "Username of the user", example = "johndoe")
    val username: String,

    @field:NotBlank(message = "Password is required")
    @field:Schema(description = "Password of the user (minimum 8 characters)", example = "P@ssw0rd")
    val password: String
)
