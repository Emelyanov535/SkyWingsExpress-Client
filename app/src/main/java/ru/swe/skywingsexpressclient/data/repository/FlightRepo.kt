package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import ru.swe.skywingsexpressclient.data.models.FlightsDto

interface FlightRepo {
    suspend fun getFilteredFlightByDirectionAndDate(from: String, to: String, fromDate: String, toDate: String) : Flow<FlightsDto>
}