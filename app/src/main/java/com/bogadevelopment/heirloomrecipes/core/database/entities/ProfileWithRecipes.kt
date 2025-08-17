package com.bogadevelopment.heirloomrecipes.core.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProfileWithRecipes(
    @Embedded val profile: ProfileEntity,
    @Relation(
        parentColumn = "profile_id",
        entityColumn = "ownerProfileId"
    )
    val recipes: List<RecipesEntity>
)