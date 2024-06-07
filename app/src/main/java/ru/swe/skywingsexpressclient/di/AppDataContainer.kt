package ru.swe.skywingsexpressclient.di

import ru.swe.skywingsexpressclient.data.network.BackendService
import ru.swe.skywingsexpressclient.data.repository.FlightRepo
import ru.swe.skywingsexpressclient.data.repository.FlightRepoImpl

class AppDataContainer : AppContainer {
    override val flightRepo: FlightRepo by lazy {
        FlightRepoImpl(BackendService.getInstance())
    }

}