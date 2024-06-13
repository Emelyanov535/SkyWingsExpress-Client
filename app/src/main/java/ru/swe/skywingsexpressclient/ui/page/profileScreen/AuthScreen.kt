package ru.swe.skywingsexpressclient.ui.page.profileScreen

import SignInScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import ru.swe.skywingsexpressclient.viewmodel.ProfileViewModel

@Composable
fun AuthScreen(profileViewModel: ProfileViewModel, navController: NavController) {
    val accessToken = profileViewModel.tokenAccess.collectAsState().value
    if (accessToken!=null){
        ProfileScreen(profileViewModel)
    }else{
        SignInScreen(profileViewModel = profileViewModel, navController)
    }
}