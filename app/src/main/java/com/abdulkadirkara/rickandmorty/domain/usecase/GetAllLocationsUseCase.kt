package com.abdulkadirkara.rickandmorty.domain.usecase

import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class GetAllLocationsUseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    suspend operator fun invoke(): List<LocationListItem> {
        return repository.getAllLocations()
    }
}