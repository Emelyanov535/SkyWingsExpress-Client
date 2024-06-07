package ru.swe.skywingsexpressclient.ui.page.flightScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.swe.skywingsexpressclient.data.models.getFlightPairs
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FindFlightScreen(navController: NavHostController, flightFinderViewModel: FlightFinderViewModel) {

    val flights by flightFinderViewModel.flights.collectAsState()

    Column(
        modifier = Modifier
            .padding(0.dp, 64.dp, 0.dp, 0.dp)
            .background(SWE_GREY)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        flights?.let { flightsDto ->
            getFlightPairs(flightsDto).forEach { flightPair ->
                FlightCard(flightDeparture = flightPair.departureFlight, flightArrival = flightPair.returnFlight)
            }
        }
    }
}