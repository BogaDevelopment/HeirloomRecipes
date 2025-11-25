package com.bogadevelopment.heirloomrecipes.features.recipes.data

import com.bogadevelopment.heirloomrecipes.core.database.entities.RecipesEntity

data class RecipesCard (
    val id : Int,
    val userUid : String,
    val title: String,
    val ingredients : String = "",
    val steps : String = ""
)

fun RecipesEntity.toDomain() = RecipesCard(id = id, userUid = userUid,title = title, ingredients = ingredients, steps = steps)

fun RecipesCard.toEntity() = RecipesEntity(id = id, userUid = userUid, title = title, ingredients = ingredients, steps = steps)