package com.bogadevelopment.heirloomrecipes.core.di

import com.bogadevelopment.heirloomrecipes.core.database.dao.ProfileDao
import com.bogadevelopment.heirloomrecipes.core.database.dao.RecipeDao
import com.bogadevelopment.heirloomrecipes.features.recipes.data.RecipeRepository
import com.bogadevelopment.heirloomrecipes.features.recipes.domain.RecipeRepositoryImpl
import com.bogadevelopment.heirloomrecipes.features.register.data.ProfileRepository
import com.bogadevelopment.heirloomrecipes.features.register.domain.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        dao: RecipeDao
    ): RecipeRepository = RecipeRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideProfileRepository(dao: ProfileDao): ProfileRepository =
        ProfileRepositoryImpl(dao)
}