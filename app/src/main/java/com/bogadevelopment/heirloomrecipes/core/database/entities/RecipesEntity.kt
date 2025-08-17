package com.bogadevelopment.heirloomrecipes.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (
    tableName = "recipe_table",
    foreignKeys = [
        ForeignKey(
            entity = ProfileEntity::class,
            parentColumns = ["profile_id"],
            childColumns = ["ownerProfileId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id") val id : Int = 0,
    @ColumnInfo(name = "ownerProfileId", index = true) val profileId: Int,
    @ColumnInfo (name = "title")val title: String,
    @ColumnInfo (name = "ingredients")val ingredients : String = "",
    @ColumnInfo (name = "steps") val steps : String = "",

)