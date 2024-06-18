package ru.swe.skywingsexpressclient.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ReservationDto(
    val reservationTicket: List<String>
) {
}