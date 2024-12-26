package com.abdulkadirkara.rickandmorty.presentation.screens.screenhome

import android.widget.Toast
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
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHome(navController: NavController, viewModel: ScreenHomeViewModel){
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
            when(homeLocationUiState.value){
                is HomeLocationUiState.Error -> {
                    val errorMessage = (homeLocationUiState.value as HomeLocationUiState.Error).message
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

            SearchBarExample()

            when(homeCharactersUiState.value){
                is HomeCharactersUiState.Error -> {
                    val errorMessage = (homeCharactersUiState.value as HomeCharactersUiState.Error).message
                    ErrorComponent(errorMessage)
                }
                is HomeCharactersUiState.Loading -> {
                    LoadingComponent()
                }
                is HomeCharactersUiState.Success -> {
                    val data = (homeCharactersUiState.value as HomeCharactersUiState.Success).data
                    CharactersComponent(paddingValues, data, navController)
                }
                else -> {}
            }

        }
    }
}

@Composable
fun SearchBarExample() {
    // Arama durumu
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            isSearching = it.isNotEmpty() // Eğer arama boş değilse arama durumu aktif olur
        },
        label = { Text("Search Character") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        leadingIcon = {
            if (isSearching) {
                IconButton(onClick = {
                    searchQuery = "" // Arama sorgusunu temizle
                    isSearching = false // Arama durumunu sıfırla
                }) {
                    Icon(
                        Icons.Rounded.Close, // Close ikonu
                        contentDescription = "Clear Search"
                    )
                }
            } else {
                Icon(
                    Icons.Rounded.Search, // Search ikonu
                    contentDescription = "Search"
                )
            }
        },
        singleLine = true // Tek satırlık arama çubuğu
    )
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
fun LoadingComponent(){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
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

@Composable
fun CharactersComponent(paddingValues: PaddingValues, data: List<CharacterListItem>, navController: NavController){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .wrapContentSize()
            .padding(4.dp),
    ) {
        items(data.size) { index ->
            CharacterCard(character = data[index]) { characterId ->
                navController.navigate(Screens.ScreenDetail.route+"/${characterId}")
            }
        }
    }
}

@Composable
fun CharacterCard(character: CharacterListItem, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick(character.id) },
        shape = RoundedCornerShape(20.dp)
    ) {
        Column (modifier = Modifier.fillMaxSize()) {
            Card (
                modifier = Modifier
                    .padding(4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillBounds,
                    model = character.image,
                    contentDescription = null,
                )
            }

            Row (
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
                        .background(color = when(character.status){
                            "Alive" -> Color.Green
                            "Dead" -> Color.Red
                            else -> Color.Gray
                        })
                )
                Text(
                    modifier = Modifier.padding(4.dp)
                        .fillMaxWidth()
                        .weight(80f),
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
fun LocationsComponent(paddingValues: PaddingValues, data: List<LocationListItem>){
    val context = LocalContext.current
    LazyRow (
        modifier = Modifier.fillMaxWidth().padding(4.dp)
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
fun LocationCard(location: LocationListItem, onClick: (Int) -> Unit){
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(4.dp)
            .clickable { onClick(location.id) },
        colors = CardDefaults.cardColors(
            contentColor = Color.Cyan
        ),
        shape = RoundedCornerShape(30f)
    ){
        Row (
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