package nl.torquelink.data.network.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import nl.torquelink.shared.filters.Filters
import nl.torquelink.shared.serializers.FiltersSerializer

val httpClient = HttpClient {
    install(Resources)
    install(ContentNegotiation){
        json(
            Json {
                serializersModule = SerializersModule {
                    contextual(Filters::class, FiltersSerializer)
                }
            }
        )
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 15000L
        connectTimeoutMillis = 15000L
        socketTimeoutMillis = 15000L
    }

    defaultRequest {
//        url("http://torquelink.nl")
        url("https://dev.torquelink.nl")
        accept(ContentType.Application.Json)
        contentType(ContentType.Application.Json)
    }
}