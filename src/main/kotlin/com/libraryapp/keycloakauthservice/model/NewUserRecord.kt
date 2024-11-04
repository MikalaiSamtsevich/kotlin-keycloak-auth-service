package com.libraryapp.keycloakauthservice.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class NewUserRecord(
    @field:NotBlank(message = "Username is required")
    @field:Schema(description = "Username of the new user", example = "johndoe")
    val username: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, max = 128, message = "Password must be at least 8 characters")
    @field:Schema(description = "Password for the new user", example = "P@ssw0rd")
    val password: String,

    @field:NotBlank(message = "Password confirmation is required")
    @field:Schema(description = "Confirmation of the password", example = "P@ssw0rd")
    val passwordConfirm: String,

    @field:Email(message = "Email should be valid")
    @field:NotBlank(message = "Email is required")
    @field:Schema(description = "Email address of the new user", example = "johndoe@example.com")
    val email: String,

    @field:NotBlank(message = "First name is required")
    @field:Schema(description = "First name of the new user", example = "John")
    val firstname: String,

    @field:NotBlank(message = "Last name is required")
    @field:Schema(description = "Last name of the new user", example = "Doe")
    val lastname: String
)