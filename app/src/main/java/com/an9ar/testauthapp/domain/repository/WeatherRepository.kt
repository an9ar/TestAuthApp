package com.an9ar.testauthapp.domain.repository

import com.an9ar.testauthapp.data.network.WeatherApiDTOs
import io.reactivex.Single

interface WeatherRepository {
    fun getCurrentWeather(latitude: Double, longitude: Double, token: String): Single<WeatherApiDTOs.CurrentWeatherResponse>
}