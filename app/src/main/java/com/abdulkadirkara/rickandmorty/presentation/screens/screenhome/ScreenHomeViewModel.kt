package com.abdulkadirkara.rickandmorty.presentation.screens.screenhome

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkadirkara.rickandmorty.data.remote.onEmpty
import com.abdulkadirkara.rickandmorty.data.remote.onError
import com.abdulkadirkara.rickandmorty.data.remote.onLoading
import com.abdulkadirkara.rickandmorty.data.remote.onSuccess
import com.abdulkadirkara.rickandmorty.di.coroutines.DispatcherType
import com.abdulkadirkara.rickandmorty.di.coroutines.RickAndMortyDispatchers
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.usecase.GetAllCharactersUseCase
import com.abdulkadirkara.rickandmorty.domain.usecase.GetAllLocationsUseCase
import com.abdulkadirkara.rickandmorty.domain.usecase.GetSearchCharahctersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScreenHomeViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val getAllLocationsUseCase: GetAllLocationsUseCase,
    //private val getMultipleCharacterUseCase: GetMultipleCharacterUseCase,
    private val getSearchCharahctersUseCase: GetSearchCharahctersUseCase,
    @RickAndMortyDispatchers(DispatcherType.Main) private val mainDispatcher: CoroutineDispatcher,
    @RickAndMortyDispatchers(DispatcherType.Io) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _homeCharactersUiState = MutableLiveData<HomeCharactersUiState>()
    val homeCharactersUiState: LiveData<HomeCharactersUiState> = _homeCharactersUiState
    private var allCharacters : List<CharacterListItem>? = null

    var currentQuery: String? = null // Arama sorgusunu tutar
    var queryList = mutableListOf<String>() // Arama sorgusunu tutar
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
        viewModelScope.launch(ioDispatcher) {
            currentQuery = name
            delay(300)
            if (name == currentQuery){
                getSearchCharahctersUseCase.invoke(name).collect { it->
                    it.onLoading {
                        withContext(mainDispatcher){
                            _homeCharactersUiState.value = HomeCharactersUiState.Loading
                        }
                    }.onEmpty {
                        withContext(mainDispatcher) {
                            _homeCharactersUiState.value = HomeCharactersUiState.Empty
                        }
                    }.onError {
                        withContext(mainDispatcher) {
                            _homeCharactersUiState.value = HomeCharactersUiState.Error("Network Error")
                        }
                    }.onSuccess {
                        withContext(mainDispatcher) {
                            _homeCharactersUiState.value = HomeCharactersUiState.Success(it)
                        }
                    }
                }
            }
        }
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            getAllCharactersUseCase.invoke().collect { it ->
                it.onLoading {
                    _homeCharactersUiState.value = HomeCharactersUiState.Loading
                }.onEmpty {
                    _homeCharactersUiState.value = HomeCharactersUiState.Empty
                }.onError {
                    _homeCharactersUiState.value = HomeCharactersUiState.Error("Network Error")
                }.onSuccess {
                    _homeCharactersUiState.value = HomeCharactersUiState.Success(it)
                    allCharacters = it
                }
            }
        }
    }

    private fun getAllLocations() {
        viewModelScope.launch {
            getAllLocationsUseCase.invoke().collect { it ->
                it.onLoading {
                    _homeLocationUiState.value = HomeLocationUiState.Loading
                }.onEmpty {
                    _homeLocationUiState.value = HomeLocationUiState.Empty
                }.onError {
                    _homeLocationUiState.value = HomeLocationUiState.Error("Network Error")
                }.onSuccess {
                    _homeLocationUiState.value = HomeLocationUiState.Success(it)
                }
            }
        }
    }
}
