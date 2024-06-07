package ru.swe.skywingsexpressclient.businessLogic.model

import kotlinx.serialization.Serializable

@Serializable
data class Route(
    val id: Long,
    val origin: String,
    val destination: String,
    val distance: Double,
)

