package com.example.insomease.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.insomease.R
import com.example.insomease.route.listScreen

@Composable
fun RegisterScreenView(
    navController: NavController? = null
) {
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.main_page_bg),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Username",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFAAAAE5),
                        shape = RoundedCornerShape(25.dp)
                    ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(25.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
            )
            Text(
                text = "Email",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
//                placeholder = { Text(text = "Enter your email") },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFAAAAE5),
                        shape = RoundedCornerShape(25.dp)
                    ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(25.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
            )

            Text(
                text = "Password",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
//                placeholder = { Text(text = "Enter your password") },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFAAAAE5),
                        shape = RoundedCornerShape(25.dp)
                    ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(25.dp),
                visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = if (isPasswordVisible.value) KeyboardType.Text else KeyboardType.Password
                ),
                trailingIcon = {
                    Image(
                        painter = if (isPasswordVisible.value) painterResource(R.drawable.visible_password) else painterResource(
                            R.drawable.invisible_password),
                        contentDescription = "Toggle Password Visibility",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(30.dp)
                            .clickable {
                                isPasswordVisible.value = !isPasswordVisible.value
                            }
                    )
                }
            )
            Button(
                onClick = {
                    navController?.navigate(listScreen.LoginScreen.name)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF514388)
                ),
                modifier = Modifier
                    .padding(vertical = 30.dp)
            ) {
                Text(
                    text = "Sign Up",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
            Text(
                text = buildAnnotatedString {
                    append("Already have an account? ")

                    withStyle(style = SpanStyle(color = Color(0xFFACACE7))) {
                        append("Log In")
                    }
                },
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 15.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
//                    navController?.navigate("sign_up_screen")
                    }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreenView()
}
