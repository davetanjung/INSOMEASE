package com.example.insomease.route

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.example.insomease.view.RegisterScreenPreview
import com.example.insomease.view.RegisterScreenView
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

            composable(
                route = listScreen.OnBoardingScreen.name,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Starts from the right
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // Exits to the left
                        animationSpec = tween(durationMillis = 500)
                    )
                },
            ) {
                OnBoardingScreen(navController = NavController)
            }

            composable(
                route = listScreen.OnBoardingScreen_2.name,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Starts from the right
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // Exits to the left
                        animationSpec = tween(durationMillis = 500)
                    )
                },
            ) {
                OnBoardingScreen_2(navController = NavController)
            }

            composable(
                route = listScreen.OnBoardingScreen_3.name,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Starts from the right
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // Exits to the left
                        animationSpec = tween(durationMillis = 500)
                    )
                },
            ) {
                OnBoardingScreen_3(navController = NavController)
            }

            composable(
                route = listScreen.LoginScreen.name,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }, // Starts from the right
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth }, // Exits to the left
                        animationSpec = tween(durationMillis = 500)
                    )
                },
            ) {
                LoginScreenView(navController = NavController)
            }

            composable(route = listScreen.SignUpScreen.name) {
                RegisterScreenView(navController = NavController)
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