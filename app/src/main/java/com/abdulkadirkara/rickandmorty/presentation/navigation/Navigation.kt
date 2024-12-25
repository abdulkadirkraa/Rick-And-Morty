package com.abdulkadirkara.rickandmorty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abdulkadirkara.rickandmorty.presentation.screens.screendetail.ScreenDetail
import com.abdulkadirkara.rickandmorty.presentation.screens.screendetail.ScreenDetailViewModel
import com.abdulkadirkara.rickandmorty.presentation.screens.screenhome.ScreenHome
import com.abdulkadirkara.rickandmorty.presentation.screens.screenhome.ScreenHomeViewModel

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.ScreenHome.route){
        composable(route = Screens.ScreenHome.route) {
            val homeViewModel: ScreenHomeViewModel = hiltViewModel()
            ScreenHome(navController, homeViewModel)
        }
        composable(route = Screens.ScreenDetail.route + "/{id}",
            arguments = listOf(
                navArgument("id"){ type = NavType.IntType}
            )
        ) {
            val id = it.arguments?.getInt("id")!!
            val detailViewModel: ScreenDetailViewModel = hiltViewModel()
            ScreenDetail(id, detailViewModel)
        }
    }
}