package ru.swe.skywingsexpressclient.ui.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE_MODAL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarModalScreen(
    initialDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    var selectedDate by remember { mutableStateOf(initialDate ?: today) }
    val dateState = rememberDatePickerState(initialSelectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
            .wrapContentHeight()
    ) {
        DatePicker(
            state = dateState,
            title = null,
            headline = null,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = SWE_RED,
                todayDateBorderColor = SWE_WHITE_MODAL,
                todayContentColor = Color.Black
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
                selectedDate = LocalDate.ofEpochDay(dateState.selectedDateMillis?.div(24 * 60 * 60 * 1000) ?: today.toEpochDay())
                onDateSelected(selectedDate)
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor  = SWE_RED)
        ) {
            Text(text = "Выбрать дату", color = SWE_WHITE)
        }
    }
}