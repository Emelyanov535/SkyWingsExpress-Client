package ru.swe.skywingsexpressclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.data.models.FlightsDto
import ru.swe.skywingsexpressclient.data.models.Seat
import ru.swe.skywingsexpressclient.data.models.SeatsOnFlight
import ru.swe.skywingsexpressclient.data.repository.FlightRepo
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

    private val _selectedSeats = MutableStateFlow<List<Seat>>(emptyList())
    val selectedSeats: StateFlow<List<Seat>> = _selectedSeats.asStateFlow()

    // Изменяем тип данных для хранения списка мест
    private val _seatsOnFlight = MutableStateFlow<SeatsOnFlight?>(null)
    val seatsOnFlight: StateFlow<SeatsOnFlight?> = _seatsOnFlight.asStateFlow()

    private val _flight = MutableStateFlow<Flight?>(null)
    val flight: StateFlow<Flight?> = _flight.asStateFlow()

    private val _flightConvertedFromId = MutableStateFlow<List<Flight>>(emptyList())
    val flightConvertedFromId: StateFlow<List<Flight>> = _flightConvertedFromId.asStateFlow()

    private val selectedSeatsMap = mutableMapOf<Long, List<Seat>>()

    fun getFlightConvertedFromId(list: List<String>) {
        viewModelScope.launch {
            val flightsList = mutableListOf<Flight>()

            list.forEach { flightId ->
                val flight = flightRepository.getFlightById(flightId.toLong())
                flight?.let {
                    flightsList.add(it.first())
                }
            }

            _flightConvertedFromId.value = flightsList
        }
    }

    fun getSelectedSeats(flightId: Long): List<Seat> {
        return selectedSeatsMap[flightId] ?: emptyList()
    }

    fun selectSeats(flightId: Long, seats: List<Seat>) {
        selectedSeatsMap[flightId] = seats
    }

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
// fun getFlightById(id: Long) = viewModelScope.launch{
// flightRepository.getFlightById(id).collect{
// flight -> _flight.value = flight
// }
// }

    suspend fun getFlightById(id: Long): Flight {
        val flow: Flow<Flight> = flightRepository.getFlightById(id)
        return flow.first()
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

    fun fetchSeatsOnFlight(flightId: Long) = viewModelScope.launch {
        flightRepository.getTicketOnFlight(flightId).collect { seatsOnFlight ->
            _seatsOnFlight.value = seatsOnFlight
        }
    }

    fun selectSeat(seat: Seat) {
        _selectedSeats.value = _selectedSeats.value + seat
    }

    fun removeSeat(seat: Seat) {
        _selectedSeats.value = _selectedSeats.value - seat
    }
// fun purchaseTickets() = viewModelScope.launch {
// val reservation = ReservationDto(_selectedSeats.value.map { it.ticketNumber })
// seatRepository.purchaseTickets(reservation)
// }
}