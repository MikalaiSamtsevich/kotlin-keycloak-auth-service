package com.libraryapp.keycloakauthservice.api

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestApi {
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user")
    fun forUser(): String {
        return "Im user"
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin")
    fun forAdmin(): String {
        return "Im admin"
    }
}