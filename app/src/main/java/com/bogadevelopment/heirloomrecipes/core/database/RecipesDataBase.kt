package com.bogadevelopment.heirloomrecipes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogadevelopment.heirloomrecipes.core.database.dao.RecipeDao
import com.bogadevelopment.heirloomrecipes.core.database.dao.ProfileDao
import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileEntity
import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

@Database(entities = [ProfileEntity::class, RecipesEntity::class], version = 1, exportSchema = false)
abstract class RecipesDataBase : RoomDatabase(){
    abstract fun getProfileDao() : ProfileDao
    abstract fun getRecipeDao() : RecipeDao
}