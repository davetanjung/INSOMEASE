package com.example.insomease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.insomease.ui.theme.INSOMEASETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            INSOMEASETheme {
                InsomeaseApp()
            }
        }
    }
}

