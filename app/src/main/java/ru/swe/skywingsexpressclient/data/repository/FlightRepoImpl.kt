package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.swe.skywingsexpressclient.data.models.FlightsDto
import ru.swe.skywingsexpressclient.data.network.BackendService

class FlightRepoImpl(private val service: BackendService): FlightRepo {
    override suspend fun getFilteredFlightByDirectionAndDate(
        from: String,
        to: String,
        fromDate: String,
        toDate: String,
    ): Flow<FlightsDto> {
        return flowOf(service.getFilteredFlightByDirectionAndDate(from, to, fromDate, toDate))
    }
}