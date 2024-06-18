package ru.swe.skywingsexpressclient.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ru.swe.skywingsexpressclient.ui.page.ticketScreen.MyTicketScreen
import ru.swe.skywingsexpressclient.ui.page.flightScreen.BuyTicket
import ru.swe.skywingsexpressclient.ui.page.flightScreen.FindFlightScreen
import ru.swe.skywingsexpressclient.ui.page.flightScreen.FlightScreen
import ru.swe.skywingsexpressclient.ui.page.homeScreen.HomeScreen
import ru.swe.skywingsexpressclient.ui.page.profileScreen.AuthScreen
import ru.swe.skywingsexpressclient.ui.page.profileScreen.SignUpScreen
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel
import ru.swe.skywingsexpressclient.viewmodel.ProfileViewModel
import ru.swe.skywingsexpressclient.viewmodel.TicketViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navController: NavHostController,
                    flightFinderViewModel: FlightFinderViewModel,
                    profileViewModel: ProfileViewModel,
                    ticketViewModel: TicketViewModel
) {
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
            AuthScreen(profileViewModel, navController)
        }
        composable(NavItem.Register.route) {
            SignUpScreen(profileViewModel, navController)
        }
        composable(NavItem.Ticket.route) {
            MyTicketScreen(ticketViewModel)
        }
        composable<flightsToScreen> {
            val args = it.toRoute<flightsToScreen>()
            FlightScreen(args.list, flightFinderViewModel = flightFinderViewModel, navController)
        }
        composable(NavItem.Buy.route) {
            BuyTicket(flightFinderViewModel, navController)
        }
    }
}

@Serializable
data class flightsToScreen(
    val list: List<String>
)

