package ru.swe.skywingsexpressclient.ui.page.flightScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel
import java.math.BigDecimal

@Composable
fun BuyTicket(flightFinderViewModel: FlightFinderViewModel, navController: NavController) {
    val tickets = flightFinderViewModel.selectedSeatsMap.collectAsState()
    val selectedSeats = flightFinderViewModel.selectedSeats.collectAsState()
    var totalPrice = remember { BigDecimal.ZERO }
    val seatsString = ArrayList<String>()
    selectedSeats.value.forEach { seat ->
        seatsString.add(seat.seatNumber)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SWE_RED),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        tickets.value.forEach { (flight, seat) ->
            TicketCard(
                flight = flight,
                seat = seat,
                isPurchased = false,
                onCheckInClick = {}
                )
            totalPrice += flight.ticketPrice!!
        }
        Row {
            Text(text = "Общая сумма ${totalPrice}")
        }
        Button(onClick = {
            flightFinderViewModel.buyTickets(seatsString)
            navController.navigate("home")
        }) {
            Text(text = "Купить билеты")
        }
        Button(onClick = {
            flightFinderViewModel.reserveTickets(seatsString)
            navController.navigate("home")
        }) {
            Text(text = "Забронировать билеты")
        }
    }
}