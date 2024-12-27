package com.abdulkadirkara.rickandmorty.data.datasource

import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.LocationResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.data.remote.service.ApiService
import com.abdulkadirkara.rickandmorty.di.coroutines.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {

    override suspend fun getAllCharacters(): NetworkResponse<CharactersResponse> = withContext(ioDispatcher){
        try {
            val response = apiService.getAllCharacters()
            if (response.results.isNotEmpty()){
                NetworkResponse.Success(response)
            } else{
                NetworkResponse.Empty
            }
        } catch (e: HttpException) {
            NetworkResponse.Error.HttpError(e)
        } catch (e: IOException) {
            NetworkResponse.Error.NetworkError(e)
        } catch (e: Exception) {
            NetworkResponse.Error.UnknownError(e)
        }
    }

    override suspend fun getSingleCharacter(id: Int): NetworkResponse<CharacterResponse> = withContext(ioDispatcher) {
        return@withContext try {
            val response = apiService.getSingleCharacter(id)
            NetworkResponse.Success(response)
        } catch (e: HttpException) {
            NetworkResponse.Error.HttpError(e)
        } catch (e: IOException) {
            NetworkResponse.Error.NetworkError(e)
        } catch (e: Exception) {
            NetworkResponse.Error.UnknownError(e)
        }
    }

    override suspend fun getAllLocations(): NetworkResponse<LocationResponse> = withContext(ioDispatcher) {
        try {
            val response = apiService.getAllLocations()
            if (response.results.isNotEmpty()){
                NetworkResponse.Success(response)
            } else{
                NetworkResponse.Empty
            }
        } catch (e: HttpException) {
            NetworkResponse.Error.HttpError(e)
        } catch (e: IOException) {
            NetworkResponse.Error.NetworkError(e)
        } catch (e: Exception) {
            NetworkResponse.Error.UnknownError(e)
        }
    }

   override suspend fun getSingleLocation(id: Int): NetworkResponse<Result> = withContext(ioDispatcher) {
        try {
            val response = apiService.getSingleLocation(id)
            NetworkResponse.Success(response)
        } catch (e: HttpException) {
            NetworkResponse.Error.HttpError(e)
        } catch (e: IOException) {
            NetworkResponse.Error.NetworkError(e)
        } catch (e: Exception) {
            NetworkResponse.Error.UnknownError(e)
        }
    }

    override suspend fun getMultipleCharacters(ids: List<Int>): NetworkResponse<List<CharacterResponse>> = withContext(ioDispatcher){
        try {
            val response = apiService.getMultipleCharacters(ids)
            if (response.isNotEmpty()){
                NetworkResponse.Success(response)
            } else{
                NetworkResponse.Empty
            }
        }  catch (e: HttpException) {
            NetworkResponse.Error.HttpError(e)
        } catch (e: IOException) {
            NetworkResponse.Error.NetworkError(e)
        } catch (e: Exception) {
            NetworkResponse.Error.UnknownError(e)
        }
    }

    override suspend fun searchCharacter(name: String): NetworkResponse<CharactersResponse> = withContext(ioDispatcher) {
        try {
            val response = apiService.searchCharacter(name)
            if (response.results.isNotEmpty()){
                NetworkResponse.Success(response)
            } else{
                NetworkResponse.Empty
            }
        } catch (e: HttpException) {
            NetworkResponse.Error.HttpError(e)
        } catch (e: IOException) {
            NetworkResponse.Error.NetworkError(e)
        } catch (e: Exception) {
            NetworkResponse.Error.UnknownError(e)
        }
    }
}