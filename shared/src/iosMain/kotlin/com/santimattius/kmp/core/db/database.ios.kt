package com.santimattius.kmp.core.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.santimattius.kmp.CharactersDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(CharactersDatabase.Schema, "app_database.db")
    }
}