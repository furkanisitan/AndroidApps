package com.furkanisitan.youtubeplaylist.clients.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    @Volatile
    private var retrofit: Retrofit? = null

    val client: Retrofit?
        get() {
            return retrofit ?: synchronized(this) {
                retrofit ?: Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .also { retrofit = it }
            }
        }
}