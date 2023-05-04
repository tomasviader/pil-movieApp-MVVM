package com.pil.movieApp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pil.movieApp.database.MovieDataBase
import com.pil.movieApp.database.MovieDataBaseImpl
import com.pil.movieApp.mvvm.contract.MainContract
import com.pil.movieApp.mvvm.viewmodel.MainViewModel
import com.pil.movieApp.service.model.Movie
import com.pil.movieApp.util.CoroutineResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyList

class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var model: MainContract.Model

    private lateinit var viewModel: MainViewModel

    @MockK
    private lateinit var movieList: List<Movie>

    @MockK
    private lateinit var database: MovieDataBase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MainViewModel(model)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `callService should set SHOW_INFO status when getMovies is successful`() {
        coEvery { model.getMovies() } returns CoroutineResult.Success(movieList)
        coEvery { database.getAllMovies() } returns movieList

        viewModel.callService()
        //coVerify {  database.insertMovies(movieList)}

        assertEquals(movieList, viewModel.getValue().value?.movies)
        assertEquals(MainViewModel.MainStatus.SHOW_INFO, viewModel.getValue().value?.status)
    }


}
