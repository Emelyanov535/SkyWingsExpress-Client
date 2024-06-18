package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.swe.skywingsexpressclient.data.models.TicketDto
import ru.swe.skywingsexpressclient.data.network.AuthService

class TicketRepoImpl(
    private val authService: AuthService
) : TicketRepo {
    override suspend fun getReserveTickets(): Flow<List<TicketDto>> {
        return flowOf(authService.getUserReservedTicket())
    }

    override suspend fun getBuyTickets(): Flow<List<TicketDto>> {
        return flowOf(authService.getUserBuyTicket())
    }

    override suspend fun checkIn(ticketNumber: String) {
        authService.checkin(ticketNumber)
    }
}