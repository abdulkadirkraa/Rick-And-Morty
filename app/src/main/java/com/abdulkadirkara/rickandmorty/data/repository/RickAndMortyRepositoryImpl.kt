package com.abdulkadirkara.rickandmorty.data.repository

import com.abdulkadirkara.rickandmorty.data.datasource.RemoteDataSource
import com.abdulkadirkara.rickandmorty.data.remote.BaseRepository
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toCharacterDetail
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toCharacterListItem
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toLocationListItem
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : RickAndMortyRepository, BaseRepository() {

    override suspend fun getAllCharacters(): Flow<NetworkResponse<List<CharacterListItem>>> = safeApiCall(
        apiCall = { remoteDataSource.getAllCharacters() },
        transform = { it.results.map { dto -> dto.toCharacterListItem() } }
    )

    override suspend fun getSingleCharacter(id: Int): Flow<NetworkResponse<CharacterDetail>> =
        safeApiCall(
            apiCall = { remoteDataSource.getSingleCharacter(id) },
            transform = { it.toCharacterDetail() }
        )

    override suspend fun getAllLocations(): Flow<NetworkResponse<List<LocationListItem>>> =
        safeApiCall(
            apiCall = { remoteDataSource.getAllLocations() },
            transform = { it -> it.results.map { it.toLocationListItem() } }
        )

    override suspend fun getSingleLocation(id: Int): Flow<NetworkResponse<Result>> =
        safeApiCall(
            apiCall = { remoteDataSource.getSingleLocation(id) },
            transform = { it }
        )

    override suspend fun getMultipleCharacters(ids: List<Int>): Flow<NetworkResponse<List<CharacterListItem>>> =
        safeApiCall(
            apiCall = { remoteDataSource.getMultipleCharacters(ids) },
            transform = { it -> it.map { it.toCharacterListItem() } }
        )

    override suspend fun searchCharacter(name: String): Flow<NetworkResponse<List<CharacterListItem>>> =
        safeApiCall(
            apiCall = { remoteDataSource.searchCharacter(name) },
            transform = { it -> it.results.map { it.toCharacterListItem() } }
        )
}