package com.example.newsapp.di

import android.app.Application
import com.example.newsapp.data.manager.LocalUserManagerImpl
import com.example.newsapp.domain.manager.LocalUserManager
import com.example.newsapp.domain.manager.useCases.AppEntryUseCases
import com.example.newsapp.domain.manager.useCases.ReadAppEntry
import com.example.newsapp.domain.manager.useCases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//this will handle dependency injection for the whole application

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application // this holds the application's context
    ): LocalUserManager {
        return LocalUserManagerImpl(application)//passed here o injected here cause the dataStore uses context
    }
    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ): AppEntryUseCases {//this injects the use cases with the localUserManager
        return AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager)
        )
    }



}