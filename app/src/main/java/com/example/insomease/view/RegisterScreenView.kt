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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.insomease.R
import com.example.insomease.route.listScreen
import com.example.insomease.viewModels.AuthenticationViewModel

@Composable
fun RegisterScreenView(
    navController: NavController? = null,
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {
    val username = remember { mutableStateOf(authenticationViewModel.usernameInput) }
    val email = remember { mutableStateOf(authenticationViewModel.emailInput) }
    val password = remember { mutableStateOf(authenticationViewModel.passwordInput) }
    val confirmPassword = remember { mutableStateOf(authenticationViewModel.confirmPasswordInput) }
    val isPasswordVisible = remember { mutableStateOf(authenticationViewModel.isPasswordVisible) }
    val isConfirmPasswordVisible = remember { mutableStateOf(authenticationViewModel.isConfirmPasswordVisible) }

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
                onValueChange = {
                    username.value = it
                    authenticationViewModel.onUsernameChange(it)
                },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .height(54.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
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
                onValueChange = {
                    email.value = it
                    authenticationViewModel.onEmailChange(it)
                },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .height(54.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
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
                onValueChange = {
                    password.value = it
                    authenticationViewModel.onPasswordChange(it)
                },
                visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = if (isPasswordVisible.value) KeyboardType.Text else KeyboardType.Password
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            isPasswordVisible.value = !isPasswordVisible.value
                            authenticationViewModel.togglePasswordVisibility()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = if (isPasswordVisible.value) R.drawable.visible_password else R.drawable.invisible_password),
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .height(54.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                )
            )

            Text(
                text = "Confirm Password",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = {
                    confirmPassword.value = it
                    authenticationViewModel.onConfirmPasswordChange(it)
                },
                visualTransformation = if (isConfirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = if (isConfirmPasswordVisible.value) KeyboardType.Text else KeyboardType.Password
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            isConfirmPasswordVisible.value = !isConfirmPasswordVisible.value
                            authenticationViewModel.toggleConfirmPasswordVisibility()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = if (isConfirmPasswordVisible.value) R.drawable.visible_password else R.drawable.invisible_password),
                            contentDescription = "Toggle Confirm Password Visibility"
                        )
                    }
                },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .height(54.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                )
            )

            Button(
                onClick = {
                    navController?.let {
                        authenticationViewModel.registerUser(
                            navController,
                            name = username.value,
                            email = email.value,
                            password = password.value
                        )
                    }
                },
                enabled = authenticationViewModel.isButtonEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF514388)),
                modifier = Modifier
                    .padding(vertical = 30.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            // Show error message if exists
            if (authenticationViewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = authenticationViewModel.errorMessage,
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreenView()
}
