package ru.swe.skywingsexpressclient.di

import ru.swe.skywingsexpressclient.api.BackendService
import ru.swe.skywingsexpressclient.api.repository.FlightRepoImpl
import ru.swe.skywingsexpressclient.businessLogic.repo.FlightRepo

class AppDataContainer : AppContainer {
    override val flightRepo: FlightRepo by lazy {
        FlightRepoImpl(BackendService.getInstance())
    }

}