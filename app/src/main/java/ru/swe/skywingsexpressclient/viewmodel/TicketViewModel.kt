package ru.swe.skywingsexpressclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.swe.skywingsexpressclient.data.models.TicketDto
import ru.swe.skywingsexpressclient.data.repository.ProfileRepo
import ru.swe.skywingsexpressclient.data.repository.TicketRepo
import java.time.LocalDate

class TicketViewModel(private val ticketRepo: TicketRepo) : ViewModel() {
    private val _ticket = MutableStateFlow<List<TicketDto>>(emptyList())
    val ticket: StateFlow<List<TicketDto>> = _ticket.asStateFlow()

    fun getReserveTickets() = viewModelScope.launch {
        ticketRepo.getReserveTickets().collect{
            _ticket.value = it
        }
    }
    fun getBuyTickets() = viewModelScope.launch {
        ticketRepo.getBuyTickets().collect{
            _ticket.value = it
        }
    }
    fun checkIn(ticketNumber: String) = viewModelScope.launch {
        ticketRepo.checkIn(ticketNumber)
    }
}