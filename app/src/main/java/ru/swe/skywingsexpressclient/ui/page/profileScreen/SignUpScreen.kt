package ru.swe.skywingsexpressclient.ui.page.profileScreen

import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.viewmodel.ProfileViewModel

@Composable
fun SignUpScreen(profileViewModel: ProfileViewModel, navController: NavController) {
    var isEmailValid by remember { mutableStateOf(false) }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    Column(
        modifier = Modifier
            .background(SWE_GREY)
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, SWE_WHITE),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(SWE_WHITE),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = profileViewModel.regEmail,
                    onValueChange = {
                        profileViewModel.regEmail = it
                        isEmailValid = isValidEmail(it) },
                    placeholder = { Text(text = "Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .height(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SWE_GREY),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = SWE_GREY,
                        focusedContainerColor = SWE_GREY,
                        unfocusedIndicatorColor = SWE_GREY,
                        focusedIndicatorColor = SWE_GREY,
                    )
                )
                TextField(
                    value = profileViewModel.regPassword,
                    onValueChange = { profileViewModel.regPassword = it },
                    placeholder = { Text(text = "Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .height(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SWE_GREY),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = SWE_GREY,
                        focusedContainerColor = SWE_GREY,
                        unfocusedIndicatorColor = SWE_GREY,
                        focusedIndicatorColor = SWE_GREY,
                    )
                )
                TextField(
                    value = profileViewModel.regName,
                    onValueChange = { profileViewModel.regName = it },
                    placeholder = { Text(text = "Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .height(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SWE_GREY),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = SWE_GREY,
                        focusedContainerColor = SWE_GREY,
                        unfocusedIndicatorColor = SWE_GREY,
                        focusedIndicatorColor = SWE_GREY,
                    )
                )
                TextField(
                    value = profileViewModel.regSurname,
                    onValueChange = { profileViewModel.regSurname = it },
                    placeholder = { Text(text = "Surname") },
                    modifier =Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .height(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SWE_GREY),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = SWE_GREY,
                        focusedContainerColor = SWE_GREY,
                        unfocusedIndicatorColor = SWE_GREY,
                        focusedIndicatorColor = SWE_GREY,
                    )
                )
                Button(
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp,
                    ),
                    onClick = {
                        if (isEmailValid) {
                            profileViewModel.register()
                            navController.navigate("lc")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 16.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SWE_RED)
                ) {
                    Text(text = "Зарегестрироваться", color = SWE_WHITE)
                }
            }
        }
    }
}
}