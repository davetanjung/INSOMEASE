package com.example.insomease.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.insomease.R
import com.example.insomease.models.ActivityModel
import com.example.insomease.models.ActivityUserModel
import com.example.insomease.view.components.BottomNavigationBar
import com.example.insomease.viewModels.HomePageViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun HomePage(
    navController: NavController? = null,
    homePageViewModel: HomePageViewModel = viewModel(factory = HomePageViewModel.Factory),
    userId: Int
) {
    val username by homePageViewModel.username.collectAsState()
    val token by homePageViewModel.token.collectAsState()
    val showPopup by homePageViewModel.showPopup

    LaunchedEffect(Unit) {
        homePageViewModel.fetchActivities(token, userId)
    }

    // Using standard Box and Column without Flow
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1527))
    ) {
        Image(
            painter = painterResource(R.drawable.main_page_bg),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WelcomeSection(username)
                    ClockAndActivitySection(
                        homePageViewModel = homePageViewModel,
                        navController = navController
                    )
                    Button(
                        onClick = {
                            homePageViewModel.togglePopup()
                        },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388))
                    ) {
                        Text(
                            text = "Add New Activity",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                }
            }
            BottomNavigationBar(
                modifier = Modifier,
                currentScreen = "home"
            )
        }
    }
}


@Composable
fun WelcomeSection(username: String) {
    Text(
        text = "$username, It's Night Time",
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.poppins)),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun ClockAndActivitySection(
    homePageViewModel: HomePageViewModel = viewModel(factory = HomePageViewModel.Factory),
    navController: NavController? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF182341))
            .border(
                width = 2.dp,
                color = Color(0xFFACACE7),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ClockView()
            Spacer(modifier = Modifier.width(16.dp))
            ActivitiesList(homePageViewModel = homePageViewModel)
        }
    }
}


@Composable
fun ClockView() {
    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .background(Color(0xFF0D1527))
            .border(
                width = 4.dp,
                color = Color(0xFFACACE7),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        // Central Text for Sleep Schedule Info
        Image(
            painter = painterResource(R.drawable.clock),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
        )
        Text(
            text = "Wake Up\nin 8 hours",
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ActivitiesList(homePageViewModel: HomePageViewModel = viewModel(factory = HomePageViewModel.Factory)) {
    val activities by homePageViewModel.activityUserModel.collectAsState() // Collect activity list from ViewModel
    var showAllActivities by remember { mutableStateOf(false) } // State for toggle

    val displayedActivities = if (showAllActivities) activities else activities.take(2)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "🗓️ Monday, 2 December",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(displayedActivities) { activity ->
                ActivityItem(activity)
            }
        }

        if (activities.size > 2) {
            Button(
                onClick = { showAllActivities = !showAllActivities },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388))
            ) {
                Text(
                    text = if (showAllActivities) "Show Less" else "See More",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun ActivityItem(activity: ActivityUserModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0D1527))
            .padding(12.dp)
    ) {
        Text(
            text = activity.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            text = "${formatTime(activity.start_time)} - ${formatTime(activity.end_time)}",
            color = Color(0xFFACACE7),
            fontSize = 12.sp
        )
    }
}
fun formatTime(time: String): String {
    return try {
        // Parse the input date-time string
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Handle the 'Z' (UTC timezone)

        // Define the desired output format
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = inputFormat.parse(time)

        // Format to desired output or return the original if parsing fails
        date?.let { outputFormat.format(it) } ?: time
    } catch (e: Exception) {
        time // Return the original input if formatting fails
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
//    HomePage()
}