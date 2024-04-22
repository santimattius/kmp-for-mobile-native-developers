package com.santimattius.kmp.data.db

import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.Character
import com.santimattius.kmp.CharactersDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): CharactersDatabase {
    val driver = driverFactory.createDriver()
    return CharactersDatabase(driver)
}