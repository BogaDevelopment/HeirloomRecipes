package com.bogadevelopment.heirloomrecipes.features.register.data

import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileEntity

data class ProfileData(
    val id : Int? = null,
    val firebaseUid: String,
    val name: String,
    val lastName : String,
    val email : String
        )

fun ProfileData.toEntity() = ProfileEntity(profileId = id ?: 0, firebaseUid = firebaseUid, name = name, lastName = lastName, email = email)

fun ProfileEntity.toDomain() = ProfileData(id = profileId, firebaseUid = firebaseUid, name = name, lastName = lastName, email = email)