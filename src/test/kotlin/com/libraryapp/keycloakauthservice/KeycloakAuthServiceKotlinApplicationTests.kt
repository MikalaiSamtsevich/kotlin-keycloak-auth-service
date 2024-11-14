package com.libraryapp.keycloakauthservice

import com.libraryapp.keycloakauthservice.config.KeycloakConfig
import com.libraryapp.keycloakauthservice.service.impl.KeycloakAdminRestApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono

@SpringBootTest
class KeycloakAuthServiceKotlinApplicationTests(@Autowired val keycloakConfig: KeycloakConfig) {


    @Test
    fun contextLoads() = runBlocking {
        val kc = KeycloakAdminRestApi(keycloakConfig)
        println(kc.getAccessToken().token)
    }

    @Test
    fun test() {
        val mono = Mono.fromCallable {
            Thread.sleep(2000) // Эмуляция долгой операции
            "Result from Mono"
        }

        // Подписка на результат
        mono.subscribe { result ->
            println("Получен результат: $result")
        }

        // Приложение продолжает выполнение других задач
        println("Ожидание результата...")

        // Задержка для демонстрации
        Thread.sleep(3000) // Даем время для завершения асинхронной задачи
    }
}