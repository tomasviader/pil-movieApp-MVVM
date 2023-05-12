package com.pil.movieApp.data.service.api

import com.pil.movieApp.data.service.response.MovieListResponse
import com.pil.movieApp.data.service.response.MovieResponse
import com.pil.movieApp.domain.entity.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MovieClient {
    @GET("/3/movie/popular")
    fun getPopularMovies(): Call<MovieResponse>
}
