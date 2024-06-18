package ru.swe.skywingsexpressclient.ui.page.flightScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.navigation.NavHostController
import ru.swe.skywingsexpressclient.R
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.ui.navigation.flightsToScreen
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.ui.util.TimeConverterToPretty.Companion.getDate
import ru.swe.skywingsexpressclient.ui.util.TimeConverterToPretty.Companion.getTime
import ru.swe.skywingsexpressclient.ui.util.TimeConverterToPretty.Companion.getTimeBetween
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale



@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FlighConnectingCard(flightDeparture: List<Flight>, flightArrival: List<Flight>?, navController: NavHostController) {

    var selectedFlight by remember { mutableStateOf<Flight?>(null) }

    Card(
        modifier = Modifier.padding(8.dp)
    ) {
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
                        if (flightArrival == null) {
                            flightDeparture.sumOf { it.ticketPrice ?: BigDecimal.ZERO }
                        } else {
                            (flightDeparture.sumOf { it.ticketPrice ?: BigDecimal.ZERO } + flightArrival.sumOf { it.ticketPrice ?: BigDecimal.ZERO })
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
            flightDeparture.forEach{ flight ->
                FlightInfoRow(flight = flight, onClick = { selectedFlight = flight })
            }
            flightArrival?.forEach{ flight ->
                FlightInfoRow(flight = flight, onClick = { selectedFlight = flight })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp,
                ),
                onClick = {
//                    val list = listOf(flightArrival, flightDeparture)
//                    val listFlightsIds = ArrayList<String>()
//                    list.forEach{
//                        listFlightsIds.add(it?.id.toString())
//                    }
//                    navController.navigate(flightsToScreen(listFlightsIds))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor  = SWE_RED)
            ) {
                Text(text = "Купить", color = SWE_WHITE)
            }
        }
    }
    selectedFlight?.let { flight ->
        ModalBottomSheet(onDismissRequest = { selectedFlight = null }) {
            InfoAboutFlightAndAirline(flight)
        }
    }
}