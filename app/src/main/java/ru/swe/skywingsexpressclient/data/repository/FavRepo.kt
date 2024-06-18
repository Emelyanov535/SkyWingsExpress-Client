package ru.swe.skywingsexpressclient.data.repository

import kotlinx.coroutines.flow.Flow
import ru.swe.skywingsexpressclient.data.models.Flight

interface FavRepo {
    suspend fun getFav() : Flow<List<Flight>>
    suspend fun addToFav(flightId: String)
    suspend fun deleteFromFav(flightId: String)
}