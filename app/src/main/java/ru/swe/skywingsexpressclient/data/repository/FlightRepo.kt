package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.Interceptor
import ru.swe.skywingsexpressclient.data.models.ConnectingFlightDto
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.data.models.FlightsDto
import ru.swe.skywingsexpressclient.data.models.ReservationDto
import ru.swe.skywingsexpressclient.data.models.SeatsOnFlight
import ru.swe.skywingsexpressclient.ui.navigation.NavItem

interface FlightRepo {
    suspend fun getFilteredFlightByDirectionAndDate(from: String, to: String, fromDate: String, toDate: String) : Flow<FlightsDto>
    suspend fun getTicketOnFlight(flightId : Long) : Flow<SeatsOnFlight>
    suspend fun getFlightById(flightId : Long) : Flow<Flight>
    suspend fun reserveTickets(list: ReservationDto)
    suspend fun buyTickets(list: ReservationDto)
    suspend fun getConnectingFlights(from: String, to: String, fromDate: String, toDate: String) : Flow<ConnectingFlightDto>
}