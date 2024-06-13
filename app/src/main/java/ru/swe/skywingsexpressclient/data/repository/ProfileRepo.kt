package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import ru.swe.skywingsexpressclient.data.models.FlightsDto
import ru.swe.skywingsexpressclient.data.models.SignUpDto
import ru.swe.skywingsexpressclient.data.models.TokenResponse
import ru.swe.skywingsexpressclient.data.models.TwoFaDto
import ru.swe.skywingsexpressclient.data.models.responseFor2FA

interface ProfileRepo {
    suspend fun getToken(email: String, password: String) : Flow<TokenResponse>
    suspend fun getTokenWithOtp(email: String, password: String, otp: String) : Flow<TokenResponse>
    suspend fun getGoogleToken(accessToken: String) : Flow<TokenResponse>
    suspend fun getAccessToken(authCode: String) : Flow<String>
    suspend fun register(data: SignUpDto)
    suspend fun generateTwoFactorCode() : Flow<TwoFaDto>
    suspend fun submitTwoFactorCode(data: responseFor2FA)
    suspend fun checkUserOnOtp(email: String, password: String) : Flow<Boolean>
}