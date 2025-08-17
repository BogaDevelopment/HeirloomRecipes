package com.bogadevelopment.heirloomrecipes.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class ProfileEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "profile_id") val profileId: Int = 0,
    @ColumnInfo(name = "firebase_uid") val firebaseUid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "email") val email: String,
)
