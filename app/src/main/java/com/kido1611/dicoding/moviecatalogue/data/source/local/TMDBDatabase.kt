package com.kido1611.dicoding.moviecatalogue.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieBookmark
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieRemoteKey

@Database(
    entities = [
        MovieEntity::class,
        MovieRemoteKey::class,
        MovieBookmark::class
    ],
    version = 12,
    exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}