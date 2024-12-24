package com.abdulkadirkara.rickandmorty.domain.repository

import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem

interface RickAndMortyRepository {
    suspend fun getAllCharacters(): List<CharacterListItem>
    suspend fun getSingleCharacter(id: Int): CharacterDetail
    suspend fun getAllLocations(): List<LocationListItem>
}