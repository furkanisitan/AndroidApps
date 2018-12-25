package com.furkanisitan.weatherapp.helpers

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class VolleyHelper(private val context: Context) {

    fun get(url: String, listener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener) {
        val request = JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener)
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

    companion object {

        class VolleySingleton(context: Context) {

            companion object {

                @Volatile
                private var INSTANCE: com.furkanisitan.weatherapp.helpers.VolleyHelper.Companion.VolleySingleton? = null

                fun getInstance(context: Context) =
                    INSTANCE ?: synchronized(this) {
                        INSTANCE ?: VolleySingleton(context).also { INSTANCE = it }
                    }
            }

            /*
            val imageLoader: ImageLoader by lazy {
                ImageLoader(requestQueue,
                    object : ImageLoader.ImageCache {
                        private val cache = LruCache<String, Bitmap>(20)
                        override fun getBitmap(url: String): Bitmap {
                            return cache.get(url)
                        }

                        override fun putBitmap(url: String, bitmap: Bitmap) {
                            cache.put(url, bitmap)
                        }
                    })
            }
            */

            private val requestQueue: RequestQueue by lazy {
                Volley.newRequestQueue(context.applicationContext)
            }

            fun <T> addToRequestQueue(req: Request<T>) {
                requestQueue.add(req)
            }
        }
    }
}