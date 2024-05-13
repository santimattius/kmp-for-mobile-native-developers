package com.santimattius.kmp.data.db

import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.CharactersDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driver: SqlDriver): CharactersDatabase {
    return CharactersDatabase(driver)
}