package com.abdulkadirkara.rickandmorty.presentation.screens.screenhome

import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem

sealed class HomeCharactersUiState {
    data object Loading : HomeCharactersUiState()
    data class Success(val data: List<CharacterListItem>) : HomeCharactersUiState()
    data class Error(val message: String) : HomeCharactersUiState()
    data object Empty : HomeCharactersUiState()
}