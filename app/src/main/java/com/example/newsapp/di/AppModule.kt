package com.example.newsapp.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.local.NewsTypeConverter
import com.example.newsapp.data.manager.LocalUserManagerImpl
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.manager.LocalUserManager
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.useCases.app_entry.AppEntryUseCases
import com.example.newsapp.domain.useCases.app_entry.ReadAppEntry
import com.example.newsapp.domain.useCases.app_entry.SaveAppEntry
import com.example.newsapp.domain.useCases.news.DeleteArticle
import com.example.newsapp.domain.useCases.news.GetNews
import com.example.newsapp.domain.useCases.news.NewsUseCases
import com.example.newsapp.domain.useCases.news.SearchNews
import com.example.newsapp.domain.useCases.news.SelectArticle
import com.example.newsapp.domain.useCases.news.SelectArticles
import com.example.newsapp.domain.useCases.news.UpsertArticle
import com.example.newsapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun providesApi(): NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository{
        return NewsRepositoryImpl(newsApi = newsApi, newsDao = newsDao)
    }


    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
    ): NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db",
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao


}