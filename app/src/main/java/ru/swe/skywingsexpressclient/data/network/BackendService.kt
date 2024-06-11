package ru.swe.skywingsexpressclient.data.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.swe.skywingsexpressclient.data.models.FlightsDto
import ru.swe.skywingsexpressclient.data.models.GoogleTokenResponse
import ru.swe.skywingsexpressclient.data.models.SignInDto
import ru.swe.skywingsexpressclient.data.models.TokenResponse
import ru.swe.skywingsexpressclient.ui.util.LocalDateTimeAdapter
import java.time.LocalDateTime

interface BackendService {

    //FLIGHT
    @GET("flights/search")
    suspend fun getFilteredFlightByDirectionAndDate(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("fromDate") fromDate: String,
        @Query("toDate") toDate: String?,
    ) : FlightsDto

    @POST("signin")
    suspend fun getToken(
        @Body signIn: SignInDto
    ) : TokenResponse

    @POST("google")
    suspend fun sendGoogleToken(
        @Body token: String
    ) : TokenResponse

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8081/api/v1/"

        @Volatile
        private var INSTANCE: BackendService? = null

        @RequiresApi(Build.VERSION_CODES.O)
        fun getInstance(): BackendService {
            return INSTANCE ?: synchronized(this) {
                // Создаем Gson с адаптером для LocalDateTime
                val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
                    .create()

                // Настраиваем HttpLoggingInterceptor
                val logger = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }

                // Создаем OkHttpClient с логирующим интерсептором
                val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()

                // Настраиваем Retrofit с клиентом и Gson
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(BackendService::class.java)
                    .also { INSTANCE = it }
            }
        }
    }
}