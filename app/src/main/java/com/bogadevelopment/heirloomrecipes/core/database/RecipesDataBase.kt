package com.bogadevelopment.heirloomrecipes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogadevelopment.heirloomrecipes.core.database.dao.ProfileDao
import com.bogadevelopment.heirloomrecipes.core.database.dao.RecipeDao
import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity
import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileEntity

@Database(entities = [ProfileEntity::class, RecipesEntity::class], version = 2, exportSchema = false)
abstract class RecipesDataBase : RoomDatabase(){

    abstract fun getRecipeDao() : RecipeDao
    abstract fun getProfileDao() : ProfileDao
}