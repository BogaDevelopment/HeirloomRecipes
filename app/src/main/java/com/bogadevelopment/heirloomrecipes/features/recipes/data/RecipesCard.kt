package com.bogadevelopment.heirloomrecipes.features.recipes.data

import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

data class RecipesCard (
    val id : Int,
    val title: String,
    val ingredients : String = "",
    val steps : String = ""
)

fun RecipesEntity.toDomain() = RecipesCard(id, title, ingredients, steps)

fun RecipesCard.toEntity() = RecipesEntity(id, title, ingredients, steps)