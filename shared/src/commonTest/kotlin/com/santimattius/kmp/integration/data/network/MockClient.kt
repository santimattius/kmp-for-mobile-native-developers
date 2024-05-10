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

fun testKtorClient(mockClient: MockClient = MockClient()) = HttpClient(mockClient.engine) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

private fun testKtorEngine(interceptor: ResponseInterceptor) = MockEngine { request ->
    val response = interceptor.intercept(request)
    respond(
        content = ByteReadChannel(response.content),
        status = response.status,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )
}

class MockClient(
    private val defaultResponse: MockResponse = DefaultMockResponse(
        status = HttpStatusCode.OK,
        content = ""
    ),
) : ResponseInterceptor {

    private var _call: (String) -> MockResponse = { defaultResponse }
    val engine = testKtorEngine(this)

    fun setResponse(response: MockResponse) {
        _call = { response }
    }

    override fun intercept(request: HttpRequestData): MockResponse {
        return _call.invoke(request.url.fullPath)
    }
}

interface ResponseInterceptor {
    fun intercept(request: HttpRequestData): MockResponse
}

data class DefaultMockResponse(
    override val content: String,
    override val status: HttpStatusCode
) : MockResponse

interface MockResponse {
    val content: String
    val status: HttpStatusCode
}