package com.abdulkadirkara.rickandmorty.data.repository

import android.util.Log
import com.abdulkadirkara.rickandmorty.data.datasource.RemoteDataSource
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.LocationResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    //ioDispatcher burda da çağrılmalı mı sonuçta datastore'da onunla api'dan çağırdım??
) : RickAndMortyRepository {

    private val TAG = this::class.java.simpleName

    override suspend fun getAllCharacters(): NetworkResponse<CharactersResponse> {
        Log.e(TAG, "getAllCharacters was called")
        try {
            return remoteDataSource.getAllCharacters()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            return NetworkResponse.Error(e)
        }
    }

    override suspend fun getSingleCharacter(id: Int): NetworkResponse<CharacterResponse> {
        Log.e(TAG, "getSingleCharacter was called")
        try {
            return remoteDataSource.getSingleCharacter(id)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            return NetworkResponse.Error(e)
        }
    }

    override suspend fun getAllLocations(): NetworkResponse<LocationResponse> {
        Log.e(TAG, "getAllLocations was called")
        try {
            return remoteDataSource.getAllLocations()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            return NetworkResponse.Error(e)
        }
    }

    override suspend fun getSingleLocation(id: Int): NetworkResponse<Result> {
        Log.e(TAG, "getSingleLocation was called")
        try {
            return remoteDataSource.getSingleLocation(id)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            return NetworkResponse.Error(e)
        }
    }

    override suspend fun getMultipleCharacters(ids: List<Int>): NetworkResponse<List<CharacterResponse>> {
        Log.e(TAG, "getMultipleCharacters was called")
        try {
            return remoteDataSource.getMultipleCharacters(ids)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            return NetworkResponse.Error(e)
        }
    }

    override suspend fun searchCharacter(name: String): NetworkResponse<CharactersResponse> {
        Log.e(TAG, "searchCharacter was called")
        try {
            return remoteDataSource.searchCharacter(name)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            return NetworkResponse.Error(e)
        }
    }
}