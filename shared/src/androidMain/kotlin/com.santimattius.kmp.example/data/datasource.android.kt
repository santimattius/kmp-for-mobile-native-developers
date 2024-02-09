package com.santimattius.kmp.example.data

actual fun provideGameDataSource(): GameRemoteDataSources {
    return AndroidGameRemoteDataSources()
}