package com.abdulkadirkara.rickandmorty.presentation.screens.screenhome

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import com.abdulkadirkara.rickandmorty.domain.usecase.GetAllCharactersUseCase
import com.abdulkadirkara.rickandmorty.domain.usecase.GetAllLocationsUseCase
import com.abdulkadirkara.rickandmorty.domain.usecase.GetMultipleCharacterUseCase
import com.abdulkadirkara.rickandmorty.domain.usecase.GetSearchCharahctersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenHomeViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val getAllLocationsUseCase: GetAllLocationsUseCase,
    private val getMultipleCharacterUseCase: GetMultipleCharacterUseCase,
    private val getSearchCharahctersUseCase: GetSearchCharahctersUseCase
) : ViewModel() {
    private val TAG = this::class.java.simpleName

    private val _homeCharactersUiState = MutableLiveData<HomeCharactersUiState>()
    val homeCharactersUiState: LiveData<HomeCharactersUiState> = _homeCharactersUiState
    var allCharacters : List<CharacterListItem>? = null

    var currentQuery: String? = null // Arama sorgusunu tutar
    var searchBarActive = mutableStateOf(false) // SearchBar'ın açık/kapalı durumunu kontrol eder

    private val _homeLocationUiState = MutableLiveData<HomeLocationUiState>()
    val homeLocationUiState: LiveData<HomeLocationUiState> = _homeLocationUiState

    init {
        getAllLocations()
        getAllCharacters()
    }

    fun clearSearch() {
        currentQuery = null
        getAllCharacters()
    }

    fun searchCharacter(name: String) {
        viewModelScope.launch {
            currentQuery = name
            Log.e(TAG, "searchCharacter was called")
            getSearchCharahctersUseCase.invoke(name).collect {
                when(it){
                    is NetworkResponse.Error -> {
                        Log.e(TAG,"searchCharacter Error")
                        _homeCharactersUiState.value = HomeCharactersUiState.Error("Network Error")
                    }
                    is NetworkResponse.Loading -> {
                        Log.e(TAG,"searchCharacter Loading")
                        _homeCharactersUiState.value = HomeCharactersUiState.Loading
                    }
                    is NetworkResponse.Success -> {
                        Log.e(TAG,"searchCharacter Success")
                        _homeCharactersUiState.value = HomeCharactersUiState.Success(it.result!!)
                    }
                }
            }
        }
    }

    fun getAllCharacters() {
        viewModelScope.launch {
            Log.e(TAG, "getAllCharacters was called")
            getAllCharactersUseCase.invoke().collect {
                when (it) {
                    is NetworkResponse.Error -> {
                        Log.e(TAG,"getAllCharacters Error")
                        _homeCharactersUiState.value = HomeCharactersUiState.Error("Network Error")
                    }
                    is NetworkResponse.Loading -> {
                        Log.e(TAG,"getAllCharacters Loading")
                        _homeCharactersUiState.value = HomeCharactersUiState.Loading
                    }
                    is NetworkResponse.Success -> {
                        Log.e(TAG,"getAllCharacters Success")
                        _homeCharactersUiState.value = HomeCharactersUiState.Success(it.result!!)
                        allCharacters = it.result
                    }
                }
            }
        }
    }

    fun getAllLocations() {
        Log.e(TAG, "getAllLocations was called")
        getAllLocationsUseCase.invoke().onEach {
            when(it){
                is NetworkResponse.Error -> {
                    Log.e(TAG,"getAllLocations Error")
                    _homeLocationUiState.value = HomeLocationUiState.Error("Network Error")
                }
                is NetworkResponse.Loading -> {
                    Log.e(TAG,"getAllLocations Loading")
                    _homeLocationUiState.value = HomeLocationUiState.Loading
                }
                is NetworkResponse.Success -> {
                    Log.e(TAG,"getAllLocations Success")
                    _homeLocationUiState.value = HomeLocationUiState.Success(it.result!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}
