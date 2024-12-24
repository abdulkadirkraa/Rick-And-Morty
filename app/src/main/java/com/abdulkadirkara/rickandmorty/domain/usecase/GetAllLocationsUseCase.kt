package com.abdulkadirkara.rickandmorty.domain.usecase

import android.util.Log
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toLocationListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllLocationsUseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    private val TAG = this::class.java.simpleName
    operator fun invoke(): Flow<NetworkResponse<List<LocationListItem>>> = flow {
        Log.e(TAG, "invoke was called")
        emit(NetworkResponse.Loading)
        when(val response = repository.getAllLocations()){
            is NetworkResponse.Error -> emit(response)
            is NetworkResponse.Loading -> Unit
            is NetworkResponse.Success -> emit(
                NetworkResponse.Success(response.result!!.results.map { it.toLocationListItem() })
            )
        }
    }
}