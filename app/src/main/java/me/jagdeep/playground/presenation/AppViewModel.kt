package me.jagdeep.playground.presenation

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.jagdeep.playground.domain.FetchWeatherUseCase
import me.jagdeep.playground.domain.GetLastSearchCityUseCase
import me.jagdeep.playground.domain.SaveLastSearchCityUseCase
import me.jagdeep.playground.domain.WeatherForecast
import me.jagdeep.playground.domain.WeatherQuery
import me.jagdeep.playground.domain.WeatherUnit
import me.jagdeep.playground.permission.GetLastKnownLocationUseCase
import me.jagdeep.playground.permission.PermissionChecker

sealed class AppUiState {
    data object Initial : AppUiState()
    data object Loading : AppUiState()
    data class Success(
        val forecast: WeatherForecast,
        val hasLocationPermission: Boolean,
    ) : AppUiState()

    data class Error(
        val message: String,
        val hasLocationPermission: Boolean,
    ) : AppUiState()

    data class InvalidCityError(
        val hasLocationPermission: Boolean = false
    ) : AppUiState()
}

class AppViewModel(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val getLastSearchCityUseCase: GetLastSearchCityUseCase,
    private val saveLastSearchCityUseCase: SaveLastSearchCityUseCase,
    private val getLastKnownLocationUseCase: GetLastKnownLocationUseCase,
    private val permissionChecker: PermissionChecker,
) : ViewModel() {

    private val _uiState: MutableStateFlow<AppUiState> = MutableStateFlow(AppUiState.Initial)
    val uiState: StateFlow<AppUiState> = _uiState

    init {
        viewModelScope.launch {
            if (permissionChecker.hasLocationPermission()) {
                _uiState.value = AppUiState.Initial
            } else {
                val lastSearchCity = getLastSearchCityUseCase.getLastSearchCity()
                if (lastSearchCity != null) {
                    fetchWeather(WeatherQuery.ByCity(lastSearchCity))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun onLocationPermissionGranted() {
        _uiState.value = AppUiState.Loading
        viewModelScope.launch {
            val location = getLastKnownLocationUseCase.getLastKnownLocation()
            onUserLocationFound(location)
        }
    }

    private fun onUserLocationFound(location: Location?) {
        if (location != null) {
            fetchWeather(WeatherQuery.ByLocation(location.latitude, location.longitude))
        } else {
            _uiState.value = AppUiState.Error(
                message = "Failed to fetch your location",
                hasLocationPermission = permissionChecker.hasLocationPermission()
            )
        }
    }

    fun onSearchWithCity(query: String) {
        if (query.isNotBlank()) {
            fetchWeather(WeatherQuery.ByCity(query))
        } else {
            _uiState.value = AppUiState.InvalidCityError(
                hasLocationPermission = permissionChecker.hasLocationPermission()
            )
        }
    }

    private fun fetchWeather(query: WeatherQuery) {
        viewModelScope.launch {
            _uiState.value = AppUiState.Loading
            runCatching {
                fetchWeatherUseCase.fetchWeather(
                    query = query,
                    unit = WeatherUnit.IMPERIAL
                )
            }.onSuccess { weatherForecast ->
                _uiState.value = AppUiState.Success(
                    forecast = weatherForecast,
                    hasLocationPermission = permissionChecker.hasLocationPermission()
                )
                if (query is WeatherQuery.ByCity) {
                    saveLastSearchCityUseCase.saveLastSearchCity(query.city)
                }
            }.onFailure {
                _uiState.value = AppUiState.Error(
                    message = "Failed to fetch weather, make sure the city name is valid",
                    hasLocationPermission = permissionChecker.hasLocationPermission()
                )
            }
        }
    }
}
