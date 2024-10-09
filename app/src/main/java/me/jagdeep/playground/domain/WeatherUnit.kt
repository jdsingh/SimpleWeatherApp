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
}
