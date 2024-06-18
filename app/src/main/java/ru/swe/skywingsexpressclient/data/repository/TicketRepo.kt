package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import ru.swe.skywingsexpressclient.data.models.TicketDto

interface TicketRepo {
    suspend fun getReserveTickets() : Flow<List<TicketDto>>
    suspend fun getBuyTickets() : Flow<List<TicketDto>>
    suspend fun checkIn(ticketNumber: String)
}