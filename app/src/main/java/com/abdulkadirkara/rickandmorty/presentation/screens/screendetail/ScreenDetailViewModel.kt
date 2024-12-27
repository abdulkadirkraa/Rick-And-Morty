package com.abdulkadirkara.rickandmorty.presentation.screens.screendetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkadirkara.rickandmorty.data.remote.onEmpty
import com.abdulkadirkara.rickandmorty.data.remote.onError
import com.abdulkadirkara.rickandmorty.data.remote.onLoading
import com.abdulkadirkara.rickandmorty.data.remote.onSuccess
import com.abdulkadirkara.rickandmorty.domain.usecase.GetSingleCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenDetailViewModel @Inject constructor(
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase
) : ViewModel(){

    private val _detailCharacterUiState = MutableLiveData<DetailCharacterUiState>()
    val detailCharacterUiState: LiveData<DetailCharacterUiState> = _detailCharacterUiState

    fun getSingleCharacter(id : Int){
        viewModelScope.launch {
            getSingleCharacterUseCase.invoke(id).collect{ it ->
                it.onSuccess {
                    _detailCharacterUiState.value = DetailCharacterUiState.Success(it)
                }.onError {
                    _detailCharacterUiState.value = DetailCharacterUiState.Error("Network Error")
                }.onLoading {
                    _detailCharacterUiState.value = DetailCharacterUiState.Loading
                }.onEmpty {
                    _detailCharacterUiState.value = DetailCharacterUiState.Empty
                }
            }
        }
    }
}