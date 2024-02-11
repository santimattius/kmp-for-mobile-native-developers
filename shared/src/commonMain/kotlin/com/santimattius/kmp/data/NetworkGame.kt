package com.santimattius.kmp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkGame(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("short_description") val shortDescription: String,
    @SerialName("game_url") val gameURL: String,
    @SerialName("publisher") val publisher: String,
    @SerialName("developer") val developer: String,
    @SerialName("release_date") val releaseDate: String,
)