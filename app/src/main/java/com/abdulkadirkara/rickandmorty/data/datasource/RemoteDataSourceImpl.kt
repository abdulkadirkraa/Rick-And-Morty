package com.abdulkadirkara.rickandmorty.data.datasource

import com.abdulkadirkara.rickandmorty.data.remote.BaseDataSource
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.LocationResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.data.remote.service.ApiService
import com.abdulkadirkara.rickandmorty.di.coroutines.DispatcherType
import com.abdulkadirkara.rickandmorty.di.coroutines.RickAndMortyDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    @RickAndMortyDispatchers(DispatcherType.Io) private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource, BaseDataSource() {

    override suspend fun getAllCharacters(): NetworkResponse<CharactersResponse> {
        return ioDispatcherCall(ioDispatcher) {
            safeApiCall { apiService.getAllCharacters() }
        }
    }

    override suspend fun getSingleCharacter(id: Int): NetworkResponse<CharacterResponse> = withContext(ioDispatcher) {
        return@withContext ioDispatcherCall(ioDispatcher) {
            safeApiCall { apiService.getSingleCharacter(id) }
        }
    }

    override suspend fun getAllLocations(): NetworkResponse<LocationResponse> = withContext(ioDispatcher) {
        return@withContext ioDispatcherCall(ioDispatcher) {
            safeApiCall { apiService.getAllLocations() }
        }
    }

   override suspend fun getSingleLocation(id: Int): NetworkResponse<Result> = withContext(ioDispatcher) {
        return@withContext ioDispatcherCall(ioDispatcher) {
            safeApiCall { apiService.getSingleLocation(id) }
        }
    }

    override suspend fun getMultipleCharacters(ids: List<Int>): NetworkResponse<List<CharacterResponse>> = withContext(ioDispatcher){
        return@withContext ioDispatcherCall(ioDispatcher) {
            safeApiCall { apiService.getMultipleCharacters(ids) }
        }
    }

    override suspend fun searchCharacter(name: String): NetworkResponse<CharactersResponse> = withContext(ioDispatcher) {
        return@withContext ioDispatcherCall(ioDispatcher) {
            safeApiCall { apiService.searchCharacter(name) }
        }
    }
}