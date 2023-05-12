package com.pil.movieApp.domain.service



import com.pil.movieApp.data.service.response.MovieListResponse
import com.pil.movieApp.domain.util.CoroutineResult


interface MovieService {
    suspend fun getMovies(): CoroutineResult<MovieListResponse>
}
