package com.example.insomease.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
fun music_player(){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background), // Your background image resource
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Column {
        Text("sleep on cloud")
        Image(painter = painterResource(id = R.drawable.logo_lunaire),
            contentDescription = "")
        Text("julian theodore")
        Text("music")
Row {
    Text("0:00")
    Spacer(modifier = Modifier.weight(1f))
    Text("5:00")
}
Row {
    Image(painter = painterResource(id = R.drawable.baseline_skip_previous_24),
        contentDescription = "")
    Image(painter = painterResource(id = R.drawable.baseline_pause_24),
        contentDescription = "")
    Image(painter = painterResource(id = R.drawable.baseline_skip_next_24),
        contentDescription = "")
}
    }
}