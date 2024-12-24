package com.abdulkadirkara.rickandmorty.data.repository

import android.util.Log
import com.abdulkadirkara.rickandmorty.data.datasource.RemoteDataSource
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.LocationResponse
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toCharacterDetail
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toCharacterListItem
import com.abdulkadirkara.rickandmorty.domain.mapper.Mapper.toLocationListItem
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import com.abdulkadirkara.rickandmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    //ioDispatcher burda da çağrılmalı mı sonuçta datastore'da onunla api'dan çağırdım??
) : RickAndMortyRepository {

    private val TAG = this::class.java.simpleName

    override suspend fun getAllCharacters() : NetworkResponse<CharactersResponse> {
        Log.e(TAG, "getAllCharacters çağrıldı")
        try {
            return remoteDataSource.getAllCharacters()
        } catch (e : Exception){
            Log.e(TAG,e.toString())
            return NetworkResponse.Error(e)
        }
    }

    override suspend fun getSingleCharacter(id: Int): NetworkResponse<CharacterResponse> {
        Log.e(TAG, "getSingleCharacter çağrıldı")
        try {
            return remoteDataSource.getSingleCharacter(id)
        } catch (e: Exception){
            Log.e(TAG,e.toString())
            return NetworkResponse.Error(e)
        }
    }

    override suspend fun getAllLocations(): NetworkResponse<LocationResponse> {
        Log.e(TAG, "getAllLocations çağrıldı")
        try {
            return remoteDataSource.getAllLocations()
        } catch (e: Exception){
            Log.e(TAG,e.toString())
            return NetworkResponse.Error(e)
        }
    }
}