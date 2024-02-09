package com.santimattius.kmp.example.data

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import platform.Foundation.NSArray
import platform.Foundation.NSData
import platform.Foundation.NSJSONSerialization
import platform.Foundation.NSLog
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.dataTaskWithURL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class IOSGameRemoteDataSources : GameRemoteDataSources {

    override suspend fun getGames(): Result<GameResponse> = runCatching {
        val jsonString = fetch()
        Json.decodeFromString<GameResponse>(jsonString)
    }.run {
        print(this.toString())
        this
    }

    private suspend fun fetch(): String {
        val url = NSURL(string = baseUrl)
        return suspendCoroutine {
            val task = NSURLSession.sharedSession()
                .dataTaskWithURL(url = url) { data: NSData?, _, _ ->
                    if (data == null) {
                        it.resumeWithException(Throwable())
                    } else {
                        val json = data.asJson()
                        NSLog(json)
                        it.resume(json)
                    }
                }
            task.resume()
        }
    }

    companion object {
        private const val baseUrl = "https://www.freetogame.com/api/games"
    }
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.asJson(): String {
    return (NSJSONSerialization.JSONObjectWithData(this, options = 0u, error = null) as NSArray).toString()
}