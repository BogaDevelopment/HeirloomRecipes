package com.bogadevelopment.heirloomrecipes.core.di

import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepositoryImpl
import com.bogadevelopment.heirloomrecipes.core.database.dao.ProfileDao
import com.bogadevelopment.heirloomrecipes.core.database.dao.RecipeDao
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.features.recipes.domain.RecipeRepositoryImpl
import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileRepository
import com.bogadevelopment.heirloomrecipes.features.register.domain.ProfileRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    // 🔹 RecipeRepository
    @Provides
    @Singleton
    fun provideRecipeRepository(
        dao: RecipeDao
    ): RecipeRepository = RecipeRepositoryImpl(dao)


    // 🔹 ProfileRepository
    @Provides
    @Singleton
    fun provideProfileRepository(dao: ProfileDao): ProfileRepository =
        ProfileRepositoryImpl(dao)

    // 🔹 Firebase Auth
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    // 🔹 AuthRepository
    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

}