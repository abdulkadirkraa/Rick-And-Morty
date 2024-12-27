package com.abdulkadirkara.rickandmorty.presentation.screens.screenhome

import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem

sealed class HomeLocationUiState {
    data object Loading : HomeLocationUiState()
    data class Success(val data: List<LocationListItem>) : HomeLocationUiState()
    data class Error(val message: String) : HomeLocationUiState()
    data object Empty : HomeLocationUiState()
}