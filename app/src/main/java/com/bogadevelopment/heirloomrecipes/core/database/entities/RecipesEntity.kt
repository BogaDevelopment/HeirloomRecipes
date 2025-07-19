package com.bogadevelopment.heirloomrecipes.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "recipe_table")
data class RecipesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id") val id : Int = 0,
    @ColumnInfo (name = "title")val title: String,
    @ColumnInfo (name = "ingredients")val ingredients : String = "",
    @ColumnInfo (name = "steps") val steps : String = ""
)