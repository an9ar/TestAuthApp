package com.an9ar.testauthapp.data.repositoryImpl

import com.an9ar.testauthapp.domain.repository.WeatherRepository
import com.an9ar.testauthapp.data.network.WeatherApi
import com.an9ar.testauthapp.data.network.WeatherApiDTOs
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        token: String
    ): Single<WeatherApiDTOs.CurrentWeatherResponse> {
        return weatherApi.getCurrentWeatherByGeo(
            latitude = latitude,
            longitude = longitude,
            token = token
        )
    }

}