package ru.swe.skywingsexpressclient.ui.page.profileScreen

import SignInScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.viewmodel.ProfileViewModel

@Composable
fun AuthScreen(profileViewModel: ProfileViewModel, navController: NavController) {
    val accessToken = profileViewModel.tokenAccess.collectAsState().value
    Column(
        modifier = Modifier
            .background(SWE_GREY)
            .fillMaxSize()
    ){
        if (accessToken!=null){
            ProfileScreen(profileViewModel)
        }else{
            SignInScreen(profileViewModel = profileViewModel, navController)
        }
    }
}