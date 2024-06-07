package ru.swe.skywingsexpressclient.ui.page.homeScreen.search

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.swe.skywingsexpressclient.R
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.ui.util.CalendarModalScreen
import ru.swe.skywingsexpressclient.ui.util.ModalScreen
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection(navController: NavHostController, flightFinderViewModel: FlightFinderViewModel) {
    Column(
        modifier = Modifier
            .offset(0.dp, (-80).dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SWE_WHITE)
    ) {
        var showModal by remember { mutableStateOf(false) }

        Card(
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomEnd = 20.dp,
                bottomStart = 20.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 40.dp)
                .height(56.dp)
                .clickable {
                    showModal = true
                },
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Поиск билета",
                    color = Color.Black
                )
                Icon(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null
                )
            }
        }

        DepartureArrivalSelector(flightFinderViewModel)

        DateSelector(flightFinderViewModel)

        Button(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp,
                bottomStart = 16.dp,
            ),
            onClick = {
                navController.navigate("flight")
                flightFinderViewModel.searchFlights()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor  = SWE_RED)
        ) {
            Text(text = "ПОИСК", color = SWE_WHITE)
        }

        if (showModal) {
            ModalBottomSheet(onDismissRequest = { showModal = false }) {
                ModalScreen()
            }
        }
    }
}


@Composable
fun DepartureArrivalSelector(flightFinderViewModel: FlightFinderViewModel) {

    var departureText by remember { mutableStateOf("") }
    var arrivalText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 8.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SWE_GREY),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = departureText,
            onValueChange = {
                departureText = it
                flightFinderViewModel.setFrom(it) },
            placeholder = { Text(text = "Откуда") },
            modifier = Modifier
                .weight(1f),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SWE_GREY,
                focusedContainerColor = SWE_GREY,
                unfocusedIndicatorColor = SWE_GREY,
                focusedIndicatorColor = SWE_GREY,
            )
        )
        Image(
            painter = painterResource(id = R.drawable.ic_swap),
            contentDescription = "Swap",
            modifier = Modifier
                .clickable {
                    val temp = departureText
                    departureText = arrivalText
                    arrivalText = temp
                    flightFinderViewModel.setFrom(departureText)
                    flightFinderViewModel.setTo(arrivalText)
                }
                .size(32.dp)
        )
        TextField(
            value = arrivalText,
            onValueChange = {
                arrivalText = it
                flightFinderViewModel.setTo(it) },
            placeholder = { Text(text = "Куда") },
            modifier = Modifier
                .weight(1f),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SWE_GREY,
                focusedContainerColor = SWE_GREY,
                unfocusedIndicatorColor = SWE_GREY,
                focusedIndicatorColor = SWE_GREY,
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(flightFinderViewModel: FlightFinderViewModel) {
    var showModal by remember { mutableStateOf(false) }
    var isSelectingDeparture by remember { mutableStateOf(true) }
    val departureDate by flightFinderViewModel.departureDate.collectAsState()
    val arrivalDate by flightFinderViewModel.arrivalDate.collectAsState()

    val departureText = departureDate?.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))) ?: "Туда"
    val arrivalText = arrivalDate?.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))) ?: "Обратно"

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 8.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SWE_GREY),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = departureText,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp, 0.dp)
                .clickable {
                    isSelectingDeparture = true
                    showModal = true
                },
        )
        Image(
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = "Swap",
            modifier = Modifier
                .size(32.dp)
        )
        Text(
            text = arrivalText,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp, 0.dp)
                .clickable {
                    isSelectingDeparture = false
                    showModal = true
                },
        )
    }
    if (showModal) {
        ModalBottomSheet(onDismissRequest = { showModal = false }) {
            CalendarModalScreen(
                initialDate = if (isSelectingDeparture) departureDate else arrivalDate,
                onDateSelected = { selectedDate ->
                    if (isSelectingDeparture) {
                        flightFinderViewModel.setDepartureDate(selectedDate)
                        if (arrivalDate != null && selectedDate.isAfter(arrivalDate)) {
                            flightFinderViewModel.setArrivalDate(null)
                        }
                    } else {
                        if (departureDate != null && selectedDate.isBefore(departureDate)) {
                            Toast.makeText(context, "Дата возвращения не может быть раньше даты отправления", Toast.LENGTH_SHORT).show()
                        } else {
                            flightFinderViewModel.setArrivalDate(selectedDate)
                        }
                    }
                    showModal = false
                },
            )
        }
    }
}