package com.abdulkadirkara.rickandmorty.domain.usecase

import android.util.Log
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toCharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchCharahctersUseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    private val TAG = this::class.java.simpleName
    operator fun invoke(name: String) : Flow<NetworkResponse<List<CharacterListItem>>> = flow {
        Log.e(TAG, "invoke was called")
        emit(NetworkResponse.Loading)
        when(val response = repository.searchCharacter(name)){
            is NetworkResponse.Error -> emit(response)
            is NetworkResponse.Loading -> Unit
            is NetworkResponse.Success -> emit(NetworkResponse.Success(
                response.result!!.results.map { it.toCharacterListItem() }
            ))
        }
    }
}