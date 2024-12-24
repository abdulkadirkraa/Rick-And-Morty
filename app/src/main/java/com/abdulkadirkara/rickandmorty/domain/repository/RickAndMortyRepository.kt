package com.abdulkadirkara.rickandmorty.domain.repository

import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.LocationResponse

interface RickAndMortyRepository {
    suspend fun getAllCharacters(): NetworkResponse<CharactersResponse>
    suspend fun getSingleCharacter(id: Int): NetworkResponse<CharacterResponse>
    suspend fun getAllLocations(): NetworkResponse<LocationResponse>
}