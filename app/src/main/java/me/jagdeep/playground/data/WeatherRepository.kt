package me.jagdeep.playground.data

import me.jagdeep.playground.domain.WeatherUnit

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val apiKey: String,
) {

    suspend fun fetchWeatherByCity(
        city: String,
        unit: WeatherUnit
    ): WeatherResponse {
        return weatherApi.getWeatherByCity(
            query = city,
            apiKey = apiKey,
            units = unit.toString()
        )
    }

    suspend fun fetchWeatherByLocation(
        lat: Double,
        long: Double,
        unit: WeatherUnit
    ): WeatherResponse {
        return weatherApi.getWeatherByLocation(
            lat = lat,
            long = long,
            apiKey = apiKey,
            units = unit.toString()
        )
    }
}
