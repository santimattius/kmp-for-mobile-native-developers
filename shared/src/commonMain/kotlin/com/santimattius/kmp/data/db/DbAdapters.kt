package com.santimattius.kmp.data.db

import app.cash.sqldelight.ColumnAdapter

val booleanColumnAdapter = object : ColumnAdapter<Boolean, Long> {
    override fun decode(databaseValue: Long): Boolean {
        return databaseValue == 1L
    }

    override fun encode(value: Boolean): Long {
        return if (value) 1 else 0
    }
}
