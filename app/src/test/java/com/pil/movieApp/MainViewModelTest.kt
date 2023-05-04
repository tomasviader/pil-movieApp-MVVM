package com.pil.movieApp

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.pil.movieApp.database.MovieDataBase
import com.pil.movieApp.database.entity.MovieEntity
import com.pil.movieApp.mvvm.contract.MainContract
import com.pil.movieApp.mvvm.viewmodel.MainViewModel
import com.pil.movieApp.service.model.Movie
import com.pil.movieApp.service.model.MovieList
import com.pil.movieApp.util.CoroutineResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

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

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MainViewModel(model)
    }

    @Test
    fun `callService should set SHOW_INFO status when getMovies is successful`() {
        // arrange
        coEvery { model.getMovies() } returns CoroutineResult.Success(movieList)
        coEvery { database.getAllMovies() } returns movieList

        viewModel.callService()
        // assert
        coVerify { database.insertMovies(movieList) }
        assertEquals(movieList, viewModel.getValue().value?.movies)
        assertEquals(MainViewModel.MainStatus.SHOW_INFO, viewModel.getValue().value?.status)
    }




    /*@Test
    fun `callService should set EMPTY_STATE status when getMovies fails`() {
        coEvery { model.getMovies() } returns CoroutineResult.Failure(Exception())

        viewModel.callService()

        val expectedData = MainViewModel.MainData(
            MainViewModel.MainStatus.EMPTY_STATE,
            null,
            View.VISIBLE
        )

        assertEquals(expectedData, mutableLiveData.value)
    }*/
}
