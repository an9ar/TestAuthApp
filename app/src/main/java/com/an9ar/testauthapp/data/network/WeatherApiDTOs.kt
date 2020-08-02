package com.an9ar.testauthapp.data.network

import com.google.gson.annotations.SerializedName

object WeatherApiDTOs {
    data class CurrentWeatherResponse(
        @SerializedName("coord") val coordinates: Coordinates,
        @SerializedName("weather") val weather: List<WeatherInformation>,
        @SerializedName("base") val base: String,
        @SerializedName("main") val mainInformation: MainInformation,
        @SerializedName("visibility") val visibility: Long,
        @SerializedName("wind") val wind: WindInformation,
        @SerializedName("clouds") val clouds: CloudsInformation,
        @SerializedName("dt") val time: Long,
        @SerializedName("sys") val location: LocationInformation,
        @SerializedName("timezone") val timezone: Long,
        @SerializedName("id") val id: Long,
        @SerializedName("name") val name: String,
        @SerializedName("cod") val code: Int
    )

    data class Coordinates(
        @SerializedName("lon") val longitude: Double,
        @SerializedName("lat") val latitude: Double
    )

    data class WeatherInformation(
        @SerializedName("id") val id: Long,
        @SerializedName("main") val main: String,
        @SerializedName("description") val description: String,
        @SerializedName("icon") val icon: String
    )

    data class MainInformation(
        @SerializedName("temp") val temp: Double,
        @SerializedName("feels_like") val feels_like: Double,
        @SerializedName("temp_min") val temp_min: Double,
        @SerializedName("temp_max") val temp_max: Double,
        @SerializedName("pressure") val pressure: Long,
        @SerializedName("humidity") val humidity: Long,
        @SerializedName("sea_level") val sea_level: Long,
        @SerializedName("grnd_level") val grnd_level: Long
    )

    data class WindInformation(
        @SerializedName("speed") val speed: Double,
        @SerializedName("deg") val deg: Long
    )

    data class CloudsInformation(
        @SerializedName("all") val all: Int
    )

    data class LocationInformation(
        @SerializedName("country") val country: String,
        @SerializedName("sunrise") val sunrise: Long,
        @SerializedName("sunset") val sunset: Long
    )
}