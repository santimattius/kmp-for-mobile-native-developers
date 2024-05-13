package com.santimattius.kmp.integration.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json

fun testKtorClient(mockClient: MockClient = MockClient()): HttpClient {
    val engine = testKtorEngine(mockClient)
    return HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}

private fun testKtorEngine(interceptor: ResponseInterceptor) = MockEngine { request ->
    val response = interceptor(request)
    respond(
        content = ByteReadChannel(response.content),
        status = response.status,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )
}

class MockClient(
    private val defaultResponse: MockResponse = MockResponse.default(),
) : ResponseInterceptor {

    private var _call: (String) -> MockResponse = { defaultResponse }

    fun setResponse(response: MockResponse) {
        _call = { response }
    }

    override fun invoke(request: HttpRequestData): MockResponse {
        return _call.invoke(request.url.fullPath)
    }
}

fun interface ResponseInterceptor {
    operator fun invoke(request: HttpRequestData): MockResponse
}

data class MockResponse(
    val content: String,
    val status: HttpStatusCode
) {
    companion object {
        fun ok(content: String) = MockResponse(content, HttpStatusCode.OK)
        fun default() = ok("{}")
    }

}