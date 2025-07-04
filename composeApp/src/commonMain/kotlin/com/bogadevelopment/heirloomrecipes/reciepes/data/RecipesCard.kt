package com.bogadevelopment.heirloomrecipes.reciepes.data

import androidx.compose.runtime.mutableStateListOf
import com.bogadevelopment.heirloomrecipes.database.Database

data class RecipesCard (
    val id : Int,
    val tittle: String,
    val ingredients : String = "",
    val steps : String = ""
)

