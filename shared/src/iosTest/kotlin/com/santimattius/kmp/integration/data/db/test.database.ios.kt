package com.santimattius.kmp.integration.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import com.santimattius.kmp.CharactersDatabase

actual fun testDbDriver(): SqlDriver {
    return inMemoryDriver(CharactersDatabase.Schema)
}