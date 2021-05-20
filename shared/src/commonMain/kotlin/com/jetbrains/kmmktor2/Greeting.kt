package com.jetbrains.kmmktor2

import com.github.aakira.napier.Napier
import kotlinx.serialization.Serializable
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

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
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }.also { initLogger() }

    @Throws(Throwable::class)
    suspend fun greeting(): String {
        return "${getHello().random().string}, ${Platform().platform}!"
    }

    private suspend fun getHello(): List<Hello> {
        return httpClient.get("https://gitcdn.link/cdn/KaterinaPetrova/greeting/7d47a42fc8d28820387ac7f4aaf36d69e434adc1/greetings.json")
    }
}