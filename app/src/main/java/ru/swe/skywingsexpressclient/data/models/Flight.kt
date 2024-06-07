package ru.swe.skywingsexpressclient.data.models

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Serializable
data class Flight(
    val id: Int,
    val flightNumber: String,
    val route: Route,
    val airline: Airline,
    val departureTime: LocalDateTime? = null,
    val arrivalTime: LocalDateTime? = null,
    val totalSeats: Int? = null,
    val availableSeats: Int? = null,
    val ticketPrice: BigDecimal? = null,
    val discountPercentage: Double? = null,
)

