package ru.swe.skywingsexpressclient.ui.page.flightScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel

@Composable
fun FlightScreen(flightsIds: List<String>, flightFinderViewModel: FlightFinderViewModel, navController: NavController) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val flights by flightFinderViewModel.flightConvertedFromId.collectAsState()

    LaunchedEffect(flightsIds) {
        flightFinderViewModel.getFlightConvertedFromId(flightsIds)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 70.dp)
    ) {
        flights.let { flightList ->
            if (currentIndex < flightList.size) {
                val currentFlight = flightList[currentIndex]

                // Отображаем информацию о текущем рейсе
                Text(text = "${currentFlight.route.origin} ----> ${currentFlight.route.destination}")
                Button(onClick = {
                    // Сохраняем выбранные места для текущего рейса
                    flightFinderViewModel.selectSeats(currentFlight, listOf(flightFinderViewModel.selectedSeats.value.last()))

                    // Переходим к следующему рейсу
                    currentIndex++

                    // Если это был последний рейс, можно выполнить какие-то действия,
                    // например, переход на экран с финальной информацией
                    if (currentIndex >= flightList.size) {
                        navController.navigate("buy")
                    }
                }) {
                    Text(text = "Next Flight")
                }
                SeatSelectionScreen(flightId = currentFlight.id,
                    flightFinderViewModel = flightFinderViewModel)
                // Отображаем выбор мест для текущего рейса


                // Кнопка для перехода к следующему рейсу

            } else {
                Text(text = "All flights processed")
            }
        }
    }
}

