package com.abdulkadirkara.rickandmorty.presentation.screens.screenhome

import android.net.Uri
import android.util.Log
import androidx.compose.material3.SearchBar
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.abdulkadirkara.rickandmorty.domain.model.CharacterListItem
import com.abdulkadirkara.rickandmorty.domain.model.LocationListItem
import com.abdulkadirkara.rickandmorty.presentation.navigation.Screens
import com.abdulkadirkara.rickandmorty.presentation.screens.component.ErrorComponent
import com.abdulkadirkara.rickandmorty.presentation.screens.component.LoadingComponent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ScreenHome(
    navController: NavController, viewModel: ScreenHomeViewModel,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val homeCharactersUiState = viewModel.homeCharactersUiState.observeAsState(HomeCharactersUiState.Loading)
    val homeLocationUiState = viewModel.homeLocationUiState.observeAsState(HomeLocationUiState.Loading)
    val currentQuery = viewModel.currentQuery // ViewModel'deki mevcut sorgu

    LaunchedEffect(Unit) {
        viewModel.searchBarActive.value = false // SearchBar'ın aktifliğini kapatıyoruz
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Characters") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding(),
                    start = 12.dp, end = 12.dp)
        ) {
            when (homeLocationUiState.value) {
                is HomeLocationUiState.Error -> {
                    val errorMessage = (homeLocationUiState.value as HomeLocationUiState.Error).message
                    ErrorComponent(errorMessage)
                }
                is HomeLocationUiState.Loading -> {
                    LoadingComponent()
                }
                is HomeLocationUiState.Success -> {
                    val data = (homeLocationUiState.value as HomeLocationUiState.Success).data
                    LocationsComponent(data)
                }
                is HomeLocationUiState.Empty -> {
                    ErrorComponent("No results found")
                }
            }
            SearchBar(
                query = currentQuery ?: "",
                onQueryChange = { viewModel.searchCharacter(it) },
                onClearQuery = { viewModel.clearSearch() },
                isActive = viewModel.searchBarActive.value,
                onActiveChange = { viewModel.searchBarActive.value = it },
                items = viewModel.queryList
            )
            when (homeCharactersUiState.value) {
                is HomeCharactersUiState.Error -> {
                    val errorMessage =
                        (homeCharactersUiState.value as HomeCharactersUiState.Error).message
                    ErrorComponent(errorMessage)
                }
                is HomeCharactersUiState.Loading -> {
                    LoadingComponent()
                }
                is HomeCharactersUiState.Success -> {
                    val data = (homeCharactersUiState.value as HomeCharactersUiState.Success).data
                    CharactersComponent(data, navController, animatedVisibilityScope)
                }
                is HomeCharactersUiState.Empty -> {
                    ErrorComponent("No results found")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit,
    items: List<String> = remember { mutableStateListOf() }
) {
    var text by remember { mutableStateOf(query) }

    SearchBar(
        query = text,
        onQueryChange = {
            text = it
            if (it.isNotEmpty()) onQueryChange(it)
        },
        onSearch = {
            if (text.isNotEmpty()) onQueryChange(text)
            onActiveChange(false) // Arama yapıldığında SearchBar kapanır
        },
        active = isActive,
        onActiveChange = {
            onActiveChange(it) // SearchBar'ın aktifliği kontrol ediliyor
        },
        placeholder = {
            Text(text = "Search Character")
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    text = ""
                },
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) { // Eğer arama metni boş değilse göster
                Icon(
                    modifier = Modifier.clickable {
                        text = ""
                        onClearQuery()
                    },
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Clear Icon"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        items.forEach {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.History,
                    contentDescription = "",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = it)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharactersComponent(
    data: List<CharacterListItem>,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .wrapContentSize()
            .padding(4.dp),
    ) {
        items(data.size) { index ->
            CharacterCard(character = data[index], animatedVisibilityScope) { characterId, image, name ->
                navController.navigate(
                    Screens.ScreenDetail.route + "/${characterId}/${Uri.encode(image)}}/${Uri.encode(name)}"
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterCard(
    character: CharacterListItem,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: (Int, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick(character.id, character.image, character.name) },
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier.padding(4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            state = rememberSharedContentState(key = "image/${character.image}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }
                        ),
                    contentScale = ContentScale.FillBounds,
                    model = character.image,
                    contentDescription = null,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .weight(20f)
                        .padding(start = 8.dp)
                        .clip(CircleShape)
                        .background(
                            color = when (character.status) {
                                "Alive" -> Color.Green
                                "Dead" -> Color.Red
                                else -> Color.Gray
                            }
                        )
                )
                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .weight(80f)
                        .sharedElement(
                            state = rememberSharedContentState(key = "name/${character.name}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }
                        ),
                    text = character.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun LocationsComponent(data: List<LocationListItem>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        items(data.size) { index ->
            LocationCard(data[index]) { locationId ->
                Log.e("location", getResidentIds(locationId).toString())
                //burda residetens'leri alıp multiplecharahter fonk ile çağırmalıyız
                //ama bir yandan da ana listeyi elde tutmalıyız
                //kullanıcı tıklarsa arasın tıklamazsa bir item'a ana liste gözüksün
                //ayrıca tıklananın görünüşü değişsin
            }
        }
    }
}

@Composable
fun LocationCard(location: LocationListItem, onClick: (LocationListItem) -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(4.dp)
            .clickable { onClick(location) },
        colors = CardDefaults.cardColors(
            contentColor = Color.Cyan
        ),
        shape = RoundedCornerShape(30f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = location.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(4.dp)
            )
            Card(
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(20f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = location.residentsCount.toString(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

fun getResidentIds(location: LocationListItem): List<Int> {
    return location.residents.map { it.split("/").last().toInt() }
}