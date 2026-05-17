package mx.tecnm.cdmadero.flightrouter.domain.model

data class Flight(
    val id: Int,
    val originId: Int,
    val destinationId: Int,
    val distance: Double,
)
