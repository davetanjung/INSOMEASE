package com.example.insomease.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.insomease.viewmodel.AlarmViewModel

@Composable
fun AlarmScreen(viewModel: AlarmViewModel = viewModel()) {
    val currentTime by viewModel.currentTime.collectAsState()
    val alarmTime by viewModel.alarmTime.collectAsState()
    val ambientNoise by viewModel.ambientNoise.collectAsState()

    // Tentukan AM atau PM
    val timeParts = currentTime.split(":")
    val hour = timeParts[0].toIntOrNull() ?: 0
    val period = if (hour in 0..11) "am" else "pm"

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
                text = "$currentTime $period",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Alarm $alarmTime",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Ambient Noise: $ambientNoise",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 16.sp,
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(90.dp))

            Button(
                onClick = { /* Add Quit Action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388)),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Quit",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AlarmPreview() {
    AlarmScreen()
}
