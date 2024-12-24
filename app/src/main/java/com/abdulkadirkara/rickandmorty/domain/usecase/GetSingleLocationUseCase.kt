package com.abdulkadirkara.rickandmorty.domain.usecase

import android.util.Log
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toLocationDetail
import com.abdulkadirkara.rickandmorty.domain.model.LocationDetail
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSingleLocationUseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    private val TAG = this::class.java.simpleName
    operator fun invoke(id: Int) : Flow<NetworkResponse<LocationDetail>> = flow {
        Log.e(TAG, "invoke was called")
        emit(NetworkResponse.Loading)
        when(val response = repository.getSingleLocation(id)){
            is NetworkResponse.Error -> emit(response)
            is NetworkResponse.Loading -> Unit
            is NetworkResponse.Success -> emit(
                NetworkResponse.Success(response.result!!.toLocationDetail())
            )
        }
    }
}