package me.jagdeep.playground.domain

import me.jagdeep.playground.data.LastSearchCityRepository

class SaveLastSearchCityUseCase(
    private val lastSearchCityRepository: LastSearchCityRepository
) {

    suspend fun saveLastSearchCity(city: String) {
        lastSearchCityRepository.saveLastSearchCity(city)
    }
}
