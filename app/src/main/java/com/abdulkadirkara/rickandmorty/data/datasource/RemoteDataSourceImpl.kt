package com.abdulkadirkara.rickandmorty.data.datasource

import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.LocationResponse
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
            if (response.results.isNotEmpty()) {
                NetworkResponse.Success(response)
            } else {
                NetworkResponse.Error(IllegalStateException("No characters found"))
            }
        } catch (e: HttpException) { // Retrofit'in HttpException sınıfı
            NetworkResponse.Error(Exception("API Error: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) { // Ağ bağlantısı hataları
            NetworkResponse.Error(Exception("Network Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun getSingleCharacter(id: Int): NetworkResponse<CharacterResponse> = withContext(ioDispatcher) {
        return@withContext try {
            val response = apiService.getSingleCharacter(id)
            NetworkResponse.Success(response)
        } catch (e: HttpException) { // Retrofit'in HttpException sınıfı
            NetworkResponse.Error(Exception("API Error: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) { // Ağ bağlantısı hataları
            NetworkResponse.Error(Exception("Network Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }

    override suspend fun getAllLocations(): NetworkResponse<LocationResponse> = withContext(ioDispatcher) {
        try {
            val response = apiService.getAllLocations()
            NetworkResponse.Success(response)
        } catch (e: HttpException) {
            NetworkResponse.Error(Exception("API Error: ${e.code()} - ${e.message()}"))
        } catch (e: IOException) {
            NetworkResponse.Error(Exception("Network Error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            NetworkResponse.Error(Exception("Unkonwn Exception: ${e.localizedMessage}"))
        }
    }
}