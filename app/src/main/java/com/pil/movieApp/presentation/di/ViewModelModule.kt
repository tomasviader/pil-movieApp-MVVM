package com.pil.movieApp.presentation.di

import com.pil.movieApp.presentation.mvvm.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val moviesViewModelModule = module {
        viewModel { MoviesViewModel(get()) }
    }
}