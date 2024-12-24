package com.abdulkadirkara.rickandmorty.data.repository

import com.abdulkadirkara.rickandmorty.data.datasource.RemoteDataSource
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.LocationResponse
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    //ioDispatcher burda da çağrılmalı mı sonuçta datastore'da onunla api'dan çağırdım??
) : RickAndMortyRepository {
    override suspend fun getAllCharacters(): NetworkResponse<CharactersResponse> {
        val response = remoteDataSource.getAllCharacters()
        return response
    }

    override suspend fun getSingleCharacter(id: Int): NetworkResponse<CharacterResponse> {
        val response = remoteDataSource.getSingleCharacter(id)
        return response
    }

    override suspend fun getAllLocations(): NetworkResponse<LocationResponse> {
        val response = remoteDataSource.getAllLocations()
        return response
    }
}