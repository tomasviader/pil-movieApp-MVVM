package com.pil.movieApp.useCase

import com.pil.movieApp.domain.database.MovieDataBase
import com.pil.movieApp.domain.entity.Movie
import com.pil.movieApp.domain.service.MovieService
import com.pil.movieApp.domain.usecase.GetMoviesUserCase
import com.pil.movieApp.domain.usecase.GetMoviesUserCaseImpl
import com.pil.movieApp.domain.util.CoroutineResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class GetMoviesUseCaseImplTest {

    @MockK
    private lateinit var movieService: MovieService

    @MockK
    private lateinit var db: MovieDataBase

    @MockK
    private lateinit var  movieList: List<Movie>

    private lateinit var getMoviesListUseCase: GetMoviesUserCase


    @Before
    fun init() {
        MockKAnnotations.init(this, relaxed = true)
        getMoviesListUseCase = GetMoviesUserCaseImpl(movieService, db)
    }

    @Test
     fun `when use case return success`() {
        coEvery { movieService.getMovies() } returns CoroutineResult.Success(movieList)
        coEvery { db.getAllMovies() } returns CoroutineResult.Success(movieList)

        val result = runBlocking { getMoviesListUseCase() }

        coVerify { db.insertMovies(movieList) }
        coVerify { db.getAllMovies() }

        assertEquals(movieList, (result as CoroutineResult.Success).data)
    }

    @Test
    fun `when use case return failure but the database is not empty`() {
        coEvery { movieService.getMovies() } returns CoroutineResult.Failure(Exception())
        coEvery { db.getAllMovies() } returns CoroutineResult.Success(movieList)

        val result = runBlocking { getMoviesListUseCase() }

        coVerify { db.getAllMovies() }

        assertEquals(movieList, (result as CoroutineResult.Success).data)
    }

    @Test
    fun `when use case return failure and the database is empty`() {
        coEvery { movieService.getMovies() } returns CoroutineResult.Failure(Exception(
            EXCEPTION_MESSAGE
        ))
        coEvery { db.getAllMovies() } returns CoroutineResult.Failure(Exception(EXCEPTION_MESSAGE))

        val result = runBlocking { getMoviesListUseCase() }

        coVerify { db.getAllMovies() }

        assertEquals(EXCEPTION_MESSAGE, (result as CoroutineResult.Failure).exception.message)
    }

    companion object {
        private const val EXCEPTION_MESSAGE = "ERROR"
    }
}