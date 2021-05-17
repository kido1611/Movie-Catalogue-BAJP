package com.kido1611.dicoding.moviecatalogue.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieEntity
import com.kido1611.dicoding.moviecatalogue.data.source.local.entity.MovieRemoteKey

@Database(
    entities = [
        MovieEntity::class,
        MovieRemoteKey::class
    ],
    version = 9,
    exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}