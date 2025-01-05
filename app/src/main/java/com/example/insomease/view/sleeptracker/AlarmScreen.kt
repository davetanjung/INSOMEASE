package com.example.insomease.view.sleeptracker

import AlarmViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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


@Composable
fun AlarmScreen(viewModel: AlarmViewModel = viewModel()) {
    val currentTime by viewModel.currentTime.collectAsState()
    val alarmTime by viewModel.alarmTime.collectAsState()
    val isAlarmTriggered by viewModel.isAlarmTriggered.collectAsState()

    // State untuk mengontrol pop-up dialog
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isAlarmTriggered) {
        if (isAlarmTriggered) {
            showDialog = true
        }
    }

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
                text = "Alarm Time: $alarmTime",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(90.dp))

            Button(
                onClick = { /* Add Snooze Action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388)),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Snooze",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }

    // Pop-up dialog ketika alarm menyala
    if (showDialog) {
        AlarmPopup(
            onDismiss = {
                showDialog = false
                viewModel.dismissAlarm()
            },
            onSnooze = {
                showDialog = false
                viewModel.snoozeAlarm()
            }
        )
    }
}

@Composable
fun AlarmPopup(onDismiss: () -> Unit, onSnooze: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Wake Up!",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = "It's time to wake up!",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388))
            ) {
                Text(
                    text = "Dismiss",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    color = Color.White
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onSnooze,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(
                    text = "Snooze",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    color = Color.White
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color(0xFF0D1527),
        textContentColor = Color.White,
        titleContentColor = Color.White
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AlarmPreview() {
    AlarmScreen()
}
