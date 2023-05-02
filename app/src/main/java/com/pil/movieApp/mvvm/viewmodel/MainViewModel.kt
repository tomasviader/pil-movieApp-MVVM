package com.pil.movieApp.mvvm.viewmodel

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pil.movieApp.mvvm.contract.MainContract
import com.pil.movieApp.service.model.Movie
import com.pil.movieApp.util.CoroutineResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val model: MainContract.Model) : ViewModel(), MainContract.ViewModel {

    private val mutableLiveData: MutableLiveData<MainData> = MutableLiveData()
    override fun getValue(): LiveData<MainData> = mutableLiveData

    override fun callService() = viewModelScope.launch {
        withContext(Dispatchers.IO) { model.getMovies() }.let { result ->
            when (result) {
                is CoroutineResult.Success -> {
                    mutableLiveData.value = MainData(MainStatus.SHOW_INFO, result.data, INVISIBLE)
                }
                is CoroutineResult.Failure -> {
                    mutableLiveData.value = MainData(MainStatus.EMPTY_STATE, null, VISIBLE)
                }
            }
        }
    }

    data class MainData(
        val status: MainStatus,
        val movies: List<Movie>?,
        val isVisible: Int
    )

    enum class MainStatus {
        SHOW_INFO,
        EMPTY_STATE
    }
}
