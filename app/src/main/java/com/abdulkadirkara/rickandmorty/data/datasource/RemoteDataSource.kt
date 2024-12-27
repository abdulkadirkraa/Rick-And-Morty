package com.abdulkadirkara.rickandmorty.data.datasource

import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.LocationResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result

interface RemoteDataSource {
    suspend fun getAllCharacters(): NetworkResponse<CharactersResponse>
    suspend fun getSingleCharacter(id: Int): NetworkResponse<CharacterResponse>
    suspend fun getAllLocations(): NetworkResponse<LocationResponse>
    suspend fun getSingleLocation(id: Int): NetworkResponse<Result>
    suspend fun getMultipleCharacters(ids: List<Int>): NetworkResponse<List<CharacterResponse>>
    suspend fun searchCharacter(name: String): NetworkResponse<CharactersResponse>
}