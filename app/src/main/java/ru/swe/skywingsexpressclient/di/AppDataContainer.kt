package ru.swe.skywingsexpressclient.di

import android.os.Build
import androidx.annotation.RequiresApi
import ru.swe.skywingsexpressclient.data.network.BackendService
import ru.swe.skywingsexpressclient.data.network.GoogleAuthService
import ru.swe.skywingsexpressclient.data.repository.FlightRepo
import ru.swe.skywingsexpressclient.data.repository.FlightRepoImpl
import ru.swe.skywingsexpressclient.data.repository.ProfileRepo
import ru.swe.skywingsexpressclient.data.repository.ProfileRepoImpl

@RequiresApi(Build.VERSION_CODES.O)
class AppDataContainer : AppContainer {
    override val flightRepo: FlightRepo by lazy {
        FlightRepoImpl(BackendService.getInstance())
    }
    override val profileRepo: ProfileRepo by lazy {
        ProfileRepoImpl(
            BackendService.getInstance(),
            GoogleAuthService.create()
        )
    }
}