package com.example.insomease.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insomease.R

@Composable
fun BottomNavigationBar(
    modifier: Modifier,
    currentScreen: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0D1527))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(
                    if (currentScreen == "home")
                        R.drawable.house_selected
                    else
                        R.drawable.house
                ),
                contentDescription = "home",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Home",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 12.sp,
                color = Color.White
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(
                    if (currentScreen == "sleep")
                        R.drawable.bed
                    else
                        R.drawable.sleep
                ),
                contentDescription = "sleep tracker",
                modifier = Modifier.size(24.dp)
            )
            androidx.compose.material3.Text(
                text = "Sleep",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 12.sp,
                color = Color.White
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.brain), // Replace with actual icon
                contentDescription = "relax",
                modifier = Modifier.size(24.dp)
            )
            androidx.compose.material3.Text(
                text = "Relax",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 12.sp,
                color = Color.White
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.person_3_sequence), // Replace with actual icon
                contentDescription = "Profile",
                modifier = Modifier.size(24.dp)
            )
            androidx.compose.material3.Text(
                text = "Profile",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }
}