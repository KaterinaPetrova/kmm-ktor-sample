package com.jetbrains.kmmktor2

import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

@Serializable
data class Hello(
    val string: String,
)
class Greeting {
    private val httpClient = httpClient() {
        install(Logging) {
            level = LogLevel.HEADERS
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(tag = "HTTP Client", message = message)
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }.also { initLogger() }

    @Throws(Throwable::class)
    suspend fun greeting(): String {
        return "${getHello().random().string}, ${Platform().platform}!"
    }

    private suspend fun getHello(): List<Hello> {
        return httpClient.get("https://cdn.jsdelivr.net/gh/KaterinaPetrova/greeting@main/greetings.json").body()
    }
}