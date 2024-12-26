package com.abdulkadirkara.rickandmorty.presentation.screens.screendetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.presentation.screens.screenhome.ErrorComponent
import com.abdulkadirkara.rickandmorty.presentation.screens.screenhome.LoadingComponent
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@Composable
fun ScreenDetail(id: Int, viewModel: ScreenDetailViewModel) {
    val characterDetailUiState = viewModel.detailCharacterUiState.observeAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getSingleCharacter(id)
    }

    when (characterDetailUiState.value) {
        is DetailCharacterUiState.Error -> {
            val errorMessage = (characterDetailUiState.value as DetailCharacterUiState.Error).message
            ErrorComponent(errorMessage)
        }
        is DetailCharacterUiState.Loading -> {
            LoadingComponent()
        }
        is DetailCharacterUiState.Success -> {
            val data = (characterDetailUiState.value as DetailCharacterUiState.Success).data
            CharacterDetailComponent(data)
        }
        else -> {}
    }
}

@Composable
fun CharacterDetailComponent(data: CharacterDetail) {
    val state = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            // Toolbar Arkaplanı
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .pin()
                    .background(color = MaterialTheme.colorScheme.primary)
            )

            // Resim
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .parallax(0.5f),
                model = data.image,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            // Başlık
            Text(
                text = data.name,
                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                modifier = Modifier
                    .padding(16.dp)
                    .road(
                        whenCollapsed = Alignment.TopStart,
                        whenExpanded = Alignment.BottomStart
                    )
                    .pin() // Başlığı sabitler
            )
        }
    ) {
        // Detaylar İçeriği
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = "ID: ${data.id}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
                        Text(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = "Gender: ${data.gender}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
                        Text(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = "Created at: ${data.createdAt}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
                        Text(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = "Location: ${data.locationName}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
                        Text(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = "Origin: ${data.originName}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
                        Text(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = "Species: ${data.species}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
                        Text(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = "Status: ${data.status}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(thickness = 2.dp, color = Color.Gray)
                        Text(
                            text = "Episodes: ${data.episodes}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

