package com.santimattius.kmp.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.santimattius.kmp.GameDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(GameDatabase.Schema, "game_database.db")
    }
}