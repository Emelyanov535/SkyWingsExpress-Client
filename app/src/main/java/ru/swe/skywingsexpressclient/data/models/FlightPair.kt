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


