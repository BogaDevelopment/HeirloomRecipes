package com.bogadevelopment.heirloomrecipes.features.recipes.data

data class RecipesCard (
    val id : Int,
    val tittle: String,
    val ingredients : String = "",
    val steps : String = ""
)