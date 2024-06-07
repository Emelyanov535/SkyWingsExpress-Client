package ru.swe.skywingsexpressclient.api.model

import kotlinx.serialization.Serializable
import ru.swe.skywingsexpressclient.businessLogic.model.Flight

@Serializable
data class FlightsDto(
    val departureFlights: List<Flight>,
    val returnFlights: List<Flight>
)