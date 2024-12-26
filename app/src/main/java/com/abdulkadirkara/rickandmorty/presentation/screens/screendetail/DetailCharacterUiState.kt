package com.abdulkadirkara.rickandmorty.presentation.screens.screendetail

import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail

sealed class DetailCharacterUiState {
    data object Loading : DetailCharacterUiState()
    data class Success(val data: CharacterDetail) : DetailCharacterUiState()
    data class Error(val message: String) : DetailCharacterUiState()
}