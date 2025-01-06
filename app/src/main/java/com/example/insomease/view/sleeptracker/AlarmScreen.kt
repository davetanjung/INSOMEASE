package com.example.insomease.view.sleeptracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.insomease.R
import com.example.insomease.viewmodel.AlarmViewModel

@Composable
fun AlarmScreen(  alarmViewModel: AlarmViewModel = viewModel()) {
    val currentTime by alarmViewModel.currentTime.collectAsState()
    val alarmTime by alarmViewModel.alarmTime.collectAsState()
    val isAlarmTriggered by alarmViewModel.isAlarmTriggered.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Latar belakang gambar
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
            // Bagian teks di atas tombol
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Menjadikan area ini fleksibel
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Posisikan di tengah
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
                                    color = Color(0xFFACACE7), // Warna glow ungu cerah
                                    blurRadius = 30f,          // Radius blur lebih besar untuk efek glow
                                    offset = Offset(1f, 1f)    // Offset untuk memberikan kedalaman pada shadow
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

            // Spacer untuk memberikan jarak antara konten dan tombol
            Spacer(modifier = Modifier.height(90.dp))

            // Tombol di bagian bawah
            Button(
                onClick = {
                    // Aksi ketika tombol ditekan
                },
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
            onDismissRequest = { alarmViewModel.dismissAlarm() },
            confirmButton = {
                Button(
                    onClick = { alarmViewModel.dismissAlarm() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388))
                ) {
                    Text("Dismiss", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { alarmViewModel.snoozeAlarm() },
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
            containerColor = Color(0xFF111B38), // Background color (dark blue)
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(16.dp), // Rounded corners
            modifier = Modifier
                .padding(16.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFFB3BFFF), // Glow-like border color
                    shape = RoundedCornerShape(16.dp) // Same shape as container
                )
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AlarmPreview() {
    AlarmScreen()
}
