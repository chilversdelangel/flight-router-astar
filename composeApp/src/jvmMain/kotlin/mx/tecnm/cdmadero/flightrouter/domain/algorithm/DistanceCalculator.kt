package mx.tecnm.cdmadero.flightrouter.domain.algorithm

import mx.tecnm.cdmadero.flightrouter.domain.model.City
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object DistanceCalculator {
    private const val EARTH_MEAN_RADIUS = 6378.0

    private fun calculateDistance(
        originCity: City,
        destinationCity: City
    ): Double {
        val originLatRadians = (originCity.latitude).toRadians()
        val destinationLatRadians = (destinationCity.latitude).toRadians()

        val originLonRadians = (originCity.longitude).toRadians()
        val destinationLonRadians = (destinationCity.longitude).toRadians()

        val latDifference = originLatRadians - destinationLatRadians
        val lonDifference = originLonRadians - destinationLonRadians

        val haversineTheta = (sin(latDifference / 2)).toSquared() +
                cos(originLatRadians) *
                cos(destinationLatRadians) *
                sin(lonDifference / 2).toSquared()

        val centralAngle = 2 *
                atan2(
                    sqrt(haversineTheta),
                    sqrt(1 - haversineTheta)
                )

        val distance = EARTH_MEAN_RADIUS * centralAngle

        return distance
    }

    private fun Double.toRadians() = (this * PI) / 180.0

    private fun Double.toSquared() = this * this

    fun City.calculateDistanceTo(destination: City): Double {
        return calculateDistance(this, destination)
    }
}