package com.santimattius.kmp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponse(
    val info: Info,
    val results: List<NetworkCharacter>
)

@Serializable
data class Info(
    val count: Long,
    val pages: Long,
    val next: String,
    val prev: String? = null
)

@Serializable
data class NetworkCharacter(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("origin") val origin: Location,
    @SerialName("location") val location: Location,
    @SerialName("image") val image: String,
    @SerialName("episode") val episode: List<String>,
    @SerialName("url") val url: String,
    @SerialName("created") val created: String
)

@Serializable
data class Location(
    val name: String,
    val url: String
)
