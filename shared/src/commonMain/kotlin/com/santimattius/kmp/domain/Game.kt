package com.santimattius.kmp.domain

data class Game(
    val id: Long,
    val title: String,
    val thumbnail: String,
    val shortDescription: String,
    val website: String,
    val publisher: String,
    val developer: String,
    val releaseDate: String,
)