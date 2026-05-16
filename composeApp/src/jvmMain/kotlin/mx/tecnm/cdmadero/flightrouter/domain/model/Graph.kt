package mx.tecnm.cdmadero.flightrouter.domain.model

class Graph {

    private val cities = mutableMapOf<Int, City>()
    private val flightsByCity = mutableMapOf<Int, MutableList<Flight>>()

    fun findCity(cityId: Int): City? =
        cities[cityId]

    fun findFlightsFrom(cityId: Int): List<Flight> =
        flightsByCity[cityId] ?: emptyList()

    fun registerCity(city: City) {
        cities[city.id] = city
    }

    fun registerCity(flight: Flight) {
        flightsByCity
            .getOrPut(flight.originId) { mutableListOf() }
            .add(flight)
    }
}