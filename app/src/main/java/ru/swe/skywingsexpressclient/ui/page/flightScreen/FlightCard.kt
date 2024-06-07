package ru.swe.skywingsexpressclient.ui.page.flightScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.swe.skywingsexpressclient.R
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FlightCard(flightDeparture: Flight, flightArrival: Flight?) {
    val departureTime = flightDeparture.departureTime?.let {
        LocalDateTime.parse(it.toString()).format(DateTimeFormatter.ofPattern("HH:mm", Locale("ru")))
    } ?: "N/A"

    val departureDate = flightDeparture.departureTime?.let {
        LocalDateTime.parse(it.toString()).format(DateTimeFormatter.ofPattern("d MMMM",  Locale("ru")))
    } ?: "N/A"

    val arrivalTime = flightDeparture.arrivalTime?.let {
        LocalDateTime.parse(it.toString()).format(DateTimeFormatter.ofPattern("HH:mm", Locale("ru")))
    } ?: "N/A"

    val arrivalDate = flightDeparture.arrivalTime?.let {
        LocalDateTime.parse(it.toString()).format(DateTimeFormatter.ofPattern("d MMMM",  Locale("ru")))
    } ?: "N/A"

    val duration = Duration.between(LocalDateTime.parse(flightDeparture.departureTime.toString()), LocalDateTime.parse(flightDeparture.arrivalTime.toString()))
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val durationText = "${hours}ч ${minutes}м"

    var showModal by remember { mutableStateOf(false) }
    var showModal1 by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(8.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SWE_WHITE)
                .clip(RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${
                        if(flightArrival == null){
                            flightDeparture.ticketPrice
                        } else {
                            flightDeparture.ticketPrice?.plus(flightArrival.ticketPrice!!)
                        }
                    } ₽",
                    modifier = Modifier.weight(1f),
                    fontSize = 40.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row() {
                    Card(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .background(SWE_GREY)
                                .clip(RoundedCornerShape(16.dp))
                                .padding(5.dp)
                                .size(30.dp),
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "notification"
                        )
                    }
                    Card(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .background(SWE_GREY)
                                .clip(RoundedCornerShape(15.dp))
                                .padding(5.dp)
                                .size(30.dp),
                            painter = painterResource(id = R.drawable.ic_link),
                            contentDescription = "link"
                        )
                    }
                }
            }
            //РЕЙС ТУДА
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(0.dp, 16.dp).clickable {
                    showModal = true
                }
            ) {
                Column {
                    Text(
                        text = departureTime,
                        fontSize = 24.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = departureDate)
                    Text(text = flightDeparture.route.origin)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Divider(color = Color.Black, thickness = 2.dp)
                    Text(
                        text = durationText,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(horizontal = 4.dp)
                    )
                }
                Column {
                    Text(
                        text = arrivalTime,
                        fontSize = 24.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = arrivalDate)
                    Text(text = flightDeparture.route.destination)
                }
            }
            //РЕЙС ОБРАТНО
            if(flightArrival != null){
                val departureTime1 = flightArrival.departureTime?.let {
                    LocalDateTime.parse(it.toString()).format(DateTimeFormatter.ofPattern("HH:mm", Locale("ru")))
                } ?: "N/A"

                val departureDate1 = flightArrival.departureTime?.let {
                    LocalDateTime.parse(it.toString()).format(DateTimeFormatter.ofPattern("d MMMM",  Locale("ru")))
                } ?: "N/A"

                val arrivalTime1 = flightArrival.arrivalTime?.let {
                    LocalDateTime.parse(it.toString()).format(DateTimeFormatter.ofPattern("HH:mm", Locale("ru")))
                } ?: "N/A"

                val arrivalDate1 = flightArrival.arrivalTime?.let {
                    LocalDateTime.parse(it.toString()).format(DateTimeFormatter.ofPattern("d MMMM",  Locale("ru")))
                } ?: "N/A"

                val duration1 = Duration.between(LocalDateTime.parse(flightArrival.departureTime.toString()), LocalDateTime.parse(flightArrival.arrivalTime.toString()))
                val hours1 = duration1.toHours()
                val minutes1 = duration1.toMinutes() % 60
                val durationText1 = "${hours1}ч ${minutes1}м"
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(0.dp, 16.dp).clickable {
                        showModal1 = true
                    }
                ) {
                    Column {
                        Text(
                            text = departureTime1,
                            fontSize = 24.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(text = departureDate1)
                        Text(text = flightArrival.route.origin)
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Divider(color = Color.Black, thickness = 2.dp)
                        Text(
                            text = durationText1,
                            modifier = Modifier
                                .background(Color.White)
                                .padding(horizontal = 4.dp)
                        )
                    }
                    Column {
                        Text(
                            text = arrivalTime1,
                            fontSize = 24.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(text = arrivalDate1)
                        Text(text = flightArrival.route.destination)
                    }
                }
            }

            Row(){
                Button(
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp,
                    ),
                    onClick = {
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor  = SWE_RED)
                ) {
                    Text(text = "КУПИТЬ", color = SWE_WHITE)
                }
            }
        }
    }
    if (showModal) {
        ModalBottomSheet(onDismissRequest = { showModal = false }) {
            InfoAboutFlightAndAirline(flightDeparture)
        }
    }
    if (showModal1) {
        ModalBottomSheet(onDismissRequest = { showModal = false }) {
            if (flightArrival != null) {
                InfoAboutFlightAndAirline(flightArrival)
            }
        }
    }
}
