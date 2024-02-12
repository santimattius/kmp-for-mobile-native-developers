package com.santimattius.kmp.example.data

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSArray
import platform.Foundation.NSData
import platform.Foundation.NSJSONSerialization

@OptIn(ExperimentalForeignApi::class)
fun NSData.asJson(): String {
    return (NSJSONSerialization.JSONObjectWithData(
        this,
        options = 0u,
        error = null
    ) as NSArray).toString()
}