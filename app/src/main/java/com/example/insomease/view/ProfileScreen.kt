package com.example.insomease.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.insomease.R
import com.example.insomease.view.sleeptracker.BottomNavigationBar

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1631))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "Profile",
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.profile_picture), // Replace with actual profile image
                    contentDescription = "Profile Picture"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Charlene Abidoye",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
            Text(
                text = "@charlnne",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Statistics Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatisticCard(title = "Avg. Sleep Quality", value = "00", reference = "References: 0 - 1 times")
            StatisticCard(title = "Nights", value = "0", reference = "References: 20 - 60%")
            StatisticCard(title = "Avg. Time", value = "0h 0m", reference = "References: < 55%")
        }



        Spacer(modifier = Modifier.height(24.dp))

        // Calendar Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(R.drawable.baseline_navigate_before_24),
                contentDescription = "Back Arrow",
                modifier = Modifier
                    .size(35.dp)
                    .clickable {  },
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Monday, 2 December",
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(R.drawable.baseline_navigate_next_24),
                contentDescription = "Back Arrow",
                modifier = Modifier
                    .size(35.dp)
                    .clickable {  },
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

        }

        Spacer(modifier = Modifier.height(24.dp))

        // Score Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Circle with score
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFF1C2642), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "00",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Sleep details
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Time in bed",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "00:00 - 00:00",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Time Asleep",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "0h 0m",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start
                )
            }


    }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Navigation Placeholder
        BottomNavigationBar()
    }
}

@Composable
fun StatisticCard(title: String, value: String, reference: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .background(Color(0xFF0C1631), RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = Color(0xFFACACE7),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = reference,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Light,
            fontSize = 8.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePreview() {
    ProfileScreen()
}
