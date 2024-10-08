package me.jagdeep.playground.data

val fakeWeatherResponse = WeatherResponse(
    weather = listOf(
        Weather(
            id = 721,
            main = "Haze",
            description = "haze",
            icon = "50n"
        )
    ),
    main = Main(
        temp = 59.09,
        feelsLike = 57.67,
    ),
    visibility = 10000,
    name = "Centreville",
)
