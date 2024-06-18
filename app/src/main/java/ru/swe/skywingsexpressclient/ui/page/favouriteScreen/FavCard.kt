package ru.swe.skywingsexpressclient.ui.page.favouriteScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.swe.skywingsexpressclient.data.models.Flight
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.ui.util.TimeConverterToPretty
import ru.swe.skywingsexpressclient.viewmodel.FavViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavCard(flight: Flight, favViewModel: FavViewModel) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SWE_WHITE)
            .padding(16.dp, 16.dp)
            .fillMaxWidth()
    ) {
        Row {
            Text(
                text = "2500 ₽",
                modifier = Modifier.weight(1f),
                fontSize = 40.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Card(
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        favViewModel.deleteFromFav(flight.id.toString())
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .background(SWE_GREY)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(5.dp)
                        .size(30.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete"
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = TimeConverterToPretty.getTime(flight.departureTime!!),
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = TimeConverterToPretty.getDate(flight.departureTime))
                Text(text = flight.route.origin)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Divider(color = Color.Black, thickness = 2.dp)
                Text(
                    text = TimeConverterToPretty.getTimeBetween(
                        flight.departureTime!!,
                        flight.arrivalTime!!
                    ),
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 4.dp)
                )
            }
            Column {
                Text(
                    text = TimeConverterToPretty.getTime(flight.arrivalTime!!),
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = TimeConverterToPretty.getDate(flight.arrivalTime))
                Text(text = flight.route.destination)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (flight.priceChangePercentage != null) {
                        if (flight.priceChangePercentage > 0) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Price Increase",
                                tint = Color.Green
                            )
                            Text(text = "+${flight.priceChangePercentage}%")
                        } else if (flight.priceChangePercentage < 0) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Price Decrease",
                                tint = Color.Red
                            )
                            Text(text = "${flight.priceChangePercentage}%")
                        }
                    }
                }
            }
        }
    }
}






