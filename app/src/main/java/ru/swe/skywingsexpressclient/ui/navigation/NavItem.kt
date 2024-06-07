package ru.swe.skywingsexpressclient.ui.navigation

sealed class NavItem(val route: String){
    data object SignIn : NavItem("login")
    data object SignUp : NavItem("signup")
    data object Home : NavItem("home")
}