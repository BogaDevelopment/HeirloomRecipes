package com.bogadevelopment.heirloomrecipes.features.login.data

import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileEntity

data class ProfileCard(
    val uid : String,
    val name : String,
    val lastName : String,
    val email : String
)

fun ProfileEntity.toDomain() = ProfileCard(
    uid = uid,
    name = name,
    lastName = lastName,
    email = email
)

fun ProfileCard.toEntity() = ProfileEntity(
    uid = uid,
    name = name,
    lastName = lastName,
    email = email
)