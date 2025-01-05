package com.example.insomease.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.insomease.models.ActivityUserModel
import com.example.insomease.route.listScreen
import com.example.insomease.viewModels.HomePageViewModel

@Composable
fun ActivityDetailCard (
    navController: NavController? = null,
    userId: Int,
    activityId: Int,
    activity: ActivityUserModel,
    homePageViewModel: HomePageViewModel = viewModel()
) {



    val token by homePageViewModel.token.collectAsState()

    var categoryId by remember { mutableStateOf(homePageViewModel.categoryId) }

    var expanded by remember { mutableStateOf(false) }
    var selectedCategoryName by remember { mutableStateOf("Select category") }
    val activityDetail = homePageViewModel.activityDetail

    var name by remember { mutableStateOf(activityDetail?.value?.name ?: "") }
    var startTime by remember { mutableStateOf(activityDetail?.value?.start_time ?: "") }
    var endTime by remember { mutableStateOf(activityDetail?.value?.end_time ?: "") }
    var date by remember { mutableStateOf(activityDetail?.value?.date ?: "") }

    // Categories list from ViewModel
    val categories by homePageViewModel.categories

    LaunchedEffect(Unit) {
        homePageViewModel.fetchCategories(token)
    }

    LaunchedEffect(activityId) {
        homePageViewModel.getActivityById(token, activityId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f))
            .wrapContentSize()
    ) {
        // Pop-up Card
        Card(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.Center)
                .wrapContentHeight()
                .fillMaxWidth(fraction = 0.8f),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF121538)),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Edit Activity",
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Activity Title TextField
                Text(
                    text = "Activity Name",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                )

                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                        homePageViewModel.updateName(name)
                    },
                    placeholder = { Text("Add title", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1C1F46), shape = MaterialTheme.shapes.small)
                        .padding(4.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF1C1F46),
                        focusedContainerColor = Color(0xFF1C1F46),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Category Dropdown
                Text(
                    text = "Category",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                )

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = { expanded = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C1F46)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectedCategoryName,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Icon Arrow Down",
                            tint = Color.Gray
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth(1 / 2f)
                            .align(Alignment.TopStart)
                            .background(Color(0xFF1C1F46))
                    ) {
                        if (categories.data.isEmpty()) {
                            DropdownMenuItem(
                                onClick = {},
                                text = { Text("No categories available", color = Color.White) },
                                modifier = Modifier.background(color = Color(0xFF1C1F46))
                            )
                        } else {
                            categories.data.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(text = category.name, color = Color.White) },
                                    onClick = {
                                        selectedCategoryName = category.name
                                        categoryId = category.id
                                        homePageViewModel.updateCategoryId(categoryId)
                                        expanded = false
                                    },
                                    modifier = Modifier.background(color = Color(0xFF1C1F46))
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Date Picker
                Text(
                    text = "Date",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                )

                TextField(
                    value = date,
                    onValueChange = {
                        date = it
                        homePageViewModel.updateDate(date)
                    },
                    readOnly = true,
                    placeholder = { Text("Select date", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1C1F46), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF1C1F46),
                        focusedContainerColor = Color(0xFF1C1F46),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Start Time Picker
                Text(
                    text = "Start Time",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                )

                TextField(
                    value = startTime,
                    onValueChange = {
                        startTime = it
                        homePageViewModel.updateStartTime(startTime)
                    },
                    readOnly = true,
                    placeholder = { Text("Select start time", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1C1F46), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF1C1F46),
                        focusedContainerColor = Color(0xFF1C1F46),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // End Time Picker
                Text(
                    text = "End Time",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                )

                TextField(
                    value = endTime,
                    onValueChange = {
                        endTime = it
                        homePageViewModel.updateEndTime(endTime)
                    },
                    readOnly = true,
                    placeholder = { Text("Select end time", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1C1F46), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF1C1F46),
                        focusedContainerColor = Color(0xFF1C1F46),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Save Button
                Button(
                    onClick = {
                        val updatedActivity = activity.copy(
                            name = name,
                            start_time = startTime,
                            end_time = endTime,
                            date = date,
                            categoryId = categoryId
                        )

                        homePageViewModel.updateActivity(
                            token,
                            activityId,
                            updatedActivity.name,
                            updatedActivity.start_time,
                            updatedActivity.end_time,
                            updatedActivity.date,
                            updatedActivity.categoryId
                        )

                        navController?.navigate(listScreen.HomeScreen.name + "/$userId")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6954DE),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}
