package com.libraryapp.keycloakauthservice

import com.libraryapp.keycloakauthservice.config.KeycloakConfig
import com.libraryapp.keycloakauthservice.service.impl.KeycloakAdminRestApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KeycloakAuthServiceKotlinApplicationTests(@Autowired val keycloakConfig: KeycloakConfig) {


    @Test
    fun contextLoads() = runBlocking {
        val kc = KeycloakAdminRestApi(keycloakConfig)
        println(kc.getAccessToken().token)
    }
}