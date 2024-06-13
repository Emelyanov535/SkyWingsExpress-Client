package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import ru.swe.skywingsexpressclient.data.models.SignInDto
import ru.swe.skywingsexpressclient.data.models.SignInWithOtp
import ru.swe.skywingsexpressclient.data.models.SignUpDto
import ru.swe.skywingsexpressclient.data.models.TokenResponse
import ru.swe.skywingsexpressclient.data.models.TwoFaDto
import ru.swe.skywingsexpressclient.data.models.responseFor2FA
import ru.swe.skywingsexpressclient.data.network.AuthService
import ru.swe.skywingsexpressclient.data.network.BackendService
import ru.swe.skywingsexpressclient.data.network.GoogleAuthService

class ProfileRepoImpl(
    private val service: BackendService,
    private val googleService: GoogleAuthService,
    private val authService: AuthService,
): ProfileRepo {
    override suspend fun getToken(email: String, password: String): Flow<TokenResponse> {
        return flowOf(service.getToken(SignInDto(email, password)))
    }

    override suspend fun getTokenWithOtp(
        email: String,
        password: String,
        otp: String
    ): Flow<TokenResponse> {
        return flowOf(service.getTokenWithOtp(SignInWithOtp(email, password, otp)))
    }

    override suspend fun getGoogleToken(accessToken: String): Flow<TokenResponse> {
        return flowOf(service.sendGoogleToken(accessToken))
    }

    override suspend fun getAccessToken(authCode: String): Flow<String> {
        try {
            val response = googleService.getAccessToken(
                grantType = "authorization_code",
                clientId = "359809661776-d4fq32or2jevfoigrhgrc06458qkvkbp.apps.googleusercontent.com",
                clientSecret = "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",
                code = authCode
            )

            if (response.isSuccessful) {
                val accessToken = response.body()?.access_token ?: throw Exception("Access token is null")
                return flowOf(accessToken)
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun register(data: SignUpDto) {
        service.signUp(data)
    }

    override suspend fun generateTwoFactorCode(): Flow<TwoFaDto> {
        return flowOf(authService.getTwoFactorCode())
    }

    override suspend fun submitTwoFactorCode(data: responseFor2FA) {
        authService.submitTwoFactorCode(data)
    }

    override suspend fun checkUserOnOtp(email: String, password: String): Flow<Boolean> {
        return flowOf(service.checkUserOnOtp(SignInDto(email, password)))
    }


}