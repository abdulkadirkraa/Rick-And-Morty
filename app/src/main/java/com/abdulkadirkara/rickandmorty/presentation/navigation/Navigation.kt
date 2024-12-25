package com.abdulkadirkara.rickandmorty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abdulkadirkara.rickandmorty.presentation.screens.screendetail.ScreenDetail
import com.abdulkadirkara.rickandmorty.presentation.screens.screenhome.ScreenHome

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.ScreenHome.route){
        composable(route = Screens.ScreenHome.route) {
            ScreenHome(navController, viewModel = viewModel())
        }
        composable(route = Screens.ScreenDetail.route + "/{id}",
            arguments = listOf(
                navArgument("id"){ type = NavType.IntType}
            )
        ) {
            val id = it.arguments?.getInt("id")!!
            ScreenDetail(id, viewModel = viewModel())
        }
    }
}