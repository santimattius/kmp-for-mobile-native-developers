package com.santimattius.kmp.example.data

import platform.Foundation.NSData
import platform.Foundation.NSLog
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.dataTaskWithURL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class URLSessionClient {

    suspend fun fetch(urlString: String): String {
        val url = NSURL(string = urlString)
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
}