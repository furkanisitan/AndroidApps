package com.furkanisitan.weatherapp.helpers

import android.net.Uri
import org.json.JSONArray
import org.json.JSONObject

class OpenWeatherMapHelper private constructor() {

    companion object {

        @Volatile
        private var INSTANCE: OpenWeatherMapHelper? = null

        private var main: JSONObject? = null
        private var weather: JSONArray? = null

        fun getInstance(response: JSONObject): OpenWeatherMapHelper {
            main = response.getJSONObject("main")
            weather = response.getJSONArray("weather")
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: OpenWeatherMapHelper().also { INSTANCE = it }
            }
        }

        private const val APP_ID_KEY = "appid"
        private const val APP_ID_VALUE = "4d2ae23788b71f88c6a1ad0cb1962a4f"
        private const val CITY_URL_KEY = "q"
        private const val LANG_URL_KEY = "lang"
        private const val UNITS_URL_KEY = "units"
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather"
        private val uriBuilder = Uri.parse(BASE_URL).buildUpon()

        fun getUrl(city: String, lang: String = "tr", units: String = "metric"): String =
            Uri.decode(
                uriBuilder
                    .clearQuery()
                    .appendQueryParameter(APP_ID_KEY, APP_ID_VALUE)
                    .appendQueryParameter(CITY_URL_KEY, city)
                    .appendQueryParameter(LANG_URL_KEY, lang)
                    .appendQueryParameter(UNITS_URL_KEY, units)
                    .build()
                    .toString()
            )
    }

    fun getTemp(): Int? =
        main?.getInt("temp")

    fun getDesc(): String? =
        weather?.getJSONObject(0)?.getString("description")

    fun getIconName(): String? =
        weather?.getJSONObject(0)?.getString("icon")

    fun isNight(): Boolean {
        val iconName = getIconName()
        if (iconName.isNullOrEmpty())
            return false
        return iconName.last().equals('n', true)
    }
}