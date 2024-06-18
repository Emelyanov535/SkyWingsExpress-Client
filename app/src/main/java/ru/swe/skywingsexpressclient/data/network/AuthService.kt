package ru.swe.skywingsexpressclient.data.network

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.swe.skywingsexpressclient.data.models.ReservationDto
import ru.swe.skywingsexpressclient.data.models.SignInDto
import ru.swe.skywingsexpressclient.data.models.TicketDto
import ru.swe.skywingsexpressclient.data.models.TokenResponse
import ru.swe.skywingsexpressclient.data.models.TwoFaDto
import ru.swe.skywingsexpressclient.data.models.responseFor2FA
import ru.swe.skywingsexpressclient.data.utils.AuthInterceptor
import ru.swe.skywingsexpressclient.data.utils.PreferencesManager
import ru.swe.skywingsexpressclient.ui.util.LocalDateTimeAdapter
import java.time.LocalDateTime

interface AuthService {
    @GET("auth/generate2faCode")
    suspend fun getTwoFactorCode(): TwoFaDto

    @POST("auth/submit2faCode")
    suspend fun submitTwoFactorCode(
        @Body data: responseFor2FA
    )
    @POST("buy")
    suspend fun buyTickets(
        @Body data: ReservationDto
    )
    @POST("buy/reservation")
    suspend fun reserveTickets(
        @Body data: ReservationDto
    )

    @GET("buy/getUserReservedTicket")
    suspend fun getUserReservedTicket(): List<TicketDto>

    @GET("buy/getUserBuyTicket")
    suspend fun getUserBuyTicket(): List<TicketDto>

    @GET("/checkin/{ticketNumber}")
    suspend fun checkin(
        @Path("ticketNumber") ticketNumber: String
    )
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8081/api/v1/"

        @RequiresApi(Build.VERSION_CODES.O)
        fun create(context: Context): AuthService {
            val gson = GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
                .create()
            val preferencesManager = PreferencesManager(context)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(preferencesManager))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(AuthService::class.java)
        }
    }
}