package ru.swe.skywingsexpressclient.businessLogic.repo

import kotlinx.coroutines.flow.Flow
import ru.swe.skywingsexpressclient.api.model.FlightsDto
import ru.swe.skywingsexpressclient.businessLogic.model.Flight
import java.util.Date

interface FlightRepo {
    suspend fun getFilteredFlightByDirectionAndDate(from: String, to: String, fromDate: String, toDate: String) : Flow<FlightsDto>
}