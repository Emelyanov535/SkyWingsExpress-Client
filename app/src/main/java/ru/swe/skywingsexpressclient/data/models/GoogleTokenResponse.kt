package ru.swe.skywingsexpressclient.data.models

data class GoogleTokenResponse(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val token_type: String,
    val id_token: String
)