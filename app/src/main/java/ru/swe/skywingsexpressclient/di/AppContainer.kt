package ru.swe.skywingsexpressclient.di

import ru.swe.skywingsexpressclient.businessLogic.repo.FlightRepo

interface AppContainer {
    val flightRepo: FlightRepo
    companion object {
        const val TIMEOUT = 5000L
        const val LIMIT = 10
    }
}