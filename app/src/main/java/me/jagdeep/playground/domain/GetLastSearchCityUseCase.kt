package me.jagdeep.playground.domain

import me.jagdeep.playground.data.LastSearchCityRepository

class GetLastSearchCityUseCase(
    private val lastSearchCityRepository: LastSearchCityRepository
) {

    suspend fun getLastSearchCity(): String? {
        return lastSearchCityRepository.getLastSearchCity()
    }
}
