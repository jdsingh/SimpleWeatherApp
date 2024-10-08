package me.jagdeep.playground.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.location.LocationServices
import me.jagdeep.playground.BuildConfig
import me.jagdeep.playground.data.LastSearchCityRepository
import me.jagdeep.playground.data.WeatherApi
import me.jagdeep.playground.data.WeatherRepository
import me.jagdeep.playground.data.WeatherResponseMapper
import me.jagdeep.playground.domain.FetchWeatherUseCase
import me.jagdeep.playground.domain.GetLastSearchCityUseCase
import me.jagdeep.playground.domain.SaveLastSearchCityUseCase
import me.jagdeep.playground.permission.GetLastKnownLocationUseCase
import me.jagdeep.playground.permission.PermissionChecker
import me.jagdeep.playground.presenation.AppViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_BASE_URL = "https://api.openweathermap.org/data/2.5/"

val appModule = module {
    single<WeatherApi> {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(WeatherApi::class.java)
    }

    factory { WeatherRepository(get(), BuildConfig.API_KEY) }
    factoryOf(::FetchWeatherUseCase)
    factoryOf(::WeatherResponseMapper)
    factoryOf(::GetLastSearchCityUseCase)
    factoryOf(::SaveLastSearchCityUseCase)
    single { LastSearchCityRepository(androidContext().dataStore) }
    single { PermissionChecker(androidContext()) }
    single {
        GetLastKnownLocationUseCase(
            LocationServices.getFusedLocationProviderClient(
                androidContext()
            )
        )
    }
    viewModelOf(::AppViewModel)
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "last_search_city")
