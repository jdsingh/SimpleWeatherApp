package me.jagdeep.playground.domain

data class WeatherForecast(
    val locationType: LocationType,
    val city: String,
    val temperature: Int,
    val feelsLike: Int,
    val unit: WeatherUnit,
    val description: String,
    val icon: String,
) {

    val showCurrentLocationIcon: Boolean
        get() = locationType == LocationType.Coordinates

    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${icon}@4x.png"

    val descriptionString: String
        get() = description.replaceFirstChar { it.uppercase() }

    enum class LocationType {
        City,
        Coordinates,
    }
}
