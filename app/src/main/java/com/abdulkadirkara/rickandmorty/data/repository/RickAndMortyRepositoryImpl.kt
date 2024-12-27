package com.abdulkadirkara.rickandmorty.data.repository

import com.abdulkadirkara.rickandmorty.data.datasource.RemoteDataSource
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.data.remote.onEmpty
import com.abdulkadirkara.rickandmorty.data.remote.onError
import com.abdulkadirkara.rickandmorty.data.remote.onLoading
import com.abdulkadirkara.rickandmorty.data.remote.onSuccess
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toCharacterDetail
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toCharacterListItem
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toLocationListItem
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    //ioDispatcher burda da çağrılmalı mı sonuçta datastore'da onunla api'dan çağırdım??
) : RickAndMortyRepository {

    override suspend fun getAllCharacters(): Flow<NetworkResponse<List<CharacterListItem>>> = flow{
        emit(NetworkResponse.Loading)
        val response = remoteDataSource.getAllCharacters()
        response.onSuccess { dtos->
            val characters = dtos.results.map { it.toCharacterListItem() }
            emit(NetworkResponse.Success(characters))
        }.onEmpty {
            emit(NetworkResponse.Empty)
        }.onLoading {
            emit(NetworkResponse.Loading)
        }.onError {
            emit(it)
        }
    }

    override suspend fun getSingleCharacter(id: Int): Flow<NetworkResponse<CharacterDetail>> = flow {
        emit(NetworkResponse.Loading)
        val response = remoteDataSource.getSingleCharacter(id)
        response.onSuccess {
            val character = it.toCharacterDetail()
            emit(NetworkResponse.Success(character))
        }.onEmpty {
            emit(NetworkResponse.Empty)
        }.onLoading {
            emit(NetworkResponse.Loading)
        }.onError {
            emit(it)
        }
    }

    override suspend fun getAllLocations(): Flow<NetworkResponse<List<LocationListItem>>> = flow {
        emit(NetworkResponse.Loading)
        val response = remoteDataSource.getAllLocations()
        response.onSuccess { it ->
            val locations = it.results.map { it.toLocationListItem() }
            emit(NetworkResponse.Success(locations))
        }.onEmpty {
            emit(NetworkResponse.Empty)
        }.onLoading {
            emit(NetworkResponse.Loading)
        }.onError {
            emit(it)
        }
    }

    override suspend fun getSingleLocation(id: Int): Flow<NetworkResponse<Result>> = flow {
        emit(NetworkResponse.Loading)
        val response = remoteDataSource.getSingleLocation(id)
        response.onSuccess {
            emit(NetworkResponse.Success(it))
        }.onEmpty {
            emit(NetworkResponse.Empty)
        }.onLoading {
            emit(NetworkResponse.Loading)
        }.onError {
            emit(it)
        }
    }

    override suspend fun getMultipleCharacters(ids: List<Int>): Flow<NetworkResponse<List<CharacterListItem>>> = flow {
        emit(NetworkResponse.Loading)
        val response = remoteDataSource.getMultipleCharacters(ids)
        response.onSuccess { it ->
            val characters = it.map { it.toCharacterListItem() }
            emit(NetworkResponse.Success(characters))
        }.onEmpty {
            emit(NetworkResponse.Empty)
        }.onLoading {
            emit(NetworkResponse.Loading)
        }.onError {
            emit(it)
        }
    }

    override suspend fun searchCharacter(name: String): Flow<NetworkResponse<List<CharacterListItem>>> = flow {
        emit(NetworkResponse.Loading)
        val response = remoteDataSource.searchCharacter(name)
        response.onSuccess { it ->
            val characters = it.results.map { it.toCharacterListItem() }
            emit(NetworkResponse.Success(characters))
        }.onEmpty {
            emit(NetworkResponse.Empty)
        }.onLoading {
            emit(NetworkResponse.Loading)
        }.onError {
            emit(it)
        }
    }
}