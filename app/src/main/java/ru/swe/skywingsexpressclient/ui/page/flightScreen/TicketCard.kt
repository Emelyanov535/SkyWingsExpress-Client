package ru.swe.skywingsexpressclient.ui.page.flightScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.data.models.Seat
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE

@Composable
fun TicketCard(flight: Flight, seat: Seat, isPurchased: Boolean, onCheckInClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SWE_WHITE)
            .padding(16.dp)
    ) {
        Row {
            Column {
                Text(text = flight.ticketPrice.toString() + "Р")
            }
            Column {
                Text(text = "             ")
            }
            Column {
                Text(text = seat.seatNumber)
            }
        }
        Row {
            Text(text = "${flight.route.origin} --------> ${flight.route.destination}")
        }
        Row {
            Text(text = "${flight.arrivalTime} --------> ${flight.departureTime}")
        }
        if (isPurchased) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onCheckInClick) {
                    Text(text = "Регистрация на рейс")
                }
            }
        }
    }
}
