package mx.tecnm.cdmadero.flightrouter.domain.algorithm

import mx.tecnm.cdmadero.flightrouter.domain.model.City
import mx.tecnm.cdmadero.flightrouter.domain.model.Flight
import mx.tecnm.cdmadero.flightrouter.domain.model.Graph
import kotlin.test.*

class FindPathBetweenTest {
    private val madero = City(1,  "Madero", 0.0, 0.0)
    private val tampico = City(2, "Tampico", 0.0, 0.0)
    private val altamira = City(3, "Altamira", 0.0, 0.0)
    private val london = City(4,  "London", 51.5, -0.1)

    @Test
    fun `Should choose the cheaper layover over the expensive direct flight`() {
        // ARRANGE
        val graph = Graph()
        graph.registerCity(madero)
        graph.registerCity(tampico)
        graph.registerCity(altamira)

        // Expensive direct flight (15.0)
        graph.registerFlight(Flight(1, madero.id, altamira.id, 15.0))

        // Cheaper layover (5.0 + 5.0 = 10.0)
        graph.registerFlight(Flight(2, madero.id, tampico.id, 5.0))
        graph.registerFlight(Flight(3, tampico.id, altamira.id, 5.0))

        // ACT
        val route = graph.findPathBetween(madero, altamira)

        // ASSERT
        assertEquals(
            3,
            route.size,
            "The route should have 3 steps (Madero -> Tampico -> Altamira)"
        )
        assertEquals(
            tampico.name,
            route[1].currentCity.name,
            "The layover should be in Tampico because it is more economical"
        )
        assertEquals(
            10.0,
            route.last().accumulatedDistance,
            "The final total distance should be 10.0"
        )
    }

    @Test
    fun `Should find the direct flight when it is the only option available`() {
        // ARRANGE
        val graph = Graph()
        graph.registerCity(madero)
        graph.registerCity(altamira)
        graph.registerFlight(Flight(1, madero.id, altamira.id, 20.0))

        // ACT
        val route = graph.findPathBetween(madero, altamira)

        // ASSERT
        assertEquals(
            2,
            route.size,
            "The direct route should have only 2 steps"
        )
        assertEquals(
            altamira.name,
            route.last().currentCity.name,
            "The final destination must be Altamira"
        )
    }

    @Test
    fun `Should return an empty list when no connection exists between cities`() {
        // ARRANGE
        val graph = Graph()
        graph.registerCity(madero)
        graph.registerCity(london)
        // No flights added between them

        // ACT
        val route = graph.findPathBetween(madero, london)

        // ASSERT
        assertTrue(
            route.isEmpty(),
            "The route should be empty when cities are not connected"
        )
    }
}