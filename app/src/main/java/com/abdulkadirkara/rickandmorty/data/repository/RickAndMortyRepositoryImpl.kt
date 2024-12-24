package com.abdulkadirkara.rickandmorty.data.repository

import android.util.Log
import com.abdulkadirkara.rickandmorty.data.datasource.RemoteDataSource
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
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

    override suspend fun getAllCharacters() : List<CharacterListItem> {
        Log.e(TAG, "getAllCharacters çağrıldı")
        return when (val response = remoteDataSource.getAllCharacters()) {
            is NetworkResponse.Success -> {
                Log.e(TAG, "getAllCharacters NetworkResponse.Succes")
                response.result!!.results.map { it.toCharacterListItem() } // Mapper kullanımı
            }
            is NetworkResponse.Error -> {
                Log.e(TAG, "getAllCharacters NetworkResponse.Error")
                throw response.exception // Hata yönetimini burada yapabilirsiniz
            }
        }
    }

    override suspend fun getSingleCharacter(id: Int): CharacterDetail {
        Log.e(TAG, "getSingleCharacter çağrıldı")
        val response = remoteDataSource.getSingleCharacter(id)
        when (response) {
            is NetworkResponse.Success -> {
                Log.e(TAG, "getSingleCharacter Network.Succes")
                return response.result!!.toCharacterDetail()
            }
            is NetworkResponse.Error -> {
                Log.e(TAG, "getSingleCharacter NetworkResponse.Error")
                throw response.exception
            }
        }
    }

    override suspend fun getAllLocations(): List<LocationListItem> {
        Log.e(TAG, "getAllLocations çağrıldı")
        return when (val response = remoteDataSource.getAllLocations()) {
            is NetworkResponse.Success -> {
                Log.e(TAG, "getAllLocations NetworkResponse.Succes")
                response.result!!.results.map { it.toLocationListItem() }
            }
            is NetworkResponse.Error -> {
                Log.e(TAG, "getAllLocations NetworkResponse.Error")
                throw response.exception
            }
        }
    }
}