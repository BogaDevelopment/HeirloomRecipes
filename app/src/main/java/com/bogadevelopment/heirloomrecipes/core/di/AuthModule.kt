package com.bogadevelopment.heirloomrecipes.core.di

import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepository
import com.bogadevelopment.heirloomrecipes.core.auth.AuthRepositoryImpl
import com.bogadevelopment.heirloomrecipes.core.auth.datasource.FirebaseAuthDataSource
import com.bogadevelopment.heirloomrecipes.core.auth.service.FirebaseAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthService(): FirebaseAuthService = FirebaseAuthService()

    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(
        firebaseAuthService: FirebaseAuthService
    ): FirebaseAuthDataSource = FirebaseAuthDataSource(firebaseAuthService)

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuthDataSource: FirebaseAuthDataSource
    ): AuthRepository = AuthRepositoryImpl(firebaseAuthDataSource)
}