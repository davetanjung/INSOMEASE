package com.example.insomease.view.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import com.example.insomease.models.ActivityUserModel
import com.example.insomease.route.listScreen
import com.example.insomease.view.components.BottomNavigationBar
import com.example.insomease.viewModels.HomePageViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
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
    val showPopup = homePageViewModel.showPopUp
    val showNextPopUp = homePageViewModel.showNextPopUp
    val currentUserId by homePageViewModel.currentUserId.collectAsState()

    LaunchedEffect(Unit) {
        homePageViewModel.fetchActivities(token, userId)
        homePageViewModel.fetchCategories(token)
    }

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
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 40.dp)
                            .padding(top = 16.dp),
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

        // Show Popup if state is true
        if (showPopup) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)), // Dim the background
                contentAlignment = Alignment.Center
            ) {
                if(showNextPopUp){
                    ActivityPopUpCard2(
                        homePageViewModel,
                        navController
                    )
                } else {
                    ActivityPopUpCard(homePageViewModel)
                }
            }
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
    val currentUserId by homePageViewModel.currentUserId.collectAsState()
    val token by homePageViewModel.token.collectAsState()
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val latestSelectedDate = rememberUpdatedState(selectedDate)

    LaunchedEffect(Unit) {
        homePageViewModel.getUserId()
    }

    LaunchedEffect(latestSelectedDate.value) {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(latestSelectedDate.value.time)
        homePageViewModel.fetchActivities(token = token, id = currentUserId, specificDate = formattedDate)
    }

    // Function to navigate forward/backward by one day
    fun navigateDate(isForward: Boolean) {
        val calendar = selectedDate.clone() as Calendar
        calendar.add(Calendar.DAY_OF_YEAR, if (isForward) 1 else -1)
        selectedDate = calendar
        Log.d("ClockAndActivitySection", "Selected Date Updated: ${selectedDate.time}")
    }

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
        Column {
            // Display the current date with buttons to change the day
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navigateDate(isForward = false) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Previous Day",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
                Text(
                    text = "🗓️ ${getFormattedCurrentDate(selectedDate)}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                IconButton(onClick = { navigateDate(isForward = true) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Next Day",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Now fetch and display activities for the selected date
            ActivitiesList(
                homePageViewModel = homePageViewModel,
                onActivityClick = { activityId ->
                    navController?.navigate("${listScreen.ActivityDetailScreen.name}/$currentUserId/$activityId")
                },
            )
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
fun ActivitiesList(
    homePageViewModel: HomePageViewModel = viewModel(factory = HomePageViewModel.Factory),
    onActivityClick: (Int) -> Unit
) {
    val activities by homePageViewModel.activityUserModel.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(activities) { activity ->
                    ActivityItem(activity, onActivityClick)
                }
            }
        }
    }
}



@Composable
fun ActivityItem(
    activity: ActivityUserModel,
    onActivityClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF0D1527))
            .padding(12.dp)
//            .clickable { onActivityClick(activity.id) }
    ) {
        Column(modifier = Modifier.weight(1f)) {
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
        Image(
            painter = painterResource(R.drawable.edit_icon),
            contentDescription = "Edit Icon",
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    onActivityClick(activity.id)
                },
            contentScale = ContentScale.Crop
        )
    }
}



fun formatTime(time: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = inputFormat.parse(time)

        date?.let { outputFormat.format(it) } ?: time
    } catch (e: Exception) {
        time
    }
}

fun getFormattedCurrentDate(selectedDate: Calendar): String {
    val jakartaTimeZone = TimeZone.getTimeZone("Asia/Jakarta")
    val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
    dateFormat.timeZone = jakartaTimeZone
    return dateFormat.format(selectedDate.time)
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
//    HomePage()
}