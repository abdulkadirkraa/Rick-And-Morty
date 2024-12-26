package com.abdulkadirkara.rickandmorty.domain.model

data class LocationListItem(
    val id: Int,
    val name: String,
    val residentsCount: Int,
    val residents: List<String>,
)