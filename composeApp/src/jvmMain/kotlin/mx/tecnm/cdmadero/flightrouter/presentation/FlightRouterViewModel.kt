package mx.tecnm.cdmadero.flightrouter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.tecnm.cdmadero.flightrouter.domain.algorithm.findPathBetween
import mx.tecnm.cdmadero.flightrouter.domain.model.City
import mx.tecnm.cdmadero.flightrouter.domain.model.Flight
import mx.tecnm.cdmadero.flightrouter.domain.model.Graph

data class FlightRouterUiState(
    val availableCities: List<City> = emptyList(),
    val availableFlights: List<Flight> = emptyList(),
    val selectedOrigin: City? = null,
    val selectedDestination: City? = null,
    val calculatedRoute: List<City> = emptyList(),
    val isCalculating: Boolean = false
)

class FlightRouterViewModel(
    private val graph: Graph = Graph()
) : ViewModel() {
    private val _uiState = MutableStateFlow(FlightRouterUiState())
    val uiState: StateFlow<FlightRouterUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                availableCities = graph.getAllCities(),
                availableFlights = graph.getAllFlights(),
            )
        }
    }

    fun onOriginSelected(cityId: Int) {
        val city = graph.findCity(cityId)

        _uiState.update { it.copy(selectedOrigin = city) }
    }

    fun onDestinationSelected(cityId: Int) {
        val city = graph.findCity(cityId)

        _uiState.update { it.copy(selectedDestination = city) }
    }

    fun onSearchSelected() {
        val origin = uiState.value.selectedOrigin
        val destination = uiState.value.selectedDestination

        if (origin == null || destination == null) return

        changeIsCalculatingState()

        viewModelScope.launch {
            val bestRoute = graph.findPathBetween(origin, destination)

            val routeOfCities = bestRoute.map { it.currentCity }

            _uiState.update {
                it.copy(
                    calculatedRoute = routeOfCities,
                    isCalculating = false
                )
            }
        }
    }

    private fun changeIsCalculatingState() {
        _uiState.update { it.copy(isCalculating = !it.isCalculating) }
    }
}