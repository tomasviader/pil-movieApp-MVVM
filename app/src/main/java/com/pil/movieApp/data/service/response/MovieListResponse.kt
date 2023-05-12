package com.pil.movieApp.data.service.response

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("count")
    var count: Int,
    @SerializedName("next")
    var next: String,
    @SerializedName("previous")
    var previous: String,
    @SerializedName("results")
    var movies: List<MovieResponse>,
)
