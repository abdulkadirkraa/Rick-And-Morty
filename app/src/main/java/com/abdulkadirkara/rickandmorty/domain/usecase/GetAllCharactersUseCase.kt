package com.abdulkadirkara.rickandmorty.domain.usecase

import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val repository: RickAndMortyRepository) {
    suspend operator fun invoke(): List<CharacterListItem> {
        return repository.getAllCharacters()
    }
}