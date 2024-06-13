package ru.swe.skywingsexpressclient.data.models

data class SignUpDto(
    val email: String,
    val password: String ,
    val name: String ,
    val surname: String
)