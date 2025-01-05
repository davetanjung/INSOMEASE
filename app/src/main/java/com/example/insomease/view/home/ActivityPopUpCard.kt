package com.example.insomease.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insomease.R
import com.example.insomease.models.ActivityRequest
import com.example.insomease.viewModels.HomePageViewModel

@Composable
fun ActivityPopUpCard(homePageViewModel: HomePageViewModel) {

    val token by homePageViewModel.token.collectAsState()

    // Initialize states for form fields
    var name by remember { mutableStateOf(homePageViewModel.nameInput) }
    var categoryId by remember { mutableStateOf(homePageViewModel.categoryId) }

    var expanded by remember { mutableStateOf(false) }
    var selectedCategoryName by remember { mutableStateOf("Select category") }

    // Categories list from ViewModel
    val categories by homePageViewModel.categories

    LaunchedEffect(Unit) {
        homePageViewModel.fetchCategories(token)
    }

    // Card content
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
                .fillMaxWidth(fraction = 0.8f), // Adjust the width relative to the screen
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
                    text = "Add your Activity",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Activity Title TextField

                Text(
                    text = "Title: ",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
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
                        unfocusedContainerColor = Color(0xFF1C1F46), // Color when not focused
                        focusedContainerColor = Color(0xFF1C1F46),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Activity Category Dropdown
                Text(
                    text = "Choose Category: ",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
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
                    // Modify DropdownMenu positioning
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth(1/2f)
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

                Spacer(modifier = Modifier.height(24.dp))

                // Button
                Button(
                    onClick = {
                        if (name.isNotEmpty() && selectedCategoryName != "Select category") {
                            homePageViewModel.toggleNextPopUp()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6954DE),
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Next", modifier = Modifier.padding(horizontal = 32.dp))
                }
            }
        }
    }
}


