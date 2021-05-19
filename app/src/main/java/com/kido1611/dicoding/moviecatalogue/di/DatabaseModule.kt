package com.kido1611.dicoding.moviecatalogue.di

import android.content.Context
import androidx.room.Room
import com.kido1611.dicoding.moviecatalogue.data.source.local.TMDBDatabase
import com.kido1611.dicoding.moviecatalogue.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TMDBDatabase {
        return Room.databaseBuilder(
            context,
            TMDBDatabase::class.java,
            "tmdb.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideExecutor(): AppExecutors {
        return AppExecutors()
    }
}