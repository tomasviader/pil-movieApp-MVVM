package com.pil.movieApp.di

import androidx.room.Room
import com.pil.movieApp.data.database.MovieDB
import org.koin.dsl.module

object DBModule {
    private const val DB = "MovieDataBase"

    val dbModule = module {
        single { Room.databaseBuilder(get(), MovieDB::class.java, DB).build() }
        single { get<MovieDB>().moviesDao() }
    }
}