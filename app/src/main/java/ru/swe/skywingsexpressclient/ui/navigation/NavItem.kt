package ru.swe.skywingsexpressclient.ui.navigation

import android.media.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(val route: String, val image: ImageVector?){
    data object Flight : NavItem("flight", null)
    data object Home : NavItem("home", Icons.Default.Send)
    data object Lc : NavItem("lc", Icons.Default.AccountCircle)
    data object Favourite : NavItem("fav", Icons.Default.Favorite)
    data object Register : NavItem("register", null)
    data object Ticket : NavItem("ticket", Icons.Default.Email)
    data object Buy : NavItem("buy", null)
}