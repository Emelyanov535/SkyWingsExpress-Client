package ru.swe.skywingsexpressclient.data.models

data class FlightPair(
    val departureFlight: Flight,
    val returnFlight: Flight?
)

fun getFlightPairs(flightsDto: FlightsDto): List<FlightPair> {
    val flightPairs = mutableListOf<FlightPair>()
    val departureFlights = flightsDto.departureFlights.orEmpty()
    val returnFlights = flightsDto.returnFlights.orEmpty()

    if (departureFlights.isNotEmpty()) {
        departureFlights.forEach { departure ->
            if (returnFlights.isNotEmpty()) {
                returnFlights.forEach { returnFlight ->
                    flightPairs.add(FlightPair(departure, returnFlight))
                }
            } else {
                flightPairs.add(FlightPair(departure, null))
            }
        }
    }
    return flightPairs
}

data class ConnectingFlightPairs(
    val departureFlight: List<Flight>,
    val returnFlight: List<Flight>?
)

fun getConnectingFlightPairs(connectingFlightDto: ConnectingFlightDto): List<ConnectingFlightPairs> {
    val connectingFlightPairs = mutableListOf<ConnectingFlightPairs>()
    val departureFlights = connectingFlightDto.departureFlights.orEmpty()
    val returnFlights = connectingFlightDto.returnFlights.orEmpty()

    if (departureFlights.isNotEmpty()) {
        departureFlights.forEach { departure ->
            if (returnFlights.isNotEmpty()) {
                returnFlights.forEach { returnFlight ->
                    connectingFlightPairs.add(ConnectingFlightPairs(departure, returnFlight))
                }
            } else {
                connectingFlightPairs.add(ConnectingFlightPairs(departure, null))
            }
        }
    }
    return connectingFlightPairs
}


