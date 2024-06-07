package ru.swe.skywingsexpressclient.ui.page.flightScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE_MODAL

@Composable
fun InfoAboutFlightAndAirline(flight: Flight) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SWE_WHITE_MODAL)
            .clip(RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Row(){
            Column {
                Text(text = flight.airline.code)
                Text(text = flight.airline.country)
                Text(text = flight.airline.name)
            }
        }
        Row(){
            Column {
                Text(text = flight.flightNumber)
                Text(text = flight.route.origin)
                Text(text = flight.route.destination)
                Text(text = flight.route.distance.toString())
                Text(text = flight.departureTime.toString())
                Text(text = flight.arrivalTime.toString())
                Text(text = flight.totalSeats.toString())
                Text(text = flight.ticketPrice.toString())
            }
        }
    }
}