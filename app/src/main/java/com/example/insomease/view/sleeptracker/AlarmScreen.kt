package com.example.insomease.view.sleeptracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insomease.R
import com.example.insomease.viewmodel.AlarmViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmScreen(
    viewModel: AlarmViewModel
) {
    val currentTime by viewModel.currentTime.collectAsState()
    val alarmTime by viewModel.alarmTime.collectAsState()
    val isAlarmTriggered by viewModel.isAlarmTriggered.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.sleep_tracker_screen),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Text content at the top
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("It is ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFACACE7),
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(
                                    color = Color(0xFFACACE7),
                                    blurRadius = 30f,
                                    offset = Offset(1f, 1f)
                                )
                            )
                        ) {
                            append(currentTime)
                        }
                    },
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Alarm Time: $alarmTime",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Don't worry, we'll wake you up",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(90.dp))

            Button(
                onClick = { /* Handle quit action */ },
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

    if (isAlarmTriggered) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissAlarm() },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissAlarm() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388))
                ) {
                    Text("Dismiss", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.snoozeAlarm() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388))
                ) {
                    Text("Snooze", color = Color.White)
                }
            },
            text = {
                Text(
                    text = "It's time to wake up!",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            },
            title = {
                Text(
                    text = "Alarm Alert!",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            },
            containerColor = Color(0xFF111B38),
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFFB3BFFF),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}