package ru.swe.skywingsexpressclient.data.models

data class responseFor2FA(
    val totpInitialCode: String ,
    val encodedTotpSecret: String
) {
}