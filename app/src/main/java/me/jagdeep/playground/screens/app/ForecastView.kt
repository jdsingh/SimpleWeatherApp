package me.jagdeep.playground.screens.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import me.jagdeep.playground.R
import me.jagdeep.playground.domain.WeatherForecast
import me.jagdeep.playground.domain.WeatherUnit

@Composable
fun ForecastView(forecast: WeatherForecast) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Row {
                    if (forecast.showCurrentLocationIcon) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = stringResource(id = R.string.current_location_icon)
                        )

                        Spacer(modifier = Modifier.size(8.dp))
                    }

                    Text(
                        text = forecast.city,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                val temperatureStringRes = when (forecast.unit) {
                    WeatherUnit.IMPERIAL -> R.string.temperature_fahrenheit
                    WeatherUnit.METRIC -> R.string.temperature_celsius
                }
                Text(
                    text = stringResource(
                        id = temperatureStringRes,
                        forecast.temperature,
                        forecast.feelsLike
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(
                    text = forecast.descriptionString,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterVertically),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(forecast.iconUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.weather_icon),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastViewPreview() {
    MaterialTheme {
        ForecastView(
            WeatherForecast(
                locationType = WeatherForecast.LocationType.Coordinates,
                city = "Washington, DC",
                temperature = 20,
                feelsLike = 18,
                unit = WeatherUnit.IMPERIAL,
                description = "Cloudy",
                icon = "04d",
            )
        )
    }
}
