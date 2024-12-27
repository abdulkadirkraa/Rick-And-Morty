package com.abdulkadirkara.rickandmorty.domain.repository

import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    suspend fun getAllCharacters(): Flow<NetworkResponse<List<CharacterListItem>>>
    suspend fun getSingleCharacter(id: Int): Flow<NetworkResponse<CharacterDetail>>
    suspend fun getAllLocations(): Flow<NetworkResponse<List<LocationListItem>>>
    suspend fun getSingleLocation(id: Int) : Flow<NetworkResponse<Result>>
    suspend fun getMultipleCharacters(ids: List<Int>): Flow<NetworkResponse<List<CharacterListItem>>>
    suspend fun searchCharacter(name: String): Flow<NetworkResponse<List<CharacterListItem>>>
}