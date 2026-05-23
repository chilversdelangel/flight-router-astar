package mx.tecnm.cdmadero.flightrouter.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.tecnm.cdmadero.flightrouter.presentation.components.CitySelector
import mx.tecnm.cdmadero.flightrouter.presentation.components.GraphCanvas

@Composable
fun FlightRouterScreen(
    viewModel: FlightRouterViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cities = uiState.availableCities.associate { it.id to it.name }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Flight Router \uD83D\uDEEB",
            style = typography.headlineLarge
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Por favor, seleccione la ciudad de origen y la ciudad de destino.",
            style = typography.bodyLarge
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
            ) {

                GraphCanvas(
                    uiState.availableCities,
                    uiState.availableFlights,
                    uiState.calculatedRoute,
                    Modifier.fillMaxSize()
                )
            }

            Spacer(
                modifier = Modifier.width(24.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1.2f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CitySelector(
                    label = "Ciudad de origen",
                    options = cities,
                    selectedCityId = uiState.selectedOrigin?.id,
                    isLocked = uiState.isCalculating,
                    onSelectCity = { cityId ->
                        viewModel.onOriginSelected(cityId)
                    }
                )

                Spacer(modifier = Modifier.size(16.dp))

                CitySelector(
                    label = "Ciudad de destino",
                    options = cities,
                    selectedCityId = uiState.selectedDestination?.id,
                    isLocked = uiState.isCalculating,
                    onSelectCity = { cityId ->
                        viewModel.onDestinationSelected(cityId)
                    }
                )
                Button(
                    onClick = { viewModel.onSearchSelected() },
                    enabled = uiState.availableCities.isNotEmpty() &&
                            uiState.selectedOrigin != null &&
                            uiState.selectedDestination != null &&
                            !uiState.isCalculating &&
                            (uiState.selectedOrigin != uiState.selectedDestination )
                ) {
                    Text(
                        text = "Buscar Ruta",
                        style = typography.labelLarge
                    )
                }
            }
        }
    }
}