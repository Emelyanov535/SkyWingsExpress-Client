package ru.swe.skywingsexpressclient.di

import ru.swe.skywingsexpressclient.data.repository.FlightRepo
import ru.swe.skywingsexpressclient.data.repository.ProfileRepo

interface AppContainer {
    val flightRepo: FlightRepo
    val profileRepo: ProfileRepo
    companion object {
        const val TIMEOUT = 5000L
        const val LIMIT = 10
    }
}