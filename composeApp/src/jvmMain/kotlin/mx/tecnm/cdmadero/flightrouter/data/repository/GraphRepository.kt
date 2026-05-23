package mx.tecnm.cdmadero.flightrouter.data.repository

import mx.tecnm.cdmadero.flightrouter.data.tables.CitiesTable
import mx.tecnm.cdmadero.flightrouter.data.tables.FlightsTable
import mx.tecnm.cdmadero.flightrouter.domain.model.City
import mx.tecnm.cdmadero.flightrouter.domain.model.Flight
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class GraphRepository {

    fun fetchAllCities(): List<City> = transaction {
        CitiesTable.selectAll().map {
            City(
                id = it[CitiesTable.id],
                name = it[CitiesTable.name],
                latitude = it[CitiesTable.latitude],
                longitude = it[CitiesTable.longitude]
            )
        }
    }

    fun fetchAllFlights(): List<Flight> = transaction {
        FlightsTable.selectAll().map {
            Flight(
                id = it[FlightsTable.id],
                originId = it[FlightsTable.originId],
                destinationId = it[FlightsTable.destinationId],
                distance = it[FlightsTable.distance]
            )
        }
    }
}