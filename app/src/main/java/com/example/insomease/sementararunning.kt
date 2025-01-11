package com.example.insomease

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.insomease.view.sleeptracker.SleepNoteScreen
import com.example.insomease.viewmodel.SleepNoteViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InsomeaseApp() {
    val navController = rememberNavController()
    val sleepNoteViewModel = SleepNoteViewModel() // Inisialisasi ViewModel

    NavHost(
        navController = navController,
        startDestination = "sleep_note"
    ) {
        composable("sleep_note") {
            // Mengambil data dari ViewModel
            val notes = sleepNoteViewModel.sleepNotes.collectAsState().value

            // Menampilkan SleepNoteScreen
            SleepNoteScreen(
                notes = notes, // Data sleep notes
                onSaveSuccess = {
                    // Navigasi dihapus karena tidak memerlukan page tambahan
                },
                sleepNoteViewModel = sleepNoteViewModel // Pastikan SleepNoteScreen menerima ViewModel
            )
        }
    }
}
