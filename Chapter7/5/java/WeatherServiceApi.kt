package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class WeatherResponse(val main: Main)
data class Main(val temp: Float)

interface WeatherServiceApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double, @Query("lon") lon:
        Double, @Query("appid") apiKey: String
    ): WeatherResponse

    companion object {
        private const val BASE_URL =
            "https://api.openweathermap.org/data/2.5/"

        fun create(): WeatherServiceApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(WeatherServiceApi::class.java)
        }
    }
}
