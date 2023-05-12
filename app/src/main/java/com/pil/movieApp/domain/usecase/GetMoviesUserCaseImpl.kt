package com.pil.movieApp.domain.usecase



import com.pil.movieApp.domain.database.MovieDataBase
import com.pil.movieApp.domain.entity.Movie
import com.pil.movieApp.domain.service.MovieService
import com.pil.movieApp.domain.util.CoroutineResult

interface GetMoviesUserCase{
    suspend operator fun invoke(): CoroutineResult<List<Movie>>
}

class GetMoviesUserCaseImpl(
    private val service: MovieService,
    private val database: MovieDataBase,
) : GetMoviesUserCase {

    override suspend operator fun invoke(): CoroutineResult<List<Movie>> {
        return when (val movies = service.getMovies()) {
            is CoroutineResult.Success -> {
                database.insertMovies(movies.data)
                database.getAllMovies()
            }

            is CoroutineResult.Failure -> {
                database.getAllMovies()
                // throw ServiceErrorException("\"Error getting movies from the service.\"")
            }
        }
    }

}
