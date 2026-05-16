package mx.tecnm.cdmadero.flightrouter.domain.algorithm

import mx.tecnm.cdmadero.flightrouter.domain.model.City

data class RouteStep(
    val accumulatedCost: Double, // g(n)
    val estimatedCost: Double, // h(n)
    val currentCity: City,
    val previousCity: City? = null,
) {
    val totalEstimatedCost = accumulatedCost + estimatedCost // f(n)
}