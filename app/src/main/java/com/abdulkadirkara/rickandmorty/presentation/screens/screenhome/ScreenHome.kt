package com.abdulkadirkara.rickandmorty.presentation.screens.screenhome

import android.net.Uri
import androidx.compose.material3.SearchBar
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ScreenHome(
    navController: NavController, viewModel: ScreenHomeViewModel,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val homeCharactersUiState = viewModel.homeCharactersUiState.observeAsState()
    val homeLocationUiState = viewModel.homeLocationUiState.observeAsState()

    //bunun yerine init dene
    LaunchedEffect(Unit) {
        viewModel.getAllCharacters()
        viewModel.getAllLocations()
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
                .padding(top = paddingValues.calculateTopPadding(), start = 12.dp, end = 12.dp)
        ) {
            when (homeLocationUiState.value) {
                is HomeLocationUiState.Error -> {
                    val errorMessage =
                        (homeLocationUiState.value as HomeLocationUiState.Error).message
                    ErrorComponent(errorMessage)
                }

                is HomeLocationUiState.Loading -> {
                    LoadingComponent()
                }

                is HomeLocationUiState.Success -> {
                    val data = (homeLocationUiState.value as HomeLocationUiState.Success).data
                    LocationsComponent(paddingValues, data)
                }

                else -> {}
            }

            SearchBar()

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

                else -> {}
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var items = remember { mutableStateListOf<String>() }
    SearchBar(
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            items.add(text)
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
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
                contentDescription = ""
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        text = ""
                        if (text.isNotEmpty()) {
                        } else {
                            active = false
                        }
                    },
                    imageVector = Icons.Rounded.Close,
                    contentDescription = ""
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


@Composable
fun ErrorComponent(errorMessage: String) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AlertDialog(
            onDismissRequest = { /* Do nothing */ },
            title = {
                Text(text = "Hata", fontSize = 20.sp, color = Color.Red)
            },
            text = {
                Text(text = errorMessage, fontSize = 16.sp, color = Color.Gray)
            },
            confirmButton = {
                Button(onClick = {
                    // Uygulamayı kapatma işlemi
                    (context as? android.app.Activity)?.finish()
                }) {
                    Text(text = "Uygulamadan Çık")
                }
            },
//            dismissButton = {
//                Button(onClick = {
//                    // İsteğe bağlı: Dialog'ı kapatabilir veya başka bir işlem yapabilirsiniz
//                }) {
//                    Text(text = "Tamam")
//                }
//            },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(8.dp)
        )
    }
}


@Composable
fun LoadingComponent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp),
                color = Color.DarkGray,
                strokeWidth = 5.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Yükleniyor...", fontSize = 18.sp, color = Color.Gray)
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
                modifier = Modifier
                    .padding(4.dp),
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
                var color by remember { mutableStateOf(Color.Red) }

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
fun LocationsComponent(paddingValues: PaddingValues, data: List<LocationListItem>) {
    val context = LocalContext.current
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        items(data.size) { index ->
            LocationCard(data[index]) { locationId ->
                //Burda karakter listesini yenilemek gerekicek
                Toast.makeText(context, "Location : $locationId", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun LocationCard(location: LocationListItem, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(4.dp)
            .clickable { onClick(location.id) },
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