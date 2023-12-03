package com.example.papb_tubes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
        fun getAllProvinsi(
            @Query("q") city:String,
    ): Call<WeatherResponse>
}