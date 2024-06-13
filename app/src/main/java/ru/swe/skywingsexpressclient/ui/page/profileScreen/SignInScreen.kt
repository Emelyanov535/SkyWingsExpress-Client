import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

    Column(
        modifier = Modifier
            .background(SWE_GREY)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = profileViewModel.email,
            onValueChange = { profileViewModel.email = it },
            placeholder = { Text(text = "email") },
            modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SWE_GREY,
                focusedContainerColor = SWE_GREY,
                unfocusedIndicatorColor = SWE_RED,
                focusedIndicatorColor = SWE_GREY,
            )
        )
        TextField(
            value = profileViewModel.password,
            onValueChange = { profileViewModel.password = it },
            placeholder = { Text(text = "password") },
            modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SWE_GREY,
                focusedContainerColor = SWE_GREY,
                unfocusedIndicatorColor = SWE_RED,
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
                profileViewModel.checkUserOnOtp()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SWE_RED)
        ) {
            Text(text = "Войти", color = SWE_WHITE)
        }
        Button(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp,
                bottomStart = 16.dp,
            ),
            onClick = {
                profileViewModel.signInWithGoogle()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SWE_RED)
        ) {
            Text(text = "Вход через Google", color = SWE_WHITE)
        }
        Button(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp,
                bottomStart = 16.dp,
            ),
            onClick = {
                      navController.navigate("register")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SWE_RED)
        ) {
            Text(text = "Регистрация", color = SWE_WHITE)
        }
    }

    if(profileViewModel.checkShowTwoFactorDialog){
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
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
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
