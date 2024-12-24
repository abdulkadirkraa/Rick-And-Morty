package com.abdulkadirkara.rickandmorty.data.remote.service

import com.abdulkadirkara.rickandmorty.data.remote.dto.CharacterResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.CharactersResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.LocationResponse
import com.abdulkadirkara.rickandmorty.data.remote.dto.Result
import com.abdulkadirkara.rickandmorty.util.Constans
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(Constans.ALL_CHARACTERS_END_POINT)
    suspend fun getAllCharacters(): CharactersResponse

    @GET(Constans.SINGLE_CHARACTER_END_POINT)
    suspend fun getSingleCharacter(@Path("id") id: Int): CharacterResponse

    @GET(Constans.ALL_LOCATIONS_END_POINT)
    suspend fun getAllLocations(): LocationResponse

    @GET(Constans.SINGLE_LOCATION_END_POINT)
    suspend fun getSingleLocation(@Path("id") id: Int): Result

}