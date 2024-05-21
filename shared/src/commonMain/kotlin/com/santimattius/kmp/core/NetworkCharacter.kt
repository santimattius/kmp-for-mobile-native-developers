package com.santimattius.kmp.core

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
    @SerialName("image") val image: String,
)
