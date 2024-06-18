package ru.swe.skywingsexpressclient.data.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TicketDto(
    val id: Long,
    val ticketNumber: String,
    @Serializable
    val flight: Flight,
    val ownerId: Long,
    @Contextual
    val finalPrice: BigDecimal,
    val isBuy : Boolean,
    val isCheckedIn : Boolean
) {
}