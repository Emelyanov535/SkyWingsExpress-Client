package ru.swe.skywingsexpressclient.viewmodel.factories

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.swe.skywingsexpressclient.App
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel
import ru.swe.skywingsexpressclient.viewmodel.ProfileViewModel
import ru.swe.skywingsexpressclient.viewmodel.TicketViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            FlightFinderViewModel(app().container.flightRepo)
        }
        initializer {
            ProfileViewModel(app(), app().container.profileRepo)
        }
        initializer {
            TicketViewModel(app().container.ticketRepo)
        }
    }
}

fun CreationExtras.app(): App =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)