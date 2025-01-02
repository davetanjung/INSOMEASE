package com.example.insomease.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.insomease.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow

@Composable
fun SleepTrackerScreen(navController: NavController? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(90.dp))


            // Scrollable Time Picker
            TimePicker()

            Spacer(modifier = Modifier.height(90.dp))

            // Continue Button
            Button(
                onClick = { /* Add action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Continue",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Not now",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { /* Add action */ },

                style = TextStyle(
                    shadow = Shadow(
                        color = Color.White,
                        offset = Offset(0f, 0f),
                        blurRadius = 10f
                    )
                )
            )
        }
    }
}

@Composable
fun TimePicker() {
    var selectedHour by remember { mutableStateOf(8) }
    var selectedMinute by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hour Pick
        NumberScroller(
            range = 0..23,
            selectedValue = selectedHour,
            onValueChange = { selectedHour = it }
        )

        // Separator
        Text(
            text = ":",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Minute Pick
        NumberScroller(
            range = 0..59,
            selectedValue = selectedMinute,
            onValueChange = { selectedMinute = it }
        )
    }
}



@Composable
fun NumberScroller(
    range: IntRange,
    selectedValue: Int,
    onValueChange: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    // Update selected value based on the central position
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex + 1 }
            .collect { index ->
                if (index in range) {
                    onValueChange(index)
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
                val isInCenter = listState.layoutInfo.visibleItemsInfo.any {
                    it.index == value && it.offset == listState.layoutInfo.viewportStartOffset
                }

                Text(
                    text = value.toString().padStart(2, '0'),
                    color = if (value == selectedValue) Color.White else Color.Gray,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = if (isInCenter) 33.sp else 35.sp,
                    fontWeight = if (isInCenter) FontWeight.Normal else FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 1.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SleepTrackerPreview() {
    SleepTrackerScreen()
}