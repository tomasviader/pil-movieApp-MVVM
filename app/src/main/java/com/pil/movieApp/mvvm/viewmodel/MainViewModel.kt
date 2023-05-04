package com.pil.movieApp.mvvm.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pil.movieApp.mvvm.contract.MainContract
import com.pil.movieApp.service.model.Movie
import com.pil.movieApp.util.CoroutineResult
import com.pil.movieApp.util.ServiceErrorException
import com.pil.retrofit_room.R
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
                    mutableLiveData.value = mutableLiveData.value?.copy(
                        status = MainStatus.SHOW_INFO,
                        movies = result.data,
                        exception = null
                    )
                }
                is CoroutineResult.Failure -> {
                    mutableLiveData.value =
                        mutableLiveData.value?.copy(
                            status = MainStatus.ERROR,
                            exception = result.exception)
                }
            }
        }
    }

    data class MainData(
        val status: MainStatus,
        val movies: List<Movie>?,
        val exception: Exception?,
    )

    enum class MainStatus {
        SHOW_INFO,
        ERROR
    }
}
