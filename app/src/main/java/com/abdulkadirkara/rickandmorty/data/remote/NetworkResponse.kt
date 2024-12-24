package com.abdulkadirkara.rickandmorty.data.remote

sealed class NetworkResponse<out T :Any?> {
    data class Success<out T:Any>(val result: T?): NetworkResponse<T>()
    data class Error(val exception:Exception): NetworkResponse<Nothing>()
}