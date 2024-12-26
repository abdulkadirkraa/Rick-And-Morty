package com.abdulkadirkara.rickandmorty.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abdulkadirkara.rickandmorty.presentation.screens.screendetail.ScreenDetail
import com.abdulkadirkara.rickandmorty.presentation.screens.screendetail.ScreenDetailViewModel
import com.abdulkadirkara.rickandmorty.presentation.screens.screenhome.ScreenHome
import com.abdulkadirkara.rickandmorty.presentation.screens.screenhome.ScreenHomeViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation(){
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screens.ScreenHome.route){
            composable(route = Screens.ScreenHome.route) {
                val homeViewModel: ScreenHomeViewModel = hiltViewModel()
                ScreenHome(navController, homeViewModel, animatedVisibilityScope = this)
            }
            composable(route = Screens.ScreenDetail.route + "/{id}/{image}/{name}",
                arguments = listOf(
                    navArgument("id"){ type = NavType.IntType},
                    navArgument("image"){ type = NavType.StringType},
                    navArgument("name"){ type = NavType.StringType}
                )
            ) {
                val id = it.arguments?.getInt("id")!!
                val image = it.arguments?.getString("image")!!
                val name = it.arguments?.getString("name")!!
                val detailViewModel: ScreenDetailViewModel = hiltViewModel()
                ScreenDetail(id,image,name,detailViewModel, animatedVisibilityScope = this)
            }
        }
    }
}