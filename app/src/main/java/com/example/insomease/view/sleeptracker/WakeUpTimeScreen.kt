package com.example.insomease.view.sleeptracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.insomease.R
import com.example.insomease.route.listScreen
import com.example.insomease.viewmodel.WakeUpTimeViewModel

@Composable
fun WakeUpTimeScreen(
    navController: NavController? = null,
    viewModel: WakeUpTimeViewModel
) {
    val wakeUpTime by viewModel.wakeUpTime.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.home_background),
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
            // Title
            Text(
                text = "Set your wake up time",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.poppins)),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(90.dp))

            // Scrollable Time Picker
            TimePicker(
                initialHour = wakeUpTime.split(":")[0].toInt(),
                initialMinute = wakeUpTime.split(":")[1].toInt(),
                onTimeSelected = { hour, minute ->
                    val formattedTime = String.format("%02d:%02d", hour, minute)
                    viewModel.setWakeUpTime(formattedTime)
                }
            )

            Spacer(modifier = Modifier.height(90.dp))

            Button(
                onClick = {
                    // Save wake up time and navigate to alarm screen
                    viewModel.setWakeUpTime(wakeUpTime)
                    navController?.navigate(listScreen.AlarmScreen.name)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Continue",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Not now",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.poppins)),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { navController?.popBackStack() }
            )
        }
    }
}


@Composable
fun TimePicker(
    initialHour: Int, initialMinute: Int, onTimeSelected: (Int, Int) -> Unit
) {
    var selectedHour by remember { mutableIntStateOf(initialHour) }
    var selectedMinute by remember { mutableIntStateOf(initialMinute) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hour Pick
        NumberScroller(range = 0..23, selectedValue = selectedHour, onValueChange = {
            selectedHour = it
            onTimeSelected(selectedHour, selectedMinute)
        })

        // Separator
        Text(
            text = ":",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Minute Pick
        NumberScroller(range = 0..59, selectedValue = selectedMinute, onValueChange = {
            selectedMinute = it
            onTimeSelected(selectedHour, selectedMinute)
        })
    }
}


@Composable
fun NumberScroller(
    range: IntRange, selectedValue: Int, onValueChange: (Int) -> Unit
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedValue)

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex + 1 }.collect { centeredIndex ->
            if (centeredIndex in range) {
                onValueChange(centeredIndex)
            }
        }
    }

    Box(
        modifier = Modifier
            .width(80.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0D1527))
            .padding(8.dp)
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(range.toList()) { value ->
                val isSelected = value == selectedValue

                Text(
                    text = value.toString().padStart(2, '0'),
                    color = if (isSelected) Color.White else Color.Gray,
                    fontSize = if (isSelected) 40.sp else 38.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Overlay untuk separator tengah
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.Center)
                .background(Color.Transparent)
        )
    }
}