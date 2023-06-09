package com.pil.movieApp.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pil.movieApp.presentation.mvvm.viewmodel.MoviesViewModel
import com.pil.movieApp.domain.entity.Movie
import com.pil.movieApp.domain.util.CoroutineResult
import com.pil.movieApp.presentation.mvvm.model.MoviesModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MoviesViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MoviesViewModel

    @MockK
    private lateinit var model: MoviesModel

    @MockK
    private lateinit var movieList: List<Movie>


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MoviesViewModel(model)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `callService should set SHOW_INFO status when getMovies is successful`() {
        coEvery { model.getMovies() } returns CoroutineResult.Success(movieList)

        runBlocking { viewModel.callService().join() }

        assertEquals(movieList, viewModel.getValue().value?.movies)
        assertEquals(MoviesViewModel.MoviesStatus.SHOW_INFO, viewModel.getValue().value?.status)
    }

    @Test
    fun `callService should set ERROR status when getMovies fail`() {
        coEvery { model.getMovies() } returns CoroutineResult.Failure(Exception())

        runBlocking { viewModel.callService().join() }

        assertEquals(MoviesViewModel.MoviesStatus.EMPTY_STATE, viewModel.getValue().value?.status)
    }


}
