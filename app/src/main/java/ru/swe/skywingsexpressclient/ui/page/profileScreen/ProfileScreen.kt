package ru.swe.skywingsexpressclient.ui.page.profileScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import ru.swe.skywingsexpressclient.data.models.TwoFaDto
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.viewmodel.ProfileViewModel


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
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
                Text(
                    text = profileViewModel.extractEmailFromToken(),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        profileViewModel.getTwoFactorQrCode()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 0.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SWE_RED)
                ) {
                    Text(text = "Включить двойной вход", color = SWE_WHITE)
                }
                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        profileViewModel.logout()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 16.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SWE_RED)
                ) {
                    Text(text = "Выйти", color = SWE_WHITE)
                }
            }
        }
    }
    if (profileViewModel.showTwoFactorDialog) {
        TwoFactorDialog(
            profileViewModel,
            twoFactorCode = profileViewModel.twoFactorCode.collectAsState().value,
            onDismiss = { profileViewModel.dismissTwoFactorDialog() }
        )
    }
}

@Composable
fun TwoFactorDialog(profileViewModel: ProfileViewModel, twoFactorCode: TwoFaDto?, onDismiss: () -> Unit) {
    if (twoFactorCode == null) return

    val qrBitmap = remember { generateImageFromBase64(twoFactorCode.totpSecretQRCode) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Двухфакторная аутентификация")
        },
        text = {
            Column {
                Text(text = "Код: ${twoFactorCode.encodedTotpSecret}")
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    modifier = Modifier.size(300.dp),
                    bitmap = qrBitmap!!.asImageBitmap(),
                    contentDescription = "QR Code")
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
                onDismiss()
                profileViewModel.confirmTwoFactorAuthCode()
            }) {
                Text(text = "OK")
            }
        }
    )
}

fun generateImageFromBase64(base64String: String): Bitmap? {
    if (base64String.isEmpty()) {
        return null
    }
    try {
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}