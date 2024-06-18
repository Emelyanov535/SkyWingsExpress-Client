package ru.swe.skywingsexpressclient.di

import ru.swe.skywingsexpressclient.data.repository.FavRepo
import ru.swe.skywingsexpressclient.data.repository.FlightRepo
import ru.swe.skywingsexpressclient.data.repository.ProfileRepo
import ru.swe.skywingsexpressclient.data.repository.TicketRepo

interface AppContainer {
    val flightRepo: FlightRepo
    val profileRepo: ProfileRepo
    val ticketRepo: TicketRepo
    val favRepo: FavRepo
    companion object {
        const val TIMEOUT = 5000L
        const val LIMIT = 10
    }
}