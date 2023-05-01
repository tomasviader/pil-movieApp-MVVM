package com.pil.movieApp.service


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRequestGenerator {

    private const val API_MOVIES_URL = "https://api.themoviedb.org/"
    private const val bearerToken =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZDgzMjY4MjczNWY0MTM4Yzc2ZDJiMWUyMGJiMTBlNiIs" +
                "InN1YiI6IjY0NGFlYTY1NzI2ZmIxMDU3NDA1YjZiOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJd" +
                "LCJ2ZXJzaW9uIjoxfQ.tDRU4h1Noc4pMXk9tIwQNfOpyZ-ivr28c4wAfQdSn3Y"

    class OAuthInterceptor(private val tokenType: String, private val accessToken: String) :
        Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder().header("Authorization", "$tokenType $accessToken").build()
            return chain.proceed(request)
        }
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor("Bearer", bearerToken))

    private val builder = Retrofit.Builder()
        .baseUrl(API_MOVIES_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }

}
