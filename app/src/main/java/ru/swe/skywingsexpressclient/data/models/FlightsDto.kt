package ru.swe.skywingsexpressclient.data.models

import kotlinx.serialization.Serializable

@Serializable
data class FlightsDto(
    val departureFlights: List<Flight> = emptyList(),
    val returnFlights: List<Flight> = emptyList()
)

fun getFlightsConcat(flightsDto: FlightsDto): List<Flight> {
    return flightsDto.departureFlights + flightsDto.returnFlights
}
