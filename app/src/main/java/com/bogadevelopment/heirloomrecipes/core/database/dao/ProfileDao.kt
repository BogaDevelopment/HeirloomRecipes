package com.bogadevelopment.heirloomrecipes.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileEntity
import com.bogadevelopment.heirloomrecipes.core.database.entities.ProfileWithRecipes

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: ProfileEntity)

    @Query("SELECT * FROM profile_table WHERE uid = :uid")
    suspend fun getUserById(uid: String): ProfileEntity?

    @Transaction
    @Query("SELECT * FROM profile_table WHERE uid = :uid")
    suspend fun getUserWithRecipes(uid: String): ProfileWithRecipes?

    @Delete
    suspend fun deleteUser(user: ProfileEntity)
}