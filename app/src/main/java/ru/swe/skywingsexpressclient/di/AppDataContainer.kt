package ru.swe.skywingsexpressclient.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ru.swe.skywingsexpressclient.data.network.AuthService
import ru.swe.skywingsexpressclient.data.network.BackendService
import ru.swe.skywingsexpressclient.data.network.GoogleAuthService
import ru.swe.skywingsexpressclient.data.repository.FavRepo
import ru.swe.skywingsexpressclient.data.repository.FavRepoImpl
import ru.swe.skywingsexpressclient.data.repository.FlightRepo
import ru.swe.skywingsexpressclient.data.repository.FlightRepoImpl
import ru.swe.skywingsexpressclient.data.repository.ProfileRepo
import ru.swe.skywingsexpressclient.data.repository.ProfileRepoImpl
import ru.swe.skywingsexpressclient.data.repository.TicketRepo
import ru.swe.skywingsexpressclient.data.repository.TicketRepoImpl

@RequiresApi(Build.VERSION_CODES.O)
class AppDataContainer(private val context: Context) : AppContainer {
    override val flightRepo: FlightRepo by lazy {
        FlightRepoImpl(
            BackendService.getInstance(),
            AuthService.create(context)
        )
    }
    override val profileRepo: ProfileRepo by lazy {
        ProfileRepoImpl(
            BackendService.getInstance(),
            GoogleAuthService.create(),
            AuthService.create(context)
        )
    }
    override val ticketRepo: TicketRepo by lazy {
        TicketRepoImpl(
            AuthService.create(context)
        )
    }
    override val favRepo: FavRepo by lazy {
        FavRepoImpl(
            AuthService.create(context)
        )
    }

}