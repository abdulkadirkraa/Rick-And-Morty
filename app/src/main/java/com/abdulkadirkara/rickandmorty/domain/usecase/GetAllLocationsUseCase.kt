package com.abdulkadirkara.rickandmorty.domain.usecase

import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLocationsUseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    suspend operator fun invoke(): Flow<NetworkResponse<List<LocationListItem>>> {
        return repository.getAllLocations()
    }
}