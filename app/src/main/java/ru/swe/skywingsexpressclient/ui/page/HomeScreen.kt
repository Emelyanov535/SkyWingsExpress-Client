package ru.swe.skywingsexpressclient.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ru.swe.skywingsexpressclient.BannerSection
import ru.swe.skywingsexpressclient.InfoCardsSection
import ru.swe.skywingsexpressclient.R
import ru.swe.skywingsexpressclient.SearchSection
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(SWE_GREY)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        BannerSection()
        SearchSection()
        InfoCardsSection("Личный кабинет", "ПОДРОБНЕЕ", R.drawable.ic_lc)
        InfoCardsSection("Онлайн-табло", "ПОДРОБНЕЕ", R.drawable.ic_online_table)
    }
}