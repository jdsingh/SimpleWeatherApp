package me.jagdeep.playground.data

import me.jagdeep.playground.domain.WeatherForecast
import me.jagdeep.playground.domain.WeatherQuery
import me.jagdeep.playground.domain.WeatherUnit

class WeatherResponseMapper {

    fun map(
        weatherResponse: WeatherResponse,
        unit: WeatherUnit,
        weatherQuery: WeatherQuery,
    ): WeatherForecast {
        return WeatherForecast(
            locationType = when (weatherQuery) {
                is WeatherQuery.ByCity -> WeatherForecast.LocationType.City
                is WeatherQuery.ByLocation -> WeatherForecast.LocationType.Coordinates
            },
            city = weatherResponse.name,
            temperature = weatherResponse.main.temp.toInt(),
            feelsLike = weatherResponse.main.feelsLike.toInt(),
            unit = unit,
            description = weatherResponse.weather.first().description,
            icon = weatherResponse.weather.first().icon,
        )
    }
}
