package com.abdulkadirkara.rickandmorty.domain.usecase

import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMultipleCharacterUseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    suspend operator fun invoke(ids: List<Int>) : Flow<NetworkResponse<List<CharacterListItem>>> {
        return repository.getMultipleCharacters(ids)
    }
}