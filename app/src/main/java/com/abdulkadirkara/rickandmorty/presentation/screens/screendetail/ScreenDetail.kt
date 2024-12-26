package com.abdulkadirkara.rickandmorty.presentation.screens.screendetail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.abdulkadirkara.rickandmorty.domain.model.CharacterDetail
import com.abdulkadirkara.rickandmorty.presentation.screens.screenhome.ErrorComponent
import com.abdulkadirkara.rickandmorty.presentation.screens.screenhome.LoadingComponent
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ScreenDetail(id: Int,
                                       image: String,
                                       name: String,
                                       viewModel: ScreenDetailViewModel,
                                       animatedVisibilityScope: AnimatedVisibilityScope
) {
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
            CharacterDetailComponent(data, animatedVisibilityScope)
        }
        else -> {}
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterDetailComponent(data: CharacterDetail, animatedVisibilityScope: AnimatedVisibilityScope) {
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
                    .parallax(0.5f)
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/${data.image}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = {_, _ ->
                            tween(durationMillis = 1000)
                        }
                    ),
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
                    .sharedElement(
                        state = rememberSharedContentState(key = "name/${data.name}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = {_, _ ->
                            tween(durationMillis = 1000)
                        }
                    )
                    .pin() // Başlığı sabitler
            )
        }
    ) {
        val scrollState = rememberScrollState()
        // Detaylar İçeriği
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(scrollState)
        ) {
            // Properties Card
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column (
                    modifier = Modifier.fillMaxSize()
                ) {
                    //Divider ve Başlık
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.Gray,
                            modifier = Modifier.weight(30f).fillMaxWidth()
                        )
                        Text(
                            text = "Properties",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 4.dp).weight(30f).fillMaxWidth()
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.Gray,
                            modifier = Modifier.weight(30f).fillMaxWidth()
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card (
                            modifier = Modifier.weight(40f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Gender",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }

                        Card (
                            modifier = Modifier.weight(60f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = data.gender,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card (
                            modifier = Modifier.weight(40f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Species",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }

                        Card (
                            modifier = Modifier.weight(60f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = data.species,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedCard (
                            modifier = Modifier.weight(40f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Status",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }

                        Card (
                            modifier = Modifier.weight(60f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = when(data.status){
                                    "Alive" -> Color.Green
                                    "Dead" -> Color.Red
                                    else -> Color.LightGray
                                },
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = data.status,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card (
                            modifier = Modifier.weight(40f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Created At",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }

                        Card (
                            modifier = Modifier.weight(60f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = data.createdAt,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Where About Card
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column (
                    modifier = Modifier.fillMaxSize()
                ) {
                    //Divider ve Başlık
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.Gray,
                            modifier = Modifier.weight(30f).fillMaxWidth()
                        )
                        Text(
                            text = "Where About",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 4.dp).weight(30f).fillMaxWidth()
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color.Gray,
                            modifier = Modifier.weight(30f).fillMaxWidth()
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card (
                            modifier = Modifier.weight(40f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Origin",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }

                        Card (
                            modifier = Modifier.weight(60f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = data.originName,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card (
                            modifier = Modifier.weight(40f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Location",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }

                        Card (
                            modifier = Modifier.weight(60f).padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = data.locationName,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

