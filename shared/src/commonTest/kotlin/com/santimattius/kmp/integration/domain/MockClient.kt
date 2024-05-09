package com.santimattius.kmp.integration.domain

import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json

fun testKtorClient(mockClient: MockClient = MockClient()) = HttpClient(mockClient.engine){
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}
private fun testKtorEngine(interceptor: ResponseInterceptor) = MockEngine { _ ->
    val response = interceptor.intercept()
    respond(
        content = ByteReadChannel(response.content),
        status = response.status,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )
}

class MockClient {
    private val interceptor = mock<ResponseInterceptor>(mode = MockMode.autoUnit)
    val engine = testKtorEngine(interceptor)

    fun intercept(response: MockResponse) {
        every {
            interceptor.intercept()
        } returns response
    }
}

interface ResponseInterceptor {
    fun intercept(): MockResponse
}

data class DefaultMockResponse(
    override val content: String,
    override val status: HttpStatusCode
) : MockResponse

interface MockResponse {
    val content: String
    val status: HttpStatusCode
}