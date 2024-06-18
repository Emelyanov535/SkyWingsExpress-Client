package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.data.network.AuthService

class FavRepoImpl(
    private val authService: AuthService
) : FavRepo {
    override suspend fun getFav(): Flow<List<Flight>> {
        return flowOf(authService.getFav())
    }

    override suspend fun addToFav(flightId: String) {
        authService.addToFav(flightId)
    }

    override suspend fun deleteFromFav(flightId: String) {
        authService.deleteFromFav(flightId)
    }
}