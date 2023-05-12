package com.pil.movieApp.domain.database


import com.pil.movieApp.data.service.response.MovieListResponse
import com.pil.movieApp.domain.entity.Movie
import com.pil.movieApp.domain.util.CoroutineResult


interface MovieDataBase {
    suspend fun insertMovies(moviesList: List<Movie>)
    suspend fun getAllMovies(): CoroutineResult<List<Movie>>
}
