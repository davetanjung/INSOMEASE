package com.example.insomease.view.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.insomease.viewModels.HomePageViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ActivityPopUpCard2(
    homePageViewModel: HomePageViewModel,
    navController: NavController?
) {
    var date by remember { mutableStateOf(homePageViewModel.date) }
    var startTime by remember { mutableStateOf(homePageViewModel.start_time) }
    var endTime by remember { mutableStateOf(homePageViewModel.end_time) }
    val userId: Int = navController?.currentBackStackEntry?.arguments?.getInt("userId") ?: 0

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Date picker dialog
    fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Format date as yyyy/MM/dd
                date = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    // Time picker dialog
    fun showTimePicker(onTimeSelected: (String) -> Unit) {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                // Format time as HH:mm
                onTimeSelected(String.format("%02d:%02d", hourOfDay, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // Use 24-hour format
        )
        timePickerDialog.show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f))
            .wrapContentSize()
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.Center)
                .wrapContentHeight()
                .fillMaxWidth(0.8f),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF121538)),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add your Activity",
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Date picker field
                TextField(
                    value = date,
                    onValueChange = {
                        date = it
                        homePageViewModel.updateDate(date)
                    },
                    readOnly = true,
                    enabled = false,
                    placeholder = { Text("Select date (YYYY/MM/DD)", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1C1F46), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.White,
                        disabledContainerColor = Color(0xFF1C1F46),
                        disabledPlaceholderColor = Color.White.copy(alpha = 0.7f),
                    )
                )
                // Clickable overlay for date picker
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .offset(y = (-56).dp)
                        .clickable { showDatePicker() }
                )

//                Spacer(modifier = Modifier.height(16.dp))

                // Start time picker field
                TextField(
                    value = startTime,
                    onValueChange = {
                        startTime = it
                        homePageViewModel.updateStartTime(startTime)
                    },
                    readOnly = true,
                    enabled = false,
                    placeholder = { Text("Select start time (HH:MM)", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1C1F46), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.White,
                        disabledContainerColor = Color(0xFF1C1F46),
                        disabledPlaceholderColor = Color.White.copy(alpha = 0.7f),
                    )
                )
                // Clickable overlay for start time picker
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .offset(y = (-56).dp)
                        .clickable { showTimePicker { startTime = it } }
                )

//                Spacer(modifier = Modifier.height(.dp))

                // End time picker field
                TextField(
                    value = endTime,
                    onValueChange = {
                        endTime = it
                        homePageViewModel.updateEndTime(endTime)
                    },
                    readOnly = true,
                    enabled = false,
                    placeholder = { Text("Select end time", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1C1F46), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.White,
                        disabledContainerColor = Color(0xFF1C1F46),
                        disabledPlaceholderColor = Color.White.copy(alpha = 0.7f),
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .offset(y = (-56).dp)
                        .clickable { showTimePicker { endTime = it } }
                )

//                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        homePageViewModel.createActivity(
                            token = homePageViewModel.token.value,
                            name = homePageViewModel.nameInput,
                            start_time = startTime,
                            end_time = endTime,
                            date = date,
                            categoryId = homePageViewModel.categoryId,
                            userId = userId
                        )
                        homePageViewModel.toggleNextPopUp()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6954DE),
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        "Submit",
                        modifier = Modifier.padding(horizontal = 32.dp),
                    )
                }
            }
        }
    }
}


