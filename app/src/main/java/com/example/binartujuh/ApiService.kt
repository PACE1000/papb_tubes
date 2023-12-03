package com.example.binartujuh

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/weather/{provinsi}")
        fun getAllProvinsi(
            @Path("provinsi") provinsi:String
    ): Call<WeatherResponses>

    @GET("weather/provinsi/{kota}")
        fun getAllKota(
            @Path("kota") kota:String
        ):Call<WeatherResponses>
}