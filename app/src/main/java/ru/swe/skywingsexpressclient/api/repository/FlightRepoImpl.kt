package ru.swe.skywingsexpressclient.api.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.swe.skywingsexpressclient.api.BackendService
import ru.swe.skywingsexpressclient.api.model.FlightsDto
import ru.swe.skywingsexpressclient.businessLogic.model.Flight
import ru.swe.skywingsexpressclient.businessLogic.repo.FlightRepo

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