package ru.swe.skywingsexpressclient.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Airline(
    val id: Long,
    val name: String,
    val code: String,
    val country: String
)

