package com.pil.movieApp.data.service.api

import com.pil.movieApp.data.service.response.DataResponse
import com.pil.movieApp.data.service.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieClient {
    @GET("/3/movie/popular")
    fun getPopularMovies(): Call<DataResponse>
}
