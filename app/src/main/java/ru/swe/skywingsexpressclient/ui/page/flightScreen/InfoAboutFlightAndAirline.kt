package ru.swe.skywingsexpressclient.ui.page.flightScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE_MODAL
import ru.swe.skywingsexpressclient.ui.util.TimeConverterToPretty.Companion.getDate
import ru.swe.skywingsexpressclient.ui.util.TimeConverterToPretty.Companion.getTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoAboutFlightAndAirline(flight: Flight) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SWE_WHITE_MODAL)
            .clip(RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Row(){
            Column {
                Text(
                    text = "Авиакомпания",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = "Sky Wings Express")
            }
        }
        Row(){
            Column {
                Text(
                    text = "Информация о рейсе",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = "Номер рейса: " + flight.flightNumber)
                Text(text = "Откуда: " + flight.route.origin)
                Text(text = "Куда: " + flight.route.destination)
                Text(text = "Расстояние: " + flight.route.distance.toString())
                Text(text = "Дата отправления: " + getDate(flight.departureTime!!))
                Text(text = "Время отправления: " + getTime(flight.departureTime))
                Text(text = "Дата прибытия: " + getDate(flight.arrivalTime!!))
                Text(text = "Время прибытия: " + getTime(flight.arrivalTime))
                Text(text = "Цена билета: " + flight.ticketPrice.toString())
            }
        }
    }
}