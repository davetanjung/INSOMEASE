package com.example.insomease.view.sleeptracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.insomease.viewmodel.SleepNoteViewModel
import androidx.compose.ui.text.TextStyle


@Composable
fun SleepNoteScreen(
    viewModel: SleepNoteViewModel = viewModel(),
    onSaveComplete: (Boolean) -> Unit = {}
) {
    val bedTime by viewModel.bedTime.collectAsState()
    val wakeTime by viewModel.wakeTime.collectAsState()
    val selectedFeeling by viewModel.selectedFeeling.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1631))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(90.dp))

        // Title
        Text(
            text = "Add Sleep Note",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sleep Time Section
        SleepTimeSection(
            bedTime = bedTime,
            wakeTime = wakeTime,
            onBedTimeChange = { viewModel.setBedTime(it) },
            onWakeTimeChange = { viewModel.setWakeTime(it) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Feelings Section
        Text(
            text = "How do you feel right now?",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        FeelingGrid(selectedFeeling, onFeelingSelected = { viewModel.selectFeeling(it) })

        Spacer(modifier = Modifier.height(32.dp))

        // Activities Section
        Text(
            text = "Any pre-sleep activities?",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Refreshment Rating
        Text(
            text = "How refreshed did you feel after sleep?",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Save Button
        Button(
            onClick = {
                viewModel.saveSleepNote(
                    token = "your_token_here", // Replace with actual token
                    userId = 1, // Replace with actual user ID
                    onComplete = onSaveComplete
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388)),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Done",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        BottomNavigationBar()
    }
}


@Composable
fun FeelingGrid(selectedFeeling: String, onFeelingSelected: (String) -> Unit) {
    val feelings = listOf("Stress", "Happy", "Calm", "Tired", "Anxious", "Neutral")
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (row in feelings.chunked(3)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                row.forEach { feeling ->
                    FeelingItem(
                        feeling = feeling,
                        isSelected = feeling == selectedFeeling,
                        onFeelingSelected = onFeelingSelected
                    )
                }
            }
        }
    }
}

@Composable
fun FeelingItem(feeling: String, isSelected: Boolean, onFeelingSelected: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { onFeelingSelected(feeling) }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = if (isSelected) Color(0xFF514388) else Color(0xFF1C3365),
                    shape = CircleShape
                )
        ) {
            Text(
                text = "😣",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = feeling,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0D1527))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.house), // Replace with actual icon
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
                painter = painterResource(R.drawable.bed),
                contentDescription = "sleep tracker",
                modifier = Modifier.size(24.dp)
            )
            Text(
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
            Text(
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
            Text(
                text = "Profile",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }
}


@Composable
fun SleepTimeSection(
    bedTime: String,
    wakeTime: String,
    onBedTimeChange: (String) -> Unit,
    onWakeTimeChange: (String) -> Unit
) {
    Column {
        Text(
            text = "Sleep Time",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TimeInputField(
                label = "Bed Time",
                value = bedTime,
                onValueChange = onBedTimeChange
            )
            Spacer(modifier = Modifier.width(16.dp))
            TimeInputField(
                label = "Wake Time",
                value = wakeTime,
                onValueChange = onWakeTimeChange
            )
        }
    }
}

@Composable
fun TimeInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        // Label
        Text(
            text = label,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = 14.sp,
            color = Color.White
        )

        // Input Field
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1C3365), RoundedCornerShape(8.dp))
                .padding(16.dp), // Add padding for better appearance
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins))
            ),
            decorationBox = { innerTextField ->
                Column {
                    if (value.isEmpty()) {
                        Text(
                            text = "Enter time...",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins))
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SleepNotePreview() {
    SleepNoteScreen(
    onSaveComplete = {}
    )
}
