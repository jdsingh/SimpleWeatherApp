package me.jagdeep.playground.screens.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.jagdeep.playground.R
import me.jagdeep.playground.presenation.AppUiState
import me.jagdeep.playground.presenation.AppViewModel

@Composable
fun WeatherApp(viewModel: AppViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { WeatherAppBar() },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState) {
                is AppUiState.Initial -> {
                    LocationPermissionView(viewModel::onLocationPermissionGranted)
                    SearchView(state, viewModel)
                }

                is AppUiState.Loading -> {
                    LoadingView()
                }

                is AppUiState.InvalidCityError -> {
                    ErrorView(message = stringResource(id = R.string.invalid_city_error))
                    if (!state.hasLocationPermission) {
                        LocationPermissionView(viewModel::onLocationPermissionGranted)
                    }
                    SearchView(state, viewModel)
                }

                is AppUiState.Error -> {
                    ErrorView(message = state.message)
                    if (!state.hasLocationPermission) {
                        LocationPermissionView(viewModel::onLocationPermissionGranted)
                    }
                    SearchView(state, viewModel)
                }

                is AppUiState.Success -> {
                    ForecastView(forecast = state.forecast)
                    if (!state.hasLocationPermission) {
                        LocationPermissionView(viewModel::onLocationPermissionGranted)
                    }
                    SearchView(state, viewModel)
                }
            }
        }
    }
}
