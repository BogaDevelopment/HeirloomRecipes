package com.bogadevelopment.heirloomrecipes.reciepes.data

import app.cash.sqldelight.Query
import com.bogadevelopment.heirloomrecipes.database.Recipes

interface RecipesQueries {
    fun selectAll(): Query<Recipes>
    fun insert(tittle: String)
    fun update(tittle: String, id: Long)
    fun deleteById(id: Long)
    fun selectById(id: Long): Query<Recipes>
}