package com.santimattius.kmp.data.db

import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.Game
import com.santimattius.kmp.GameDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): GameDatabase {
    val driver = driverFactory.createDriver()
    return GameDatabase(driver, Game.Adapter(booleanColumnAdapter))
}