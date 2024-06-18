package ru.swe.skywingsexpressclient.ui.page.flightScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.swe.skywingsexpressclient.data.models.Seat
import ru.swe.skywingsexpressclient.data.models.SeatsOnFlight
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel

@Composable
fun SeatSelectionScreen(flightId: Long, flightFinderViewModel: FlightFinderViewModel) {
    LaunchedEffect(flightId) {
        flightFinderViewModel.fetchSeatsOnFlight(flightId)
    }

    val seatsOnFlight by flightFinderViewModel.seatsOnFlight.collectAsState()
    val selectedSeats = flightFinderViewModel.selectedSeats.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        seatsOnFlight?.let { seats ->
            Text(text = "Select Seat", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(seats.seats.chunked(3)) { rowSeats ->
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(rowSeats) { seat ->
                            Checkbox(
                                checked = selectedSeats.value.any { it.seatNumber == seat.seatNumber },
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        flightFinderViewModel.selectSeat(Seat(seat.seatNumber, seat.isAvailible))
                                    } else {
                                        flightFinderViewModel.removeSeat(Seat(seat.seatNumber, seat.isAvailible))
                                    }
                                },
                                enabled = seat.isAvailible!!,
                                modifier = Modifier.size(40.dp) // Optional: Adjust size if needed
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    // Navigate to final screen or perform other actions
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
            ) {
                Text(text = "Proceed to Final Screen")
            }
        }
    }
}