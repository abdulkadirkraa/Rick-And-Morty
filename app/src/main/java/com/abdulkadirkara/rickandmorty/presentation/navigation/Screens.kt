package com.abdulkadirkara.rickandmorty.presentation.navigation

sealed class Screens (val route: String) {
    data object ScreenHome : Screens("home_screen")
    data object ScreenDetail : Screens("detail_screen")
}