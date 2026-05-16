package mx.tecnm.cdmadero.flightrouter.domain.model

data class City(
    val id: Int,
    val state: String,
    val name: String,
    val latitude: Double,
    val longitude: Double
)
