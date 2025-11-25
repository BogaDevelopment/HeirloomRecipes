package com.bogadevelopment.heirloomrecipes.core.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProfileWithRecipes(

    @Embedded val user: ProfileEntity,

    @Relation(
        parentColumn = "uid",
        entityColumn = "user_uid"
    )
    val recipes: List<RecipesEntity>
)