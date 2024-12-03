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
import com.example.insomease.view.LoginScreenView
import com.example.insomease.view.OnBoardingScreen
import com.example.insomease.view.OnBoardingScreen_2
import com.example.insomease.view.OnBoardingScreen_3
import com.example.insomease.view.SplashScreen

enum class listScreen(){
    SplashScreen,
    OnBoardingScreen,
    OnBoardingScreen_2,
    OnBoardingScreen_3,
    LoginScreen,
    SignUpScreen,
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
                    NavController.navigate(listScreen.OnBoardingScreen.name) {
                        popUpTo(listScreen.SplashScreen.name) { inclusive = true } // Clear the back stack to prevent going back to Splash
                    }
                })
            }

            composable(route = listScreen.OnBoardingScreen.name) {
                OnBoardingScreen(navController = NavController)
            }

            composable(route = listScreen.OnBoardingScreen_2.name) {
                OnBoardingScreen_2(navController = NavController)
            }

            composable(route = listScreen.OnBoardingScreen_3.name) {
                OnBoardingScreen_3(navController = NavController)
            }

            composable(route = listScreen.LoginScreen.name) {
                LoginScreenView(navController = NavController)
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