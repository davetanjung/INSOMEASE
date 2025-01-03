package com.example.insomease.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.insomease.R
import com.example.insomease.viewmodel.GoodNightViewModel

@Composable
fun GoodNightScreen(viewModel: GoodNightViewModel = viewModel()) {

    val message by viewModel.message.collectAsState()
    val isTracking by viewModel.isTracking.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.sleep_tracker_screen),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (isTracking) {
                Text(
                    text = "Tracking your sleep...",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Thin,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            } else {
                Text(
                    text = "Keep the charger connected.",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Thin,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GoodNightScreenPreview() {
    GoodNightScreen()
}