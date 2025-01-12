package com.example.insomease.route

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.insomease.data.UserPreferencesRepository
import com.example.insomease.models.ActivityUserModel
import com.example.insomease.view.home.HomePage
import com.example.insomease.view.authentication.LoginScreenView
import com.example.insomease.view.onboarding.OnBoardingScreen
import com.example.insomease.view.onboarding.OnBoardingScreen_2
import com.example.insomease.view.onboarding.OnBoardingScreen_3
import com.example.insomease.view.sleeptracker.WakeUpTimeScreen
import com.example.insomease.view.authentication.RegisterScreenView
import com.example.insomease.view.SplashScreen
import com.example.insomease.view.home.ActivityDetailCard
import com.example.insomease.view.sleeptracker.AlarmScreen
import com.example.insomease.view.sleeptracker.SleepNoteScreen
import com.example.insomease.viewModels.AuthenticationViewModel
import com.example.insomease.viewModels.HomePageViewModel
import com.example.insomease.viewmodel.AlarmViewModel
import com.example.insomease.viewmodel.AlarmViewModelFactory
import com.example.insomease.viewmodel.SleepNoteViewModel
import com.example.insomease.viewmodel.SleepNoteViewModelFactory
import com.example.insomease.viewmodel.WakeUpTimeViewModel
import com.example.insomease.viewmodel.WakeUpTimeViewModelFactory

//import com.example.insomease.viewmodel.WakeUpTimeViewModelFactory


enum class listScreen(){
    SplashScreen,
    OnBoardingScreen,
    OnBoardingScreen_2,
    OnBoardingScreen_3,
    LoginScreen,
    SignUpScreen,
    HomeScreen,
    ActivityDetailScreen,
    WakeUpTimeScreen,
    SleepNoteScreen,
    AlarmScreen
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppRouting(
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    homePageViewModel: HomePageViewModel = viewModel(factory = HomePageViewModel.Factory),
    userPreferencesRepository: UserPreferencesRepository,
    wakeUpTimeViewModel: WakeUpTimeViewModel = viewModel(factory = WakeUpTimeViewModelFactory(userPreferencesRepository)),
    sleepNoteViewModel: SleepNoteViewModel = viewModel(factory = SleepNoteViewModelFactory(userPreferencesRepository)),
    alarmViewModel: AlarmViewModel = viewModel(factory = AlarmViewModelFactory(userPreferencesRepository))


){
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
                LoginScreenView(
                    navController = NavController,
                    authenticationViewModel
                )
            }

            composable(
                route = listScreen.HomeScreen.name + "/{userId}",
                arguments = listOf(
                    navArgument("userId") { type = NavType.IntType } // `userId` as an integer argument
                )
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId")
                requireNotNull(userId) { "userId is required to navigate to HomeScreen" }

                HomePage(navController = NavController, homePageViewModel, userId)
            }

            composable(
                route = listScreen.ActivityDetailScreen.name + "/{userId}/{activityId}",
                arguments = listOf(
                    navArgument("userId") { type = NavType.IntType },
                    navArgument("activityId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId")
                val activityId = backStackEntry.arguments?.getInt("activityId")

                requireNotNull(userId) { "User ID is required" }
                requireNotNull(activityId) { "Activity ID is required" }

                // You can now pass both userId and activityId to the ActivityDetailCard
                ActivityDetailCard(
                    navController = NavController,
                    userId = userId,
                    activityId = activityId,
                    activity = ActivityUserModel(),  // You can fetch the activity data based on activityId if necessary
                    homePageViewModel
                )
            }


            composable(
                route = listScreen.SignUpScreen.name,
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
                RegisterScreenView(
                    navController = NavController,
                    authenticationViewModel
                )
            }

            composable(
                route = listScreen.SleepNoteScreen.name,
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
                SleepNoteScreen(
                    navController = NavController,
                    sleepNoteViewModel = sleepNoteViewModel,
                    homePageViewModel = homePageViewModel
                )
            }

            composable(
                route = listScreen.WakeUpTimeScreen.name,
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
                WakeUpTimeScreen(
                    navController = NavController,
                    viewModel = wakeUpTimeViewModel
                )
            }

            composable(
                route = listScreen.AlarmScreen.name,
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
                AlarmScreen(
                    navController = NavController,
                    viewModel = alarmViewModel,
                    homePageViewModel = homePageViewModel
                )
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