package ru.swe.skywingsexpressclient.data.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Serializable
data class Flight(
    val id: Long,
    val flightNumber: String,
    val route: Route,
    val airline: Airline,
    @Contextual
    val departureTime: LocalDateTime? = null,
    @Contextual
    val arrivalTime: LocalDateTime? = null,
    val totalSeats: Int? = null,
    val availableSeats: Int? = null,
    @Contextual
    val ticketPrice: BigDecimal? = null,
    val discountPercentage: Double? = null,
)

