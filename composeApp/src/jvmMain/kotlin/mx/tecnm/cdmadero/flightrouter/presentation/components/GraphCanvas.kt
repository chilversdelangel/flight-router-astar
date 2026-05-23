package mx.tecnm.cdmadero.flightrouter.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import flightrouter.composeapp.generated.resources.Res
import mx.tecnm.cdmadero.flightrouter.domain.model.City
import mx.tecnm.cdmadero.flightrouter.domain.model.Flight

@Composable
fun GraphCanvas(
    cities: List<City>,
    flights: List<Flight>,
    calculatedRoute: List<City>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    if (cities.isEmpty()) return

    Canvas(modifier = modifier) {
        val minLat = cities.minOf { it.latitude }
        val maxLat = cities.maxOf { it.latitude }
        val minLong = cities.minOf { it.longitude }
        val maxLong = cities.maxOf { it.longitude }

        val latRange = maxLat - minLat
        val longRange = maxLong - minLong

        val padding = size.width * 0.1f
        val usableWidth = size.width - (padding * 2)
        val usableHeight = size.height - (padding * 2)

        val citiesNodes = mutableMapOf<Int, Offset>()

        cities.forEach {
            val x = padding + (((it.longitude - minLong) / longRange) * usableWidth).toFloat()
            val y = padding + ((1.0 - (it.latitude - minLat) / latRange) * usableHeight).toFloat()

            citiesNodes[it.id] = Offset(x, y)
        }

        flights.forEach {
            val originNode = citiesNodes[it.originId]!!
            val destinationNode = citiesNodes[it.destinationId]!!

            drawLine(
                start = originNode,
                end = destinationNode,
                strokeWidth = 10f,
                color = Color.Gray
            )
        }

        calculatedRoute.windowed(2).forEach { pair ->
            val cityA = pair[0]
            val cityB = pair[1]

            drawLine(
                color = Color.Green,
                start = citiesNodes[cityA.id]!!,
                end = citiesNodes[cityB.id]!!,
                strokeWidth = 15f
            )
        }

        citiesNodes.forEach { (id, position) ->
            val cityName = cities.find { it.id == id }?.name

            drawCircle(
                color = Color.Black,
                radius = 60f,
                center = position
            )

            val textLayoutResult = textMeasurer.measure(
                text = cityName!!,
                style = TextStyle(color = Color.White)
            )

            drawText(
                textMeasurer = textMeasurer,
                text = cityName,
                topLeft = Offset(
                    x = position.x - textLayoutResult.size.width / 2f,
                    y = position.y - textLayoutResult.size.height / 2f
                ),
                style = TextStyle(color = Color.White)
            )
        }
    }
}