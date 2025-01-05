package com.example.insomease.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.insomease.R
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    navController: NavController? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.background), // Your background image resource
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Column() {
        Text(
            "Relax your mind",
            color = Color.White,

            )
        Row() {
            @Composable
            fun music(onClick: () -> Unit) {
                Button(
                    onClick = { onClick() },
                    enabled = true
                ) {
                    Text("music", color = Color.White, fontSize = 12.sp)
                }
            }
            music { }
            @Composable
            fun white_noise(onClick: () -> Unit) {
                Button(
                    onClick = { onClick() },
                    enabled = true
                ) {
                    Text("white noise", color = Color.White, fontSize = 12.sp)
                }
            }
            white_noise {  }
            @Composable
            fun story(onClick: () -> Unit) {
                Button(
                    onClick = { onClick() },
                    enabled = true
                ) {
                    Text("story", color = Color.White, fontSize = 12.sp)
                }
            }
            story {  }
        }
        Column (){
            Row (){
                Image(painter = painterResource(id = R.drawable.logo_lunaire),
                    contentDescription = "")
                Image(painter = painterResource(id = R.drawable.logo_lunaire),
                    contentDescription = "")
            }
            Row (){
                Text("sleep on cloud",
                    color = Color.White,
                )
                Text("sleep at ease",
                    color = Color.White)

            }
        }
        Row(){
            Image(painter = painterResource(id = R.drawable.baseline_house_24),
                contentDescription = "")
            Image(painter = painterResource(id = R.drawable.baseline_bed_24),
                contentDescription = "")
            Image(painter = painterResource(id = R.drawable.baseline_house_24),
                contentDescription = "")
            Image(painter = painterResource(id = R.drawable.baseline_person_24),
                contentDescription = "")
        }
        Row (){
            Text("home",
                color = Color.White)
            Text("sleep",
                color = Color.White)
            Text("relax",
                color = Color.White)
            Text("profile",
                color = Color.White)

        }
    }
}