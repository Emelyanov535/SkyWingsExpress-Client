package ru.swe.skywingsexpressclient.data.models;

data class TwoFaDto (
    val encodedTotpSecret: String,
    val totpSecretQRCode: String
)
