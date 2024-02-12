package com.santimattius.kmp.example

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform