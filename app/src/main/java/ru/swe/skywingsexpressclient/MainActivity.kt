package ru.swe.skywingsexpressclient

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.swe.skywingsexpressclient.ui.NavigationGraph
import ru.swe.skywingsexpressclient.ui.page.CalendarModalScreen
import ru.swe.skywingsexpressclient.ui.page.ModalScreen
import ru.swe.skywingsexpressclient.ui.theme.SWE_BLUE
import ru.swe.skywingsexpressclient.ui.theme.SWE_GREY
import ru.swe.skywingsexpressclient.ui.theme.SWE_LIGHT_BLUE
import ru.swe.skywingsexpressclient.ui.theme.SWE_RED
import ru.swe.skywingsexpressclient.ui.theme.SWE_WHITE
import ru.swe.skywingsexpressclient.ui.theme.SkyWingsExpressClientTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkyWingsExpressClientTheme {
                MyApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
fun MyApp() {
    MaterialTheme {
        Scaffold(
            topBar = { TopBar() }
        ) {
            MainContent()
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

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainContent() {
    val navController = rememberNavController()
    NavigationGraph(navController = navController)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection() {
    Column(
        modifier = Modifier
            .offset(0.dp, -80.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SWE_WHITE)
    ) {
        var from by remember { mutableStateOf("") }
        var to by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var passengers by remember { mutableStateOf("") }

        var expandedFrom by remember { mutableStateOf(false) }
        var expandedTo by remember { mutableStateOf(false) }
        val fromOptions = listOf("Москва", "Санкт-Петербург", "Новосибирск")
        val toOptions = listOf("Сочи", "Краснодар", "Владивосток")

        var showModal by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expandedFrom,
            onExpandedChange = { expandedFrom = !expandedFrom },
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp,
                ),
                value = from,
                onValueChange = { from = it },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 16.dp, 40.dp),
                label = { Text("Поиск билета") },
                readOnly = true,
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = "Your Image"
                    )
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expandedFrom,
                onDismissRequest = { expandedFrom = false },
            ) {
                fromOptions.forEach { selectionOption ->

                }
            }
        }

        DepartureArrivalSelector()

        DateSelector()

        Button(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp,
                bottomStart = 16.dp,
            ),
            onClick = { showModal = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor  = SWE_RED)
        ) {
            Text(text = "ПОИСК", color = SWE_WHITE)
        }

        if (showModal) {
            ModalBottomSheet(onDismissRequest = { showModal = false }) {
                ModalScreen()
            }
        }
    }
}

@Composable
fun DepartureArrivalSelector() {

    var departureText by remember { mutableStateOf("") }
    var arrivalText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 8.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SWE_GREY),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = departureText,
            onValueChange = { departureText = it },
            placeholder = { Text(text = "Откуда") },
            modifier = Modifier
                .weight(1f),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SWE_GREY,
                focusedContainerColor = SWE_GREY,
                unfocusedIndicatorColor = SWE_GREY,
                focusedIndicatorColor = SWE_GREY,
            )
        )
        Image(
            painter = painterResource(id = R.drawable.ic_swap),
            contentDescription = "Swap",
            modifier = Modifier
                .clickable {
                    val temp = departureText
                    departureText = arrivalText
                    arrivalText = temp
                }
                .size(32.dp)
        )
        TextField(
            value = arrivalText,
            onValueChange = { arrivalText = it },
            placeholder = { Text(text = "Куда") },
            modifier = Modifier
                .weight(1f),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SWE_GREY,
                focusedContainerColor = SWE_GREY,
                unfocusedIndicatorColor = SWE_GREY,
                focusedIndicatorColor = SWE_GREY,
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector() {
    var showModal by remember { mutableStateOf(false) }
    var isSelectingDeparture by remember { mutableStateOf(true) }
    var departureText by remember { mutableStateOf("Туда") }
    var arrivalText by remember { mutableStateOf("Обратно") }
    var departureDate by remember { mutableStateOf<LocalDate?>(null) }
    var arrivalDate by remember { mutableStateOf<LocalDate?>(null) }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 8.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SWE_GREY),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = departureText,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp, 0.dp)
                .clickable {
                    isSelectingDeparture = true
                    showModal = true
                },
        )
        Image(
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = "Swap",
            modifier = Modifier
                .clickable {
                    val tempText = departureText
                    departureText = arrivalText
                    arrivalText = tempText

                    val tempDate = departureDate
                    departureDate = arrivalDate
                    arrivalDate = tempDate
                }
                .size(32.dp)
        )
        Text(
            text = arrivalText,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp, 0.dp)
                .clickable {
                    isSelectingDeparture = false
                    showModal = true
                },
        )
    }
    if (showModal) {
        ModalBottomSheet(onDismissRequest = { showModal = false }) {
            CalendarModalScreen(
                initialDate = if (isSelectingDeparture) departureDate else arrivalDate,
                onDateSelected = { selectedDate ->
                    if (isSelectingDeparture) {
                        departureDate = selectedDate
                        departureText = selectedDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru")))
                        if (arrivalDate != null && selectedDate.isAfter(arrivalDate)) {
                            arrivalDate = null
                            arrivalText = "Обратно"
                        }
                    } else {
                        if (departureDate != null && selectedDate.isBefore(departureDate)) {
                            Toast.makeText(context, "Дата возвращения не может быть раньше даты отправления", Toast.LENGTH_SHORT).show()
                        } else {
                            arrivalDate = selectedDate
                            arrivalText = selectedDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru")))
                        }
                    }
                    showModal = false
                },
            )
        }
    }
}

@Composable
fun InfoCardsSection(
    title: String,
    description: String,
    imgId: Int
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


