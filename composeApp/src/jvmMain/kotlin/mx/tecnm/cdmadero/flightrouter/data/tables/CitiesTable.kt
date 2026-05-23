package mx.tecnm.cdmadero.flightrouter.data.tables

import org.jetbrains.exposed.v1.core.Table

object CitiesTable : Table("cities") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val latitude = double("latitude")
    val longitude = double("longitude")

    override val primaryKey = PrimaryKey(id)
}