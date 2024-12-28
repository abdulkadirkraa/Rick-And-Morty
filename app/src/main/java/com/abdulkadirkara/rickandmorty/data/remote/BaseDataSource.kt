package com.abdulkadirkara.rickandmorty.data.remote

import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.di.coroutines.DispatcherType
import com.abdulkadirkara.rickandmorty.di.coroutines.RickAndMortyDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class BaseDataSource {
    suspend fun <T> ioDispatcherCall(
        @RickAndMortyDispatchers(DispatcherType.Io) ioDispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T)
            : T {
        return withContext(ioDispatcher) { apiCall() }
    }

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResponse<T> {
        return try {
            val response = apiCall()
            if (response is List<*> && response.isEmpty() ||
                response is CharactersResponse && response.results.isEmpty()) {
                NetworkResponse.Empty
            } else {
                NetworkResponse.Success(response)
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