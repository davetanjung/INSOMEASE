package com.example.insomease.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.insomease.R
import com.example.insomease.route.listScreen


@Composable
fun OnBoardingScreen(
    navController: NavController? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.splash_screen_bg), // Your background image resource
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(bottom = 96.dp))
            Image(
                painter = painterResource(R.drawable.splash_screen_1), // Your background image resource
                contentDescription = "Image 1",
                modifier = Modifier
                    .fillMaxWidth(2/3f)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Manage Your Sleep Proactively",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 30.dp)
            )
            Text(
                text = "Track Your Sleep Levels and Discover \n" +
                        "Effective Solutions",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 30.dp)
            )
            Button(
                onClick = {
                    navController?.navigate(listScreen.OnBoardingScreen_2.name)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF514388)
                ),
            ) {
                Text(
                    text = "Next",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 30.dp)
            ) {
                repeat(3) { index ->
                    PageIndicator(isSelected = index == 0)
                }
            }
        }
    }
}

@Composable
fun OnBoardingScreen_2(
    navController: NavController? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.splash_screen_bg), // Your background image resource
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(bottom = 96.dp))
            Image(
                painter = painterResource(R.drawable.splash_screen_2), // Your background image resource
                contentDescription = "Image 1",
                modifier = Modifier
                    .fillMaxWidth(2 / 3f)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Manage Your Sleep Proactively",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 30.dp)
            )
            Text(
                text = "Track Your Sleep Levels and Discover \n" +
                        "Effective Solutions",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 30.dp)
            )
            Button(
                onClick = {
                    navController?.navigate(listScreen.OnBoardingScreen_3.name)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF514388)
                ),
            ) {
                Text(
                    text = "Next",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 30.dp)
            ) {
                repeat(3) { index ->
                    PageIndicator(isSelected = index == 1)
                }
            }
        }
    }
}

@Composable
fun OnBoardingScreen_3(
    navController: NavController? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.splash_screen_bg), // Your background image resource
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(bottom = 96.dp))
            Image(
                painter = painterResource(R.drawable.splash_screen_3), // Your background image resource
                contentDescription = "Image 1",
                modifier = Modifier
                    .fillMaxWidth(2 / 3f)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Manage Your Sleep Proactively",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 30.dp)
            )
            Text(
                text = "Track Your Sleep Levels and Discover \n" +
                        "Effective Solutions",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 30.dp)
            )
            Button(
                onClick = {
                    navController?.navigate(listScreen.LoginScreen.name)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF514388)
                ),
            ) {
                Text(
                    text = "Get Started!",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 30.dp)
            ) {
                repeat(3) { index ->
                    PageIndicator(isSelected = index == 2)
                }
            }
        }
    }
}


@Composable
fun PageIndicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(8.dp)
            .width(if(isSelected) 24.dp else 8.dp)
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) Color(0xFF514388) else Color.Gray)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardScreenPreview() {
    OnBoardingScreen()
}