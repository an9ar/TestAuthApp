package com.an9ar.testauthapp.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?units=metric&lang=ru")
    fun getCurrentWeatherByGeo(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") token: String
    ): Single<WeatherApiDTOs.CurrentWeatherResponse>
}