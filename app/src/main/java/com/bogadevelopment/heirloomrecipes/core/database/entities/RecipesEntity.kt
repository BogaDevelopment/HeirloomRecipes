package com.bogadevelopment.heirloomrecipes.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe_table",
    foreignKeys = [
        ForeignKey(
            entity = ProfileEntity::class,
            parentColumns = ["uid"],
            childColumns = ["user_uid"],
            onDelete = ForeignKey.CASCADE // delete recipe if the user is deleted
        )
    ],
    indices = [Index("user_uid")] // mejora performance de consultas por usuario
)
data class RecipesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id") val id : Int = 0,
    @ColumnInfo(name = "user_uid") val userUid: String, // relation with user_uid
    @ColumnInfo (name = "title")val title: String,
    @ColumnInfo (name = "ingredients")val ingredients : String = "",
    @ColumnInfo (name = "steps") val steps : String = ""
)