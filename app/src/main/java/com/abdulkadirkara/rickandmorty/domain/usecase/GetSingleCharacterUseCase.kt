package com.abdulkadirkara.rickandmorty.domain.usecase

import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSingleCharacterUseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    suspend operator fun invoke(id: Int): Flow<NetworkResponse<CharacterDetail>> {
        return repository.getSingleCharacter(id)
    }
}