package com.abdulkadirkara.rickandmorty.domain.model

data class CharacterDetail(
    val id: Int,
    val name: String,
    val image: String,
    val species: String,
    val status: String,
    val gender: String,
    val originName: String,
    val locationName: String,
    val episodes: List<String>,
    val createdAt: String
)