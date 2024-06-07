package ru.swe.skywingsexpressclient

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.swe.skywingsexpressclient.businessLogic.vmodel.AppViewModelProvider
import ru.swe.skywingsexpressclient.businessLogic.vmodel.FlightFinderViewModel
import ru.swe.skywingsexpressclient.ui.navigation.NavigationGraph
import ru.swe.skywingsexpressclient.ui.theme.SWE_BLUE
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_LIGHT_BLUE
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.ui.theme.SkyWingsExpressClientTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkyWingsExpressClientTheme {
                val flightFinderViewModel: FlightFinderViewModel = viewModel(factory = AppViewModelProvider.Factory)
                MyApp(flightFinderViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(flightFinderViewModel: FlightFinderViewModel) {
    MaterialTheme {
        Scaffold(
            topBar = { TopBar() }
        ) {
            MainContent(flightFinderViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MainContent(flightFinderViewModel: FlightFinderViewModel) {
    val navController = rememberNavController()
    NavigationGraph(navController = navController, flightFinderViewModel)
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(SWE_GREY),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(40.dp)
                    .padding(start = 20.dp)
            )
        }
        Icon(
            painterResource(id = R.drawable.menu_icon),
            contentDescription = "Menu",
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable {

                }
            ,
        )
    }
}

@Composable
fun BannerSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "banner",
            modifier = Modifier
                .fillMaxSize()
                .height(300.dp),
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
fun InfoCardsSection(
    title: String,
    description: String,
    imgId: Int
) {
    Row(
        modifier = Modifier
            .offset(0.dp, (-40).dp)
            .fillMaxWidth()
            .padding(20.dp, 8.5.dp, 20.dp, 0.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(SWE_WHITE),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                color = SWE_BLUE,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(30.dp))
            Text(
                text = description,
                color = SWE_LIGHT_BLUE,
                fontWeight = FontWeight.Bold,
            )
        }
        Image(
            painter = painterResource(id = imgId),
            contentDescription = "Swap",
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.CenterVertically)
        )
    }
}


