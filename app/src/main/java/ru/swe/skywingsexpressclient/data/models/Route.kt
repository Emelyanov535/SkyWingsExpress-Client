package ru.swe.skywingsexpressclient.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Route(
    val id: Long,
    val origin: String,
    val destination: String,
    val distance: Double,
)

