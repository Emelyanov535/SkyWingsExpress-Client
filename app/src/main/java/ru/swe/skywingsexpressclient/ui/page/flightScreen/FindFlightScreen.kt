package ru.swe.skywingsexpressclient.ui.page.flightScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.swe.skywingsexpressclient.data.models.getConnectingFlightPairs
import ru.swe.skywingsexpressclient.data.models.getFlightPairs
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.viewmodel.FavViewModel
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel
import java.time.Duration

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FindFlightScreen(navController: NavHostController,
                     flightFinderViewModel: FlightFinderViewModel,
                     favViewModel: FavViewModel
) {

    val flights by flightFinderViewModel.flights.collectAsState()
    val connectingFlights by flightFinderViewModel.connectingFlights.collectAsState()

    Column(
        modifier = Modifier
            .padding(0.dp, 64.dp, 0.dp, 0.dp)
            .background(SWE_GREY)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Прямые рейсы",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )

        flights?.let { flightsDto ->
            getFlightPairs(flightsDto).forEach { flightPair ->
                FlightCard(
                    flightDeparture = flightPair.departureFlight,
                    flightArrival = flightPair.returnFlight,
                    navController = navController,
                    favViewModel = favViewModel
                )
            }
        }

        Text(
            text = "Рейсы с пересадками",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        connectingFlights?.let { connectingFlightDto ->
            getConnectingFlightPairs(connectingFlightDto).forEach{ connectingFlightPairs ->
                FlighConnectingCard(
                    flightDeparture = connectingFlightPairs.departureFlight,
                    flightArrival = connectingFlightPairs.returnFlight,
                    navController = navController,
                    favViewModel = favViewModel
                )
            }
        }
    }
}

