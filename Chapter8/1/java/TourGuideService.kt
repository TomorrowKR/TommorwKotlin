package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TourGuideApi {
    @GET("getLocationInfo")
    suspend fun getLocationInfo(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double
    ): LocationInfoResponse
}

data class LocationInfoResponse(val title: String, val description: String)

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://example.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: TourGuideApi = retrofit.create(TourGuideApi::class.java)
}
