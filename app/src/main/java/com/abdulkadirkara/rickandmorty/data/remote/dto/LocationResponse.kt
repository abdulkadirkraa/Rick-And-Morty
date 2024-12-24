package com.abdulkadirkara.rickandmorty.data.remote.dto

data class LocationResponse(
    val info: Info,
    val results: List<Result>
)