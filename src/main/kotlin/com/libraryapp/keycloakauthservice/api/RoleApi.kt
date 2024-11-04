package com.libraryapp.keycloakauthservice.api

import com.libraryapp.keycloakauthservice.service.RoleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roles")
class RoleApi(val roleService: RoleService) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{userId}")
    suspend fun login(@PathVariable userId: String, @RequestParam roleName: String) =
        roleService.assignRoleToUser(userId, roleName)

}