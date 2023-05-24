package com.pil.movieApp.presentation.mvvm.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pil.movieApp.domain.entity.Movie
import com.pil.movieApp.domain.util.CoroutineResult
import com.pil.movieApp.presentation.mvvm.model.MoviesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(private val model: MoviesModel) : ViewModel() {

    private val mutableLiveData: MutableLiveData<MoviesData> = MutableLiveData()
    fun getValue(): LiveData<MoviesData> = mutableLiveData

    fun callService() = viewModelScope.launch {
        withContext(Dispatchers.IO) { model.getMovies() }.let { result ->
            when (result) {
                is CoroutineResult.Success -> {
                    mutableLiveData.value =
                        MoviesData(status = MoviesStatus.SHOW_INFO, movies = result.data)
                }

                is CoroutineResult.Failure -> {
                    mutableLiveData.value =
                        MoviesData(status = MoviesStatus.EMPTY_STATE)
                }
            }
        }
    }

    fun onBackButtonPressed() {
        mutableLiveData.postValue(MoviesData(MoviesStatus.BACK_BUTTON))
    }


    data class MoviesData(
        val status: MoviesStatus,
        val movies: List<Movie> = emptyList(),
        val exception: Exception? = null,
    )

    enum class MoviesStatus {
        SHOW_INFO,
        EMPTY_STATE,
        BACK_BUTTON
    }
}
