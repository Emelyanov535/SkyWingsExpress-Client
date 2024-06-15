package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.data.models.FlightsDto
import ru.swe.skywingsexpressclient.data.models.SeatsOnFlight
import ru.swe.skywingsexpressclient.data.network.BackendService
import ru.swe.skywingsexpressclient.ui.navigation.NavItem

class FlightRepoImpl(private val service: BackendService): FlightRepo {
    override suspend fun getFilteredFlightByDirectionAndDate(
        from: String,
        to: String,
        fromDate: String,
        toDate: String,
    ): Flow<FlightsDto> {
        return flowOf(service.getFilteredFlightByDirectionAndDate(from, to, fromDate, toDate))
    }

    override suspend fun getTicketOnFlight(flightId: Long): Flow<SeatsOnFlight> {
        return flowOf(service.getTicketOnFlight(flightId))
    }

    override suspend fun getFlightById(flightId: Long): Flow<Flight> {
        return flowOf(service.getFlightById(flightId))
    }
}