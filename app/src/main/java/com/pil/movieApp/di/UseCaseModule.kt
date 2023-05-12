package com.pil.movieApp.di

import com.pil.movieApp.domain.usecase.GetMoviesUserCase
import com.pil.movieApp.domain.usecase.GetMoviesUserCaseImpl
import org.koin.dsl.module

object UseCaseModule {
    val useCaseModule = module {
        factory<GetMoviesUserCase> { GetMoviesUserCaseImpl(get(), get()) }
    }
}