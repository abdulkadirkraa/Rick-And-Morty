package com.abdulkadirkara.rickandmorty.domain.model

data class LocationDetail(
    val id: Int,
    val name: String,
    val dimension: String,
    val residents: List<String>,
    val type: String,
)