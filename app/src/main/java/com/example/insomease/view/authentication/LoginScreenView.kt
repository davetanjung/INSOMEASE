package com.example.insomease.view.authentication

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.insomease.viewModels.AuthenticationViewModel

@Composable
fun LoginScreenView(
    navController: NavController? = null,
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {

    val emailInput = authenticationViewModel.emailInput
    val passwordInput = authenticationViewModel.passwordInput
    val isPasswordVisible = authenticationViewModel.isPasswordVisible
    val errorMessage = authenticationViewModel.errorMessage
    val isButtonEnabled = authenticationViewModel.isButtonEnabled

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.main_page_bg),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Email Input
            Text(
                text = "Email",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = emailInput,
                onValueChange = {
                    authenticationViewModel.onEmailChange(it)
                },
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
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
            )

            // Password Input
            Text(
                text = "Password",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = passwordInput,
                onValueChange = {
                    authenticationViewModel.onPasswordChange(it)
                },
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
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                trailingIcon = {
                    Icon(
                        painter = if (isPasswordVisible) painterResource(R.drawable.visible_password) else painterResource(R.drawable.invisible_password),
                        contentDescription = "Toggle Password Visibility",
                        modifier = Modifier.clickable {
                            authenticationViewModel.togglePasswordVisibility()
                        }
                    )
                }
            )

            // Error Message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Login Button
            Button(
                onClick = {
                    navController?.let {
                        authenticationViewModel.loginUser(navController)
                    }
                },
                enabled = isButtonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF514388),
                    disabledContainerColor = Color.Gray
                ),
                modifier = Modifier.padding(vertical = 30.dp)
            ) {
                Text(
                    text = "Login",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Signup Text
            Text(
                text = buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(style = SpanStyle(color = Color(0xFFACACE7))) {
                        append("Sign Up")
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
                        navController?.navigate(listScreen.SignUpScreen.name)
                    }
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenView()
}
