package ru.swe.skywingsexpressclient.ui.page.ticketScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.swe.skywingsexpressclient.data.models.Seat
import ru.swe.skywingsexpressclient.ui.page.flightScreen.TicketCard
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.viewmodel.TicketViewModel

@Composable
fun MyTicketScreen(ticketViewModel: TicketViewModel) {
    val tickets = ticketViewModel.ticket.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SWE_GREY)
            .padding(top = 70.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { ticketViewModel.getReserveTickets() }) {
                Text(text = "Забронированные")
            }
            Button(onClick = { ticketViewModel.getBuyTickets() }) {
                Text(text = "Купленные")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(tickets.value) { ticket ->
                TicketCard(
                    flight = ticket.flight,
                    seat = Seat(ticket.ticketNumber, null),
                    isPurchased = ticket.isBuy,
                    onCheckInClick = { ticketViewModel.checkIn(ticket.ticketNumber) }
                )
            }
        }
    }
}