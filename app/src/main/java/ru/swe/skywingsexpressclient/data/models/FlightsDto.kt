package ru.swe.skywingsexpressclient.data.models

import kotlinx.serialization.Serializable

@Serializable
data class FlightsDto(
    val departureFlights: List<Flight>,
    val returnFlights: List<Flight>
)