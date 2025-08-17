package com.bogadevelopment.heirloomrecipes.features.recipes.data

import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

data class RecipesCard (
    val id : Int,
    val profileId : Int,
    val title: String,
    val ingredients : String = "",
    val steps : String = ""
)

fun RecipesEntity.toDomain() = RecipesCard(id = id,profileId = profileId, title = title, ingredients= ingredients, steps = steps)

fun RecipesCard.toEntity() = RecipesEntity(id = id, profileId = profileId, title = title, ingredients = ingredients, steps = steps)