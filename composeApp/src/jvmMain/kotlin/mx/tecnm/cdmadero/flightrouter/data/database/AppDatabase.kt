package mx.tecnm.cdmadero.flightrouter.data.database

import mx.tecnm.cdmadero.flightrouter.data.tables.CitiesTable
import mx.tecnm.cdmadero.flightrouter.data.tables.FlightsTable
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

        transaction {
            SchemaUtils.create(CitiesTable, FlightsTable)
        }
    }

    fun seedDatabase() {
        transaction {
            if (CitiesTable.selectAll().count() == 0L) {

                val tampicoId = CitiesTable.insert {
                    it[name] = "Tampico"
                    it[latitude] = 22.25
                    it[longitude] = -97.85
                } get CitiesTable.id

                val mtyId = CitiesTable.insert {
                    it[name] = "Monterrey"
                    it[latitude] = 25.68
                    it[longitude] = -100.31
                } get CitiesTable.id

                val cdmxId = CitiesTable.insert {
                    it[name] = "CDMX"
                    it[latitude] = 19.43
                    it[longitude] = -99.13
                } get CitiesTable.id

                val guadalajaraId = CitiesTable.insert {
                    it[name] = "Guadalajara"
                    it[latitude] = 20.67
                    it[longitude] = -103.35
                } get CitiesTable.id

                val cancunId = CitiesTable.insert {
                    it[name] = "Cancún"
                    it[latitude] = 21.16
                    it[longitude] = -86.85
                } get CitiesTable.id

                val meridaId = CitiesTable.insert {
                    it[name] = "Mérida"
                    it[latitude] = 20.97
                    it[longitude] = -89.62
                } get CitiesTable.id

                val queretaroId = CitiesTable.insert {
                    it[name] = "Querétaro"
                    it[latitude] = 20.59
                    it[longitude] = -100.39
                } get CitiesTable.id

                val chihuahuaId = CitiesTable.insert {
                    it[name] = "Chihuahua"
                    it[latitude] = 28.63
                    it[longitude] = -106.08
                } get CitiesTable.id


                FlightsTable.insert {
                    it[originId] = tampicoId
                    it[destinationId] = mtyId
                    it[distance] = 500.0
                }

                FlightsTable.insert {
                    it[originId] = mtyId
                    it[destinationId] = tampicoId
                    it[distance] = 500.0
                }

                FlightsTable.insert {
                    it[originId] = mtyId
                    it[destinationId] = cdmxId
                    it[distance] = 800.0
                }

                FlightsTable.insert {
                    it[originId] = cdmxId
                    it[destinationId] = mtyId
                    it[distance] = 800.0
                }

                FlightsTable.insert {
                    it[originId] = cdmxId
                    it[destinationId] = guadalajaraId
                    it[distance] = 550.0
                }

                FlightsTable.insert {
                    it[originId] = guadalajaraId
                    it[destinationId] = cdmxId
                    it[distance] = 550.0
                }

                FlightsTable.insert {
                    it[originId] = guadalajaraId
                    it[destinationId] = cancunId
                    it[distance] = 1700.0
                }

                FlightsTable.insert {
                    it[originId] = cancunId
                    it[destinationId] = guadalajaraId
                    it[distance] = 1700.0
                }

                FlightsTable.insert {
                    it[originId] = tampicoId
                    it[destinationId] = cancunId
                    it[distance] = 1200.0
                }

                FlightsTable.insert {
                    it[originId] = cancunId
                    it[destinationId] = tampicoId
                    it[distance] = 1200.0
                }

                FlightsTable.insert {
                    it[originId] = cancunId
                    it[destinationId] = meridaId
                    it[distance] = 300.0
                }

                FlightsTable.insert {
                    it[originId] = meridaId
                    it[destinationId] = cancunId
                    it[distance] = 300.0
                }

                FlightsTable.insert {
                    it[originId] = cdmxId
                    it[destinationId] = queretaroId
                    it[distance] = 220.0
                }

                FlightsTable.insert {
                    it[originId] = queretaroId
                    it[destinationId] = cdmxId
                    it[distance] = 220.0
                }

                FlightsTable.insert {
                    it[originId] = queretaroId
                    it[destinationId] = guadalajaraId
                    it[distance] = 350.0
                }

                FlightsTable.insert {
                    it[originId] = guadalajaraId
                    it[destinationId] = queretaroId
                    it[distance] = 350.0
                }

                FlightsTable.insert {
                    it[originId] = mtyId
                    it[destinationId] = chihuahuaId
                    it[distance] = 700.0
                }

                FlightsTable.insert {
                    it[originId] = chihuahuaId
                    it[destinationId] = mtyId
                    it[distance] = 700.0
                }

                FlightsTable.insert {
                    it[originId] = chihuahuaId
                    it[destinationId] = guadalajaraId
                    it[distance] = 1050.0
                }

                FlightsTable.insert {
                    it[originId] = guadalajaraId
                    it[destinationId] = chihuahuaId
                    it[distance] = 1050.0
                }
            }
        }
    }
}