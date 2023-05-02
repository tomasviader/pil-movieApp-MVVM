package com.pil.movieApp

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.pil.movieApp.mvvm.contract.MainContract
import com.pil.movieApp.mvvm.viewmodel.MainViewModel
import com.pil.movieApp.util.CoroutineResult
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private val model: MainContract.Model = mockk()
    private val mutableLiveData: MutableLiveData<MainViewModel.MainData> = spyk()

    @Before
    fun setup() {
        viewModel = MainViewModel(model)
        viewModel.getValue().observeForever { }
        every { mutableLiveData.value = any() } just Runs
        every { mutableLiveData.postValue(any()) } just Runs
    }

    @Test
    fun `callService should set SHOW_INFO status when getMovies is successful`() {
        coEvery { model.getMovies() } returns CoroutineResult.Success(listOf())

        viewModel.callService()

        val expectedData = MainViewModel.MainData(
            MainViewModel.MainStatus.SHOW_INFO,
            listOf(),
            View.INVISIBLE
        )

        assertEquals(expectedData.status, mutableLiveData.value)
    }

    @Test
    fun `callService should set EMPTY_STATE status when getMovies fails`() {
        coEvery { model.getMovies() } returns CoroutineResult.Failure(Exception())

        viewModel.callService()

        val expectedData = MainViewModel.MainData(
            MainViewModel.MainStatus.EMPTY_STATE,
            null,
            View.VISIBLE
        )

        assertEquals(expectedData, mutableLiveData.value)
    }
}
