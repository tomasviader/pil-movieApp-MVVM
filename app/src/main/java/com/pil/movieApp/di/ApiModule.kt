package com.pil.movieApp.di

import com.pil.movieApp.data.service.MovieRequestGenerator
import com.pil.movieApp.data.service.api.MovieClient
import org.koin.dsl.module

object ApiModule {
    val apiModule = module {
        factory { MovieRequestGenerator() }
    }
}