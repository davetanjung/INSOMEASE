package com.example.insomease.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.insomease.R
import com.example.insomease.route.listScreen
import com.example.insomease.viewModels.HomePageViewModel

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentScreen: String,
    navController: NavController,
    homePageViewModel: HomePageViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        homePageViewModel.getUserId()
    }

    val userId = homePageViewModel.currentUserId.collectAsState().value

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0D1527))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavigationButton(
            label = "Home",
            iconResId = if (currentScreen == "home") R.drawable.house_selected else R.drawable.house,
            onClick = {
                navController.navigate("${listScreen.HomeScreen.name}/$userId")
            }
        )

        NavigationButton(
            label = "Sleep",
            iconResId = if (currentScreen == "sleep") R.drawable.bed else R.drawable.sleep,
            onClick = {
                navController.navigate(listScreen.SleepNoteScreen.name)
            }
        )

        NavigationButton(
            label = "Relax",
            iconResId = R.drawable.brain,
            onClick = {
                // Add appropriate navigation or action
            }
        )

        NavigationButton(
            label = "Profile",
            iconResId = R.drawable.person_3_sequence,
            onClick = {
                // Add appropriate navigation or action
            }
        )
    }
}

@Composable
fun NavigationButton(
    label: String,
    iconResId: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = 12.sp,
            color = Color.White
        )
    }
}
