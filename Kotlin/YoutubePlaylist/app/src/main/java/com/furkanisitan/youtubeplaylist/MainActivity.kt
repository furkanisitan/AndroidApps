package com.furkanisitan.youtubeplaylist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.furkanisitan.youtubeplaylist.adapters.PlaylistAdapter
import com.furkanisitan.youtubeplaylist.helpers.PlaylistHelper
import com.furkanisitan.youtubeplaylist.models.Playlist
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
Package name:
64:3D:58:24:52:33:77:B1:4F:C0:48:B1:48:20:20:34:41:06:85:F4
*/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPlaylistToRecyclerView()
    }

    private fun setPlaylistToRecyclerView() {
        PlaylistHelper.get(25, object : Callback<Playlist> {

            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {

                response.body()?.items?.let {
                    Main_rv.adapter = PlaylistAdapter(it)

                    val linearLayoutManager =
                        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                    Main_rv.layoutManager = linearLayoutManager

                    supportActionBar?.subtitle = "Toplam Liste :${it.size}"
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
                Log.e("HATA", "" + t.printStackTrace())
            }
        })
    }
}
