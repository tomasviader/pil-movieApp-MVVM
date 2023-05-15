package com.pil.movieApp.data.service.response

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("data")
    val data: ResultResponse
)