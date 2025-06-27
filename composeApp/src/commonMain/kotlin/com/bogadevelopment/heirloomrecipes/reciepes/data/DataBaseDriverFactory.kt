package com.bogadevelopment.heirloomrecipes.reciepes.data

import app.cash.sqldelight.db.SqlDriver

expect class DataBaseDriverFactory {

    fun createDriver() : SqlDriver
}