package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import ru.swe.skywingsexpressclient.data.models.FlightsDto
import ru.swe.skywingsexpressclient.data.models.TokenResponse

interface ProfileRepo {
    suspend fun getToken(email: String, password: String) : Flow<TokenResponse>
    suspend fun getGoogleToken(accessToken: String) : Flow<TokenResponse>
    suspend fun getAccessToken(authCode: String) : Flow<String>
}