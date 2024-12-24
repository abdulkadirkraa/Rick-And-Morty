package com.abdulkadirkara.rickandmorty.data.remote

sealed class NetworkResponse<out T :Any?> {
    object Loading : NetworkResponse<Nothing>()
    data class Success<out T:Any>(val result: T?): NetworkResponse<T>()
    data class Error(val exception:Exception): NetworkResponse<Nothing>()
}
/*
Flow<NetworkResponse<List<DisneyCharactersEntity>>>
Flow<NetworkResponse<CharacterDetailEntity>>
Flow<NetworkResponse<List<DisneyCharactersEntity>>>
class GetDisneyCharacterUseCaseImpl @Inject constructor(
    private val disneyRepository: DisneyRepository,
    private val characterResponseDomainMapper: DisneyCharacterMapper<CharacterResponse, CharacterDetailEntity>
): GetDisneyCharacterUseCase {
    override fun invoke(id:Int): Flow<NetworkResponse<CharacterDetailEntity>> =  flow {
        emit(NetworkResponse.Loading)
        when(val response=disneyRepository.getDisneyCharacter(id)){
            is NetworkResponse.Error -> emit(response)
            is NetworkResponse.Loading -> Unit
            is NetworkResponse.Success -> emit(
                NetworkResponse.Success(characterResponseDomainMapper.map(response.result))
            )
        }
    }
}
-----
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val data: List<HomeUiData>) : HomeUiState()
    data class Error(@StringRes val message: Int) : HomeUiState()
}
-----
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSearchCharacterUseCase: GetSearchCharacterUseCase,
    private val disneyCharacterMapper:DisneyCharacterListMapper<DisneyCharactersEntity,HomeUiData>,
    private val getDisneyUseCase:GetDisneyUseCase
) : ViewModel() {

    private val _disneyHomeUiState=MutableLiveData<HomeUiState>()
    val disneyHomeUiState:LiveData<HomeUiState>  get() = _disneyHomeUiState
    var allDisneyCharacters: List<HomeUiData>? = null

    fun searchDisneyCharacters(name: String) {
        getSearchCharacterUseCase(nameText = name).onEach {
            when (it) {
                is NetworkResponse.Error -> {
                    _disneyHomeUiState.value = HomeUiState.Error(R.string.eror)
                }
                NetworkResponse.Loading -> {
                    _disneyHomeUiState.postValue(HomeUiState.Loading)
                }
                is NetworkResponse.Success -> {
                    _disneyHomeUiState.postValue(HomeUiState.Success(disneyCharacterMapper.map(it.result)))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDisneyCharacters(){
        getDisneyUseCase().onEach {
            when (it) {
                is NetworkResponse.Error -> {
                    _disneyHomeUiState.value = HomeUiState.Error(R.string.eror)
                }
                is NetworkResponse.Loading -> {
                    _disneyHomeUiState.value = HomeUiState.Loading
                }
                is NetworkResponse.Success -> {
                    val data = disneyCharacterMapper.map(it.result)
                    _disneyHomeUiState.value = HomeUiState.Success(data)
                    allDisneyCharacters = data
                }
            }
        }.launchIn(viewModelScope)
    }
}
-----
view
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewState()
        if (viewModel.allDisneyCharacters.isNullOrEmpty()){
            viewModel.getDisneyCharacters()
        }
        binding.searchEditText.setOnSearchListener {
            if (it.isBlank()) {
                viewModel.getDisneyCharacters()
            } else {
                viewModel.searchDisneyCharacters(it)
            }
        }
        binding.homeRcv.adapter = adapter
        binding.homeRcv.layoutManager = GridLayoutManager(requireContext(), 2)
    }


    private fun viewState() {
        viewModel.disneyHomeUiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeUiState.Error -> {
                    Toast.makeText(requireContext(), "Eror", Toast.LENGTH_SHORT).show()
                }
                is HomeUiState.Success -> {
                    handleUiState(it.data)
                    progressHide()
                }
                is HomeUiState.Loading -> {
                    progressShow()
                }
            }
        }
    }
 */