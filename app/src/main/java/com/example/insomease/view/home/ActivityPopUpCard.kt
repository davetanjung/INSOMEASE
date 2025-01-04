package com.example.insomease.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insomease.models.ActivityRequest

@Composable
fun ActivityPopUpCard(onSubmit: (ActivityRequest) -> Unit, onClose: () -> Unit) {
    // Initialize states for form fields
    var activityName by remember { mutableStateOf(TextFieldValue("")) }
    var startTime by remember { mutableStateOf(TextFieldValue("")) }
    var endTime by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var categoryId by remember { mutableStateOf(0) }

    // Card content
    Card(
        modifier = Modifier
            .background(Color(0xFF0D1527))
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFF182341)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Add New Activity",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Activity Name Field
            TextField(
                value = activityName,
                onValueChange = { activityName = it },
                label = { Text("Activity Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFACACE7),
                    focusedContainerColor = Color(0xFFACACE7),
                    unfocusedIndicatorColor = Color(0xFFACACE7),
                    focusedIndicatorColor = Color(0xFFACACE7)
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Start Time Field
            TextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Start Time (HH:mm)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFACACE7),
                    focusedContainerColor = Color(0xFFACACE7),
                    unfocusedIndicatorColor = Color(0xFFACACE7),
                    focusedIndicatorColor = Color(0xFFACACE7),
                    unfocusedTextColor = Color.Gray,
                    focusedTextColor = Color.Gray
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // End Time Field
            TextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("End Time (HH:mm)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFACACE7),
                    focusedContainerColor = Color(0xFFACACE7),
                    unfocusedIndicatorColor = Color(0xFFACACE7),
                    focusedIndicatorColor = Color(0xFFACACE7)
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Date Field
            TextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date (yyyy-mm-dd)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFACACE7),
                    focusedContainerColor = Color(0xFFACACE7),
                    unfocusedIndicatorColor = Color(0xFFACACE7),
                    focusedIndicatorColor = Color(0xFFACACE7)
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = {
                    val newActivity = ActivityRequest(
                        name = activityName.text,
                        start_time = startTime.text,
                        end_time = endTime.text,
                        date = date.text,
                        userId = 0,  // Set the appropriate user ID
                        categoryId = categoryId
                    )
                    onSubmit(newActivity)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388))
            ) {
                Text(
                    text = "Submit",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Cancel Button
            Button(
                onClick = { onClose() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFACACE7))
            ) {
                Text(
                    text = "Cancel",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

