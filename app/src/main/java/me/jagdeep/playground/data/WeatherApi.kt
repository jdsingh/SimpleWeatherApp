package me.jagdeep.playground.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") query: String,
        @Query("appId") apiKey: String,
        @Query("units") units: String,
    ): WeatherResponse

    @GET("/data/2.5/weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appId") apiKey: String,
        @Query("units") units: String,
    ): WeatherResponse
}
