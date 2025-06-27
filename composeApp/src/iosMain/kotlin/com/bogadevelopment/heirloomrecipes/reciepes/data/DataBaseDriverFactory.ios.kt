package com.bogadevelopment.heirloomrecipes.reciepes.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.bogadevelopment.heirloomrecipes.database.Database

actual class DataBaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = Database.Schema,
            name = "Database.db"
        )
    }
}