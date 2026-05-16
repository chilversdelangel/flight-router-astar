package mx.tecnm.cdmadero.flightrouter.domain.algorithm

import mx.tecnm.cdmadero.flightrouter.domain.algorithm.DistanceCalculator.calculateDistanceTo
import mx.tecnm.cdmadero.flightrouter.domain.model.City
import mx.tecnm.cdmadero.flightrouter.domain.model.Graph
import java.util.PriorityQueue

fun Graph.findPathBetween(originCity: City, destinationCity: City): List<RouteStep> {
    val stepsToEvaluate = PriorityQueue<RouteStep>(compareBy { it.totalEstimatedCost })

    val bestKnownCosts: MutableMap<Int, Double> = mutableMapOf()
    val previousStepFrom: MutableMap<Int, RouteStep> = mutableMapOf()

    val estimatedCost = originCity.calculateDistanceTo(destinationCity)

    bestKnownCosts[originCity.id] = 0.0

    val initialStep = RouteStep(
        accumulatedCost = 0.0,
        estimatedCost = estimatedCost,
        currentCity = originCity
    )

    stepsToEvaluate.add(initialStep)

    while (stepsToEvaluate.isNotEmpty()) {
        val currentStep = stepsToEvaluate.remove()

        if (currentStep.currentCity.id == destinationCity.id) {
            return currentStep.traceRoute(trace = previousStepFrom)
        }

        val possibleFlights = this.findFlightsFrom(currentStep.currentCity.id)

        for (flight in possibleFlights) {
            val nextCityId = flight.destinationId

            val proposedCost = currentStep.accumulatedCost + flight.cost

            val bestKnownCost = bestKnownCosts[nextCityId] ?: Double.MAX_VALUE

            if (proposedCost >= bestKnownCost) continue

            bestKnownCosts[nextCityId] = proposedCost
            previousStepFrom[nextCityId] = currentStep

            val nextCity = this.findCity(nextCityId) ?: continue

            val newEstimatedCost = nextCity.calculateDistanceTo(destinationCity)

            val nextStep = RouteStep(
                accumulatedCost = proposedCost,
                estimatedCost = newEstimatedCost,
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
