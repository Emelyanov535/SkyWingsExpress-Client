package ru.swe.skywingsexpressclient.ui.navigation

import SignInScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.swe.skywingsexpressclient.ui.page.flightScreen.FindFlightScreen
import ru.swe.skywingsexpressclient.ui.page.homeScreen.HomeScreen
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel
import ru.swe.skywingsexpressclient.viewmodel.ProfileViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navController: NavHostController,
                    flightFinderViewModel: FlightFinderViewModel,
                    profileViewModel: ProfileViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Home.route
    ) {
        composable(NavItem.Home.route) {
            HomeScreen(navController, flightFinderViewModel)
        }
        composable(NavItem.Flight.route) {
            FindFlightScreen(navController, flightFinderViewModel)
        }
        composable(NavItem.Lc.route) {
            SignInScreen(profileViewModel)
        }
    }
}