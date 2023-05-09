package com.pil.movieApp.service

import com.pil.movieApp.service.model.Movie
import com.pil.movieApp.util.ApiErrorException
import com.pil.movieApp.util.CoroutineResult

interface MovieService {
    suspend fun getMovies(): CoroutineResult<List<Movie>>
}

class MovieServiceImpl(private val client: MovieClient) : MovieService {

    override suspend fun getMovies(): CoroutineResult<List<Movie>> {
        try {
            val response = client.getPopularMovies().execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    return CoroutineResult.Success(it.movies)
                }
            }
        } catch (e: ApiErrorException) {
            return CoroutineResult.Failure(e)
        }
        return CoroutineResult.Failure(ApiErrorException("Error in obtaining data from the api"))

    }

}
