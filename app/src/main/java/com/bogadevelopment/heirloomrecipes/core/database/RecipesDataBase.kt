package com.bogadevelopment.heirloomrecipes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogadevelopment.heirloomrecipes.core.database.dao.RecipeDao
import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

@Database(entities = [RecipesEntity::class], version = 1, exportSchema = false)
abstract class RecipesDataBase : RoomDatabase(){

    abstract fun getRecipeDao() : RecipeDao
}