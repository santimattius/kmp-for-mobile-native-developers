package com.santimattius.kmp.integration.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.santimattius.kmp.CharactersDatabase

actual fun testDbDriver(): SqlDriver {
    return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        .also {
            CharactersDatabase.Schema.create(it)
        }
}
