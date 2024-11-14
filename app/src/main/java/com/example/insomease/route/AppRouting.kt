package com.example.insomease.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.insomease.view.HomeScreen
import com.example.insomease.view.SplashScreen

enum class listScreen(){
    SplashScreen,
    HomeScreen
}

@Composable
fun AppRouting(){

    val NavController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = NavController,
            startDestination = listScreen.SplashScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(route = listScreen.SplashScreen.name) {
                SplashScreen(onSplashFinish = {
                    NavController.navigate(listScreen.HomeScreen.name) {
                        popUpTo(listScreen.SplashScreen.name) { inclusive = true } // Clear the back stack to prevent going back to Splash
                    }
                })
            }

            composable(route = listScreen.HomeScreen.name) {
                HomeScreen(navController = NavController)
            }

//            ../RestaurantDetail/id
//            composable(
//                route = listScreen.RestaurantDetail.name + "/{id}",
//                arguments = listOf(
//                    navArgument("id") { type = NavType.IntType }
//                )
//            ) { backStackEntry ->
//                val id = backStackEntry.arguments?.getInt("id")
//                RestaurantDetailView(id!!)
//            }
        }
    }

}