package ru.swe.skywingsexpressclient.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ConnectingFlightDto (
    val departureFlights: List<List<Flight>>,
    val returnFlights: List<List<Flight>>,
)