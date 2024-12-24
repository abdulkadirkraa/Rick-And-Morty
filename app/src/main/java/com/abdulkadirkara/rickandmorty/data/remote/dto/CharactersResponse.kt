package com.abdulkadirkara.rickandmorty.data.remote.dto

data class CharactersResponse(
    val info: Info,
    val results: List<CharacterResponse>
)