package com.pil.movieApp.data.database

import com.pil.movieApp.database.mapper.mapToDataBaseExercise
import com.pil.movieApp.database.mapper.mapToLocalExercise
import com.pil.movieApp.domain.database.MovieDataBase
import com.pil.movieApp.data.service.response.MovieResponse

class MovieDataBaseImpl(private val movieDao: MovieDao) : MovieDataBase {

    override suspend fun insertMovies(movies: List<MovieResponse>) {
        movies.forEach { movie ->
            movieDao.insertMovie(movie.mapToDataBaseExercise())
        }
    }

    override suspend fun getAllMovies(): List<MovieResponse> {
        return movieDao.getPopularMovies().mapToLocalExercise()
    }

}