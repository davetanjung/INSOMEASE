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
    private lateinit var appContainer: AppContainer
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (applicationContext as LunaireApplication).container
        userPreferencesRepository = UserPreferencesRepository(this)
        setContent {
            INSOMEASETheme {
                setContent {
                    INSOMEASETheme {
                        AppRouting(
                            userPreferencesRepository = userPreferencesRepository,
                        appContainer = appContainer
                        )
                    }
                }
            }
        }
    }
}

