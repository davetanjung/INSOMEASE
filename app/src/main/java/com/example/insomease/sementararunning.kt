package com.example.insomease

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.insomease.view.sleeptracker.HistoryItem
import com.example.insomease.view.sleeptracker.SleepNoteScreen
import com.example.insomease.viewmodel.SleepNoteViewModel

@Composable
fun InsomeaseApp() {
    val navController = rememberNavController()
    val sleepNoteViewModel = SleepNoteViewModel() // You might want to use proper DI here

    NavHost(
        navController = navController,
        startDestination = "sleep_note"
    ) {
        composable("sleep_note") {
            SleepNoteScreen(
                sleepNoteViewModel = sleepNoteViewModel,
                onSaveSuccess = {
                    navController.navigate("history") {
                        popUpTo("sleep_note") { inclusive = false }
                    }
                }
            )
        }

        composable("history") {
            HistoryItem(sleepNoteViewModel = sleepNoteViewModel)
        }
    }
}