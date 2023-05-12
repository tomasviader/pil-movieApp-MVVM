package com.pil.movieApp.data.service



import com.pil.retrofit_room.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRequestGenerator {

    private const val API_MOVIES_URL = BuildConfig.API_URL
    private const val bearerToken = BuildConfig.MOVIE_API_KEY

    private class OAuthInterceptor(private val tokenType: String, private val accessToken: String) :
        Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder().header("Authorization", "$tokenType $accessToken").build()
            return chain.proceed(request)
        }
    }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor("Bearer", bearerToken))
            .build()
    }


    private val builder by lazy {
        Retrofit.Builder()
            .baseUrl(API_MOVIES_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    fun <S> createService(serviceClass: Class<S>): S {
        try {
            val retrofit = builder.client(httpClient).build()
            return retrofit.create(serviceClass)
        } catch (e: Exception) {
            throw RuntimeException("Failed to create Retrofit service.")
        }
    }

}
