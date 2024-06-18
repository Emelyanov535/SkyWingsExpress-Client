package ru.swe.skywingsexpressclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.data.repository.FavRepo

class FavViewModel(private val favRepo: FavRepo) : ViewModel() {
    private var _flights = MutableStateFlow<List<Flight>>(emptyList())
    val flights: StateFlow<List<Flight>> = _flights.asStateFlow()

    fun getFavList() = viewModelScope.launch {
        favRepo.getFav().collect{
            _flights.value = it
        }
    }

    fun addToFav(id: String) = viewModelScope.launch {
        favRepo.addToFav(id)
    }

    fun deleteFromFav(id: String) = viewModelScope.launch {
        favRepo.deleteFromFav(id)
        getFavList()
    }
}