package ru.swe.skywingsexpressclient.ui.navigation

sealed class NavItem(val route: String){
    data object Flight : NavItem("flight")
    data object Home : NavItem("home")
    data object Lc : NavItem("lc")
}