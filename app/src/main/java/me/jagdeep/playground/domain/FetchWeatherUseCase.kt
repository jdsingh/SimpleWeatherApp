package me.jagdeep.playground.domain

import me.jagdeep.playground.data.WeatherRepository
import me.jagdeep.playground.data.WeatherResponseMapper

sealed class WeatherQuery {
    data class ByCity(val city: String) : WeatherQuery()
    data class ByLocation(val lat: Double, val long: Double) : WeatherQuery()
}

class FetchWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val weatherResponseMapper: WeatherResponseMapper,
) {

    suspend fun fetchWeather(
        query: WeatherQuery,
        unit: WeatherUnit,
    ): WeatherForecast {
        val weather = when (query) {
            is WeatherQuery.ByCity -> {
                weatherRepository.fetchWeatherByCity(
                    city = query.city,
                    unit = unit
                )
            }

            is WeatherQuery.ByLocation -> {
                weatherRepository.fetchWeatherByLocation(
                    lat = query.lat,
                    long = query.long,
                    unit = unit
                )
            }
        }

        return weatherResponseMapper.map(weather, unit, query)
    }
}
