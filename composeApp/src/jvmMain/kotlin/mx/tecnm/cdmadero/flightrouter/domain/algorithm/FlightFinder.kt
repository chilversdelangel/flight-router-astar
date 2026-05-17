package mx.tecnm.cdmadero.flightrouter.domain.algorithm

import mx.tecnm.cdmadero.flightrouter.domain.model.City
import mx.tecnm.cdmadero.flightrouter.domain.model.Graph
import java.util.PriorityQueue

fun Graph.findPathBetween(originCity: City, destinationCity: City): List<RouteStep> {
    val stepsToEvaluate = PriorityQueue<RouteStep>(compareBy { it.totalEstimatedDistance })

    val bestKnownDistances: MutableMap<Int, Double> = mutableMapOf()
    val previousStepFrom: MutableMap<Int, RouteStep> = mutableMapOf()

    val estimatedDistance = originCity.calculateDistanceTo(destinationCity)

    bestKnownDistances[originCity.id] = 0.0

    val initialStep = RouteStep(
        accumulatedDistance = 0.0,
        estimatedDistance = estimatedDistance,
        currentCity = originCity
    )

    stepsToEvaluate.add(initialStep)

    while (stepsToEvaluate.isNotEmpty()) {
        val currentStep = stepsToEvaluate.remove()

        val bestKnownDistance = bestKnownDistances[currentStep.currentCity.id] ?: Double.MAX_VALUE

        if (currentStep.accumulatedDistance > bestKnownDistance) continue

        if (currentStep.currentCity.id == destinationCity.id) return currentStep.traceRoute(trace = previousStepFrom)

        val possibleFlights = this.findFlightsFrom(currentStep.currentCity.id)

        for (flight in possibleFlights) {
            val nextCityId = flight.destinationId

            val proposedCost = currentStep.accumulatedDistance + flight.distance

            val bestKnownCost = bestKnownDistances[nextCityId] ?: Double.MAX_VALUE

            if (proposedCost >= bestKnownCost) continue

            bestKnownDistances[nextCityId] = proposedCost
            previousStepFrom[nextCityId] = currentStep

            val nextCity = this.findCity(nextCityId) ?: continue

            val newEstimatedCost = nextCity.calculateDistanceTo(destinationCity)

            val nextStep = RouteStep(
                accumulatedDistance = proposedCost,
                estimatedDistance = newEstimatedCost,
                currentCity = nextCity,
                previousCity = currentStep.currentCity
            )

            stepsToEvaluate.add(nextStep)
        }
    }

    return emptyList()
}

private fun RouteStep.traceRoute(trace: MutableMap<Int, RouteStep>): List<RouteStep> {
    val shortestPathReversed = mutableListOf<RouteStep>()
    shortestPathReversed.add(this)

    var previousStep = trace[this.currentCity.id]

    while (previousStep != null) {
        shortestPathReversed.add(previousStep)
        previousStep = trace[previousStep.currentCity.id]
    }

    return shortestPathReversed.reversed() // Restore original order because the path was built in reverse
}
