package ru.swe.skywingsexpressclient.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE_MODAL

@Composable
fun ModalScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SWE_WHITE_MODAL),
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
        ){
            Column {
                Text("Поиск билета", modifier = Modifier.padding(10.dp))
                Text("Онлайн регистрация", modifier = Modifier.padding(10.dp))
                Text("Управление бронированием", modifier = Modifier.padding(10.dp))
            }
        }
    }
}


@Composable
@Preview
fun Preview(){
    ModalScreen()
}