package ru.swe.skywingsexpressclient.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.swe.skywingsexpressclient.businessLogic.vmodel.FlightFinderViewModel
import ru.swe.skywingsexpressclient.ui.page.homeScreen.HomeScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navController: NavHostController, flightFinderViewModel: FlightFinderViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Home.route
    ) {
        composable(NavItem.Home.route) {
            HomeScreen(navController, flightFinderViewModel)
        }
    }
}