import android.annotation.SuppressLint
import android.app.Activity
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.swe.skywingsexpressclient.data.models.TwoFaDto
import ru.swe.skywingsexpressclient.ui.page.profileScreen.TwoFactorDialog
import ru.swe.skywingsexpressclient.ui.page.profileScreen.generateImageFromBase64
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.viewmodel.ProfileViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SignInScreen(profileViewModel: ProfileViewModel, navController: NavController) {
    val context = LocalContext.current as Activity
    profileViewModel.setActivity(context)
    var isEmailValid by remember { mutableStateOf(false) }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
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
                    value = profileViewModel.email,
                    onValueChange = { profileViewModel.email = it
                        isEmailValid = isValidEmail(it)},
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
                    value = profileViewModel.password,
                    onValueChange = { profileViewModel.password = it },
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
                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        if (isEmailValid) {
                            profileViewModel.checkUserOnOtp()
                        }else{
                            Toast.makeText(context, "Не верный логин", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SWE_RED)
                ) {
                    Text(text = "Войти", color = SWE_WHITE)
                }
                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        profileViewModel.signInWithGoogle()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SWE_RED)
                ) {
                    Text(text = "Вход через Google", color = SWE_WHITE)
                }
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .clickable { navController.navigate("register") },
                    text = "Нет аккаунта? Зарегистрируйтесь",
                    fontSize = 14.sp
                )

            }
        }
    }

    if (profileViewModel.checkShowTwoFactorDialog) {
        TwoFactorDialogCode(
            profileViewModel,
            onDismiss = { profileViewModel.dismissTwoFactorDialogCode() }
        )
    }
}


@Composable
fun TwoFactorDialogCode(profileViewModel: ProfileViewModel, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Введите код двухфакторной аутентификации")
        },
        text = {
            Column {
                TextField(
                    value = profileViewModel.confirmCode,
                    onValueChange = { profileViewModel.confirmCode = it },
                    placeholder = { Text(text = "confirmCode") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = SWE_GREY,
                        focusedContainerColor = SWE_GREY,
                        unfocusedIndicatorColor = SWE_RED,
                        focusedIndicatorColor = SWE_GREY,
                    )
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                profileViewModel.getTokenWithOtp()
                onDismiss()
            }) {
                Text(text = "OK")
            }
        }
    )
}
