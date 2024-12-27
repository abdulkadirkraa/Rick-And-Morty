package com.abdulkadirkara.rickandmorty.data.datasource

import android.util.Log
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
    private val TAG = this::class.java.simpleName

    override suspend fun getAllCharacters(): NetworkResponse<CharactersResponse> = withContext(ioDispatcher){
        try {
            val response = apiService.getAllCharacters()
            Log.e(TAG, "getAllCharacters is success")
            NetworkResponse.Success(response)
        } catch (e: HttpException) { // Retrofit'in HttpException sınıfı
            Log.e(TAG, "getAllCharacters HttpException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("API Error: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) { // Ağ bağlantısı hataları
            Log.e(TAG, "getAllCharacters IOException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Network Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Log.e(TAG, "getAllCharacters Exception: ${e.localizedMessage}")
            NetworkResponse.Error(e)
        }
    }

    override suspend fun getSingleCharacter(id: Int): NetworkResponse<CharacterResponse> = withContext(ioDispatcher) {
        return@withContext try {
            val response = apiService.getSingleCharacter(id)
            Log.e(TAG, "getSingleCharacter is success")
            NetworkResponse.Success(response)
        } catch (e: HttpException) {
            Log.e(TAG, "getSingleCharacter HttpException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("API Error: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) {
            Log.e(TAG, "getSingleCharacter IOException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Network Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Log.e(TAG, "getSingleCharacter Exception: ${e.localizedMessage}")
            NetworkResponse.Error(e)
        }
    }

    override suspend fun getAllLocations(): NetworkResponse<LocationResponse> = withContext(ioDispatcher) {
        try {
            val response = apiService.getAllLocations()
            Log.e(TAG, "getAllLocations is success")
            NetworkResponse.Success(response)
        } catch (e: HttpException) {
            Log.e(TAG, "getAllLocations HttpException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("API Error: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) {
            Log.e(TAG, "getAllLocations IOException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Network Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Log.e(TAG, "getAllLocations Exception: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Unkonwn Exception: ${e.localizedMessage}"))
        }
    }

    override suspend fun getSingleLocation(id: Int): NetworkResponse<Result> = withContext(ioDispatcher) {
        try {
            val response = apiService.getSingleLocation(id)
            Log.e(TAG, "getSingleLocation is success")
            NetworkResponse.Success(response)
        } catch (e: HttpException) {
            Log.e(TAG, "getAllLocations HttpException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("API Error: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) {
            Log.e(TAG, "getAllLocations IOException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Network Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Log.e(TAG, "getAllLocations Exception: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Unkonwn Exception: ${e.localizedMessage}"))
        }
    }

    override suspend fun getMultipleCharacters(ids: List<Int>): NetworkResponse<List<CharacterResponse>> = withContext(ioDispatcher){
        try {
            val response = apiService.getMultipleCharacters(ids)
            Log.e(TAG, "getMultipleCharacters is success")
            NetworkResponse.Success(response)
        }  catch (e: HttpException) {
            Log.e(TAG, "getAllLocations HttpException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("API Error: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) {
            Log.e(TAG, "getAllLocations IOException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Network Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Log.e(TAG, "getAllLocations Exception: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Unkonwn Exception: ${e.localizedMessage}"))
        }
    }

    override suspend fun searchCharacter(name: String): NetworkResponse<CharactersResponse> = withContext(ioDispatcher) {
        try {
            val response = apiService.searchCharacter(name)
            Log.e(TAG, "searchCharacter is success")
            NetworkResponse.Success(response)
        } catch (e: HttpException) {
            Log.e(TAG, "getAllLocations HttpException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("API Error: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) {
            Log.e(TAG, "getAllLocations IOException: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Network Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Log.e(TAG, "getAllLocations Exception: ${e.localizedMessage}")
            NetworkResponse.Error(Exception("Unkonwn Exception: ${e.localizedMessage}"))
        }
    }
}