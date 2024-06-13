package ru.swe.skywingsexpressclient.data.network

import android.app.Application
import android.content.Context
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.swe.skywingsexpressclient.data.models.SignInDto
import ru.swe.skywingsexpressclient.data.models.TokenResponse
import ru.swe.skywingsexpressclient.data.models.TwoFaDto
import ru.swe.skywingsexpressclient.data.models.responseFor2FA
import ru.swe.skywingsexpressclient.data.utils.AuthInterceptor
import ru.swe.skywingsexpressclient.data.utils.PreferencesManager

interface AuthService {
    @GET("auth/generate2faCode")
    suspend fun getTwoFactorCode(): TwoFaDto

    @POST("auth/submit2faCode")
    suspend fun submitTwoFactorCode(
        @Body data: responseFor2FA
    )
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8081/api/v1/"

        fun create(context: Context): AuthService {
            val preferencesManager = PreferencesManager(context)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(preferencesManager))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(AuthService::class.java)
        }
    }
}