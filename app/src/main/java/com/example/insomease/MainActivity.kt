package com.example.insomease

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.insomease.route.AppRouting
import com.example.insomease.ui.theme.INSOMEASETheme
import com.example.insomease.view.LoginScreenView
import com.example.insomease.view.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                INSOMEASETheme {
                   AppRouting()
                }
            }
    }
}

