package com.furkanisitan.youtubeplaylist.helpers

import com.furkanisitan.youtubeplaylist.clients.retrofit.ApiClient
import com.furkanisitan.youtubeplaylist.clients.retrofit.ApiInterface
import com.furkanisitan.youtubeplaylist.models.Playlist
import retrofit2.Callback

object PlaylistHelper {

    private const val API_KEY = "AIzaSyAJECj_VsS2qySvWtadPuIV-34dfQUTBHg"
    private const val CHANNEL_ID = "UClpEUtFu_dXbrUJ6kc3QtlA"

    fun get(limit: Int, callback: Callback<Playlist>) {
        val apiInterface = ApiClient.client?.create(ApiInterface::class.java)
        val apiCall = apiInterface?.getAllList(CHANNEL_ID, API_KEY, limit)
        apiCall?.enqueue(callback)
    }
}