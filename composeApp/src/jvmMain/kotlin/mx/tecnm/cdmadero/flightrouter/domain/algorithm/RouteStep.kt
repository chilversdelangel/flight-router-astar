package mx.tecnm.cdmadero.flightrouter.domain.algorithm

import mx.tecnm.cdmadero.flightrouter.domain.model.City

data class RouteStep(
    val accumulatedDistance: Double, // g(n)
    val estimatedDistance: Double, // h(n)
    val currentCity: City,
    val previousCity: City? = null,
) {
    val totalEstimatedDistance = accumulatedDistance + estimatedDistance // f(n)
}