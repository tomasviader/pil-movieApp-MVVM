package com.pil.movieApp.database

import com.pil.movieApp.database.dao.MovieDao
import com.pil.movieApp.database.mapper.mapToDataBaseExercise
import com.pil.movieApp.database.mapper.mapToLocalExercise
import com.pil.movieApp.service.model.Movie
import com.pil.movieApp.util.CoroutineResult
import java.lang.Exception

interface MovieDataBase {
    suspend fun insertMovies(movies: List<Movie>)
    suspend fun getAllMovies(): List<Movie>

}

class MovieDataBaseImpl(private val movieDao: MovieDao) : MovieDataBase {

    override suspend fun insertMovies(movies: List<Movie>) {
        movies.forEach { movie ->
            movieDao.insertMovie(movie.mapToDataBaseExercise())
        }
    }

    override suspend fun getAllMovies(): List<Movie> {
        /*return movieDao.getPopularMovies().let {
            if (it.isNotEmpty()) {
                CoroutineResult.Success(it.mapToLocalExercise())
            }else{
                CoroutineResult.Failure(Exception())
            }
        }*/
        return movieDao.getPopularMovies().mapToLocalExercise()
    }
}

