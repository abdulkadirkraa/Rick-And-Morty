package com.abdulkadirkara.rickandmorty.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseRepository {

    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> NetworkResponse<T>,
        transform: (T) -> R
    ): Flow<NetworkResponse<R>> = flow {
        emit(NetworkResponse.Loading)
        val response = apiCall()
        response.onSuccess { data ->
            emit(NetworkResponse.Success(transform(data)))
        }.onEmpty {
            emit(NetworkResponse.Empty)
        }.onLoading {
            emit(NetworkResponse.Loading)
        }.onError {
            emit(it)
        }
    }
}
