package com.abdulkadirkara.rickandmorty.domain.usecase

import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchCharahctersUseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    suspend operator fun invoke(name: String) : Flow<NetworkResponse<List<CharacterListItem>>> {
        return repository.searchCharacter(name)
    }
}