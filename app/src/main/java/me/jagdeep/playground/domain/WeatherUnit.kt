package me.jagdeep.playground.domain

enum class WeatherUnit {
    IMPERIAL,
    METRIC;

    override fun toString(): String {
        return when (this) {
            IMPERIAL -> "imperial"
            METRIC -> "metric"
        }
    }

    fun toUIString(): String {
        return when (this) {
            IMPERIAL -> "°F"
            METRIC -> "°C"
        }
    }
}
