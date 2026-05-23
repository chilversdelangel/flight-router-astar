package mx.tecnm.cdmadero.flightrouter.data.tables

import org.jetbrains.exposed.v1.core.Table

object FlightsTable : Table("flights") {
    val id = integer("id").autoIncrement()
    val originId = integer("origin_id").references(CitiesTable.id)
    val destinationId = integer("destination_id").references(CitiesTable.id)
    val distance = double("cost")

    override val primaryKey = PrimaryKey(id)
}