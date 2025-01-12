package com.example.insomease

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.insomease.data.UserPreferencesRepository
import com.example.insomease.route.AppRouting
import com.example.insomease.ui.theme.INSOMEASETheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            INSOMEASETheme {

                val userPreferencesRepository = UserPreferencesRepository(this)

                setContent {
                    INSOMEASETheme {
                        AppRouting(userPreferencesRepository = userPreferencesRepository)
                    }
                }
            }
        }
    }
}

