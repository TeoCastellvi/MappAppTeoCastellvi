package com.example.mappproject.view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mappproject.Routes.Routes
import com.example.mappproject.viewModel.MyViewModel

@Composable
fun RegisterScreen(navController: NavController, myViewModel: MyViewModel) {
    val goNext by myViewModel.goToNext.observeAsState(false)
    val email by myViewModel.email.observeAsState("")
    val password by myViewModel.password.observeAsState("")
    val logeado by myViewModel.logeado.observeAsState(false)
    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(172, 252, 217)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(Color(93, 217, 193))
        ) {
            Icon(
                Icons.Filled.ArrowBackIosNew,
                tint = Color(25, 9, 51),
                contentDescription = "Back",
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp)
                    .clickable {
                        navController.navigate(Routes.LoginScreen.route)
                    }
            )
            Text(
                text = "Registrate",
                color = Color(25, 9, 51),
                textAlign = TextAlign.Center,
                fontSize = 27.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(start = 20.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.7f)
                    .padding(16.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color(93, 217, 193)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = "Introduce un correo y contraseña para poder registrate",
                    color = Color(25, 9, 51),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { myViewModel.setemail(it) },
                    label = {
                        Text(
                            "Correo electrónico",
                            color = Color(0xFF03045e),
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF03045e),
                        unfocusedBorderColor = Color(0xFF03045e),
                        cursorColor = Color(0xFF03045e),
                        focusedTextColor = Color(0xFF03045e),
                        unfocusedTextColor = Color(0xFF03045e),

                        ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
                )
                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { myViewModel.setPassword(it) },
                    label = {
                        Text(
                            "Contraseña",
                            color = Color(0xFF03045e)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF03045e),
                        unfocusedBorderColor = Color(0xFF03045e),
                        cursorColor = Color(0xFF03045e),
                        focusedTextColor = Color(0xFF03045e),
                        unfocusedTextColor = Color(0xFF03045e),

                        ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "visibility",
                                tint = Color(0xFF03045e)
                            )
                        }

                    }

                )
                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { myViewModel.setPassword(it) },
                    label = {
                        Text(
                            "Contraseña",
                            color = Color(0xFF03045e)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF03045e),
                        unfocusedBorderColor = Color(0xFF03045e),
                        cursorColor = Color(0xFF03045e),
                        focusedTextColor = Color(0xFF03045e),
                        unfocusedTextColor = Color(0xFF03045e),

                        ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "visibility",
                                tint = Color(0xFF03045e)
                            )
                        }

                    }

                )
                Spacer(modifier = Modifier.padding(10.dp))



                Button(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 0.dp)
                        .fillMaxWidth(0.8f),
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty() && email.contains("@") && email.contains(".") && password.length >= 7){
                            myViewModel.register(email, password)
                            myViewModel.setLogin(true)
                            if (goNext) {
                                navController.navigate(Routes.LoginScreen.route)
                            }
                        }

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(172, 252, 217), contentColor = Color(25, 9, 51)
                    ),
                    shape = RoundedCornerShape(8.dp)

                ) {
                    Text(text = "Registrate")

                }
            }
        }

    }

}


