package com.pil.movieApp.data.service

import com.pil.movieApp.data.service.api.MovieClient
import com.pil.movieApp.domain.service.MovieService
import com.pil.movieApp.data.service.response.MovieListResponse
import com.pil.movieApp.domain.util.CoroutineResult


class MovieServiceImpl(private val client: MovieClient) : MovieService {

    override suspend fun getMovies(): CoroutineResult<MovieListResponse> {
        try {
            val response = client.getPopularMovies().execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    return CoroutineResult.Success(it)
                }
            }
        return CoroutineResult.Failure(Exception(response.errorBody().toString()))
        } catch (e: Exception) {
            return CoroutineResult.Failure(e)
        }
    }

}
