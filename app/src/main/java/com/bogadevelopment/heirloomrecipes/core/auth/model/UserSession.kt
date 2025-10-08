package com.bogadevelopment.heirloomrecipes.core.auth.model

data class UserSession(
    val uid: String,
    val email: String?
)