package ru.swe.skywingsexpressclient.businessLogic.vmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import ru.swe.skywingsexpressclient.api.model.FlightsDto
import ru.swe.skywingsexpressclient.businessLogic.model.Flight
import ru.swe.skywingsexpressclient.businessLogic.repo.FlightRepo
import java.time.LocalDate

class FlightFinderViewModel(private val flightRepository: FlightRepo) : ViewModel() {
    private val _from = MutableStateFlow("")
    val from: StateFlow<String> = _from.asStateFlow()

    private val _to = MutableStateFlow("")
    val to: StateFlow<String> = _to.asStateFlow()

    private val _departureDate = MutableStateFlow<LocalDate?>(null)
    val departureDate: StateFlow<LocalDate?> = _departureDate.asStateFlow()

    private val _arrivalDate = MutableStateFlow<LocalDate?>(null)
    val arrivalDate: StateFlow<LocalDate?> = _arrivalDate.asStateFlow()

    private val _flights = MutableStateFlow<FlightsDto?>(null)
    val flights: StateFlow<FlightsDto?> = _flights.asStateFlow()

    fun setFrom(from: String) {
        _from.value = from
    }

    fun setTo(to: String) {
        _to.value = to
    }

    fun setDepartureDate(date: LocalDate) {
        _departureDate.value = date
    }

    fun setArrivalDate(date: LocalDate?) {
        _arrivalDate.value = date
    }

    fun searchFlights() = viewModelScope.launch {
        val from = _from.value
        val to = _to.value
        val fromDate = _departureDate.value.toString()
        val toDate = _arrivalDate.value.toString()

        flightRepository.getFilteredFlightByDirectionAndDate(from, to, fromDate, toDate)
            .collect { flights ->
                _flights.value = flights
            }

    }
}
