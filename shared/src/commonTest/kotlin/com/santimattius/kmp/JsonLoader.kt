package com.santimattius.kmp

import com.goncalossilva.resources.Resource
import kotlinx.serialization.json.Json

object JsonLoader {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun load(file: String): String {
        val loader = Resource("src/commonTest/resources/${file}")
        return loader.readText()
    }

    internal inline fun <reified R : Any> load(file: String) =
        this.load(file).convertToDataClass<R>()

    internal inline fun <reified R : Any> String.convertToDataClass(): R {
        return json.decodeFromString<R>(this)
    }

}