package ru.swe.skywingsexpressclient.ui.page.favouriteScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.viewmodel.FavViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyFavScreen(favViewModel: FavViewModel) {

    val flights = favViewModel.flights.collectAsState().value

    LaunchedEffect(true) {
        favViewModel.getFavList()
    }

    Column(
        modifier = Modifier
            .padding(0.dp, 64.dp, 0.dp, 0.dp)
            .background(SWE_GREY)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        flights.forEach{
            FavCard(
                flight = it,
                favViewModel = favViewModel
            )
        }
    }
}