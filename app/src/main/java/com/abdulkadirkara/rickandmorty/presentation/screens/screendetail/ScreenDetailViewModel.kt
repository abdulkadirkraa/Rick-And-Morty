package com.abdulkadirkara.rickandmorty.presentation.screens.screendetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulkadirkara.rickandmorty.data.remote.NetworkResponse
import com.abdulkadirkara.rickandmorty.domain.usecase.GetSingleCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenDetailViewModel @Inject constructor(
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase
) : ViewModel(){
    private val TAG = this::class.java.simpleName

    private val _detailCharacterUiState = MutableLiveData<DetailCharacterUiState>()
    val detailCharacterUiState: LiveData<DetailCharacterUiState> = _detailCharacterUiState

    fun getSingleCharacter(id : Int){
        viewModelScope.launch {
            getSingleCharacterUseCase.invoke(id).collect{
                when(it){
                    is NetworkResponse.Error -> {
                        Log.e(TAG,"getSingleCharacter Error")
                        _detailCharacterUiState.value = DetailCharacterUiState.Error("Network Error")
                    }
                    is NetworkResponse.Loading -> {
                        Log.e(TAG,"getSingleCharacter Loading")
                        _detailCharacterUiState.value = DetailCharacterUiState.Loading
                    }
                    is NetworkResponse.Success -> {
                        Log.e(TAG,"getSingleCharacter Success")
                        _detailCharacterUiState.value = DetailCharacterUiState.Success(it.result!!)
                    }
                }
            }
        }
    }

}