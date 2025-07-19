package com.bogadevelopment.heirloomrecipes.core.di

import android.content.Context
import androidx.room.Room
import com.bogadevelopment.heirloomrecipes.core.database.RecipesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "recipes_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, RecipesDataBase::class.java, DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRecipeDao(db: RecipesDataBase) = db.getRecipeDao()


}