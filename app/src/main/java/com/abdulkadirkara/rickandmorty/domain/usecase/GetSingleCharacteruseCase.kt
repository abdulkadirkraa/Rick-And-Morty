package com.abdulkadirkara.rickandmorty.domain.usecase

import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class GetSingleCharacteruseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    suspend operator fun invoke(id: Int) = repository.getSingleCharacter(id)
}