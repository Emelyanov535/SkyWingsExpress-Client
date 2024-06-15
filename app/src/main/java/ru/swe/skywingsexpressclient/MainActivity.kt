package ru.swe.skywingsexpressclient

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import ru.swe.skywingsexpressclient.ui.navigation.NavItem
import ru.swe.skywingsexpressclient.ui.navigation.NavigationGraph
import ru.swe.skywingsexpressclient.ui.theme.SWE_BLUE
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_LIGHT_BLUE
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.ui.theme.SkyWingsExpressClientTheme
import ru.swe.skywingsexpressclient.viewmodel.FlightFinderViewModel
import ru.swe.skywingsexpressclient.viewmodel.ProfileViewModel
import ru.swe.skywingsexpressclient.viewmodel.factories.AppViewModelProvider

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkyWingsExpressClientTheme {
                val flightFinderViewModel: FlightFinderViewModel =
                    viewModel(factory = AppViewModelProvider.Factory)
                val profileViewModel: ProfileViewModel =
                    viewModel(factory = AppViewModelProvider.Factory)

                val context = LocalContext.current

                // Регистрация результата для Google Sign-In
                val signInLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    if (result.resultCode == RESULT_OK) {
                        val data: Intent? = result.data
                        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                        handleSignInResult(task, profileViewModel)
                    } else {
                        // Показать сообщение об отмене входа через Google
                        Toast.makeText(context, "Вход через Google был отменен", Toast.LENGTH_SHORT).show()
                    }
                }

                profileViewModel.setSignInLauncher(signInLauncher)
                val navController = rememberNavController()
                MaterialTheme {
                    Scaffold(
                        topBar = { TopBar() },
                        bottomBar = { BottomBar(navController = navController) }
                    ) {
                        NavigationGraph(
                            navController = navController,
                            flightFinderViewModel,
                            profileViewModel
                        )
                    }
                }
            }
        }
    }
}
private fun handleSignInResult(task: Task<GoogleSignInAccount>, profileViewModel: ProfileViewModel) {
    try {
        val account = task.getResult(ApiException::class.java)
        val accessToken = account.serverAuthCode
        if (accessToken != null) {
            profileViewModel.getAccessToken(accessToken)
            profileViewModel.tokenAccess.value?.let { profileViewModel.sendGoogleTokenToServer(it) }
        }
    } catch (e: ApiException) {
        // Обработка ошибки входа
        e.printStackTrace()
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val items = listOf(
        NavItem.Home,
        NavItem.Ticket,
        NavItem.Lc
    )
    NavigationBar(
        modifier = Modifier.height(64.dp),
        containerColor = SWE_GREY
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.image!!, contentDescription = null) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
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
    imgId: Int,
    route: String,
    navController: NavHostController
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
                modifier = Modifier.clickable {
                    navController.navigate(route)
                },
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


