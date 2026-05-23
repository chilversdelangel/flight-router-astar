package mx.tecnm.cdmadero.flightrouter

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import mx.tecnm.cdmadero.flightrouter.data.database.DatabaseFactory
import mx.tecnm.cdmadero.flightrouter.data.repository.GraphRepository
import mx.tecnm.cdmadero.flightrouter.domain.model.Graph
import mx.tecnm.cdmadero.flightrouter.presentation.FlightRouterScreen
import mx.tecnm.cdmadero.flightrouter.presentation.FlightRouterViewModel

val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 85.sp,
        letterSpacing = (-1.5).sp
    ),
    bodyLarge = TextStyle(
        fontSize = 30.sp,
        lineHeight = 38.sp
    ),
    labelLarge = TextStyle(
        fontSize = 25.sp,
        fontWeight = FontWeight.Medium
    )
)

fun main() = application {
    val state = rememberWindowState(placement = WindowPlacement.Maximized)

    DatabaseFactory.init()
    DatabaseFactory.seedDatabase()

    val graphRepository = GraphRepository()
    val myGraph = Graph()

    graphRepository.fetchAllCities().forEach {
        myGraph.registerCity(it)
    }

    graphRepository.fetchAllFlights().forEach {
        myGraph.registerFlight(it)
    }

    val viewModel = FlightRouterViewModel(myGraph)

    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = "Flight Router Demo",
    ) {
        MaterialTheme(
            typography = AppTypography
        ) {
            FlightRouterScreen(viewModel)
        }
    }
}