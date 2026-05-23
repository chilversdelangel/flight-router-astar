package mx.tecnm.cdmadero.flightrouter.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitySelector(
    label: String,
    options: Map<Int, String>,
    isLocked: Boolean,
    selectedCityId: Int?,
    onSelectCity: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = typography.labelLarge,
            color = colorScheme.primary
        )

        ExposedDropdownMenuBox(
            expanded = if (isLocked) false else expanded,
            onExpandedChange = {
                if (!isLocked) { expanded = it }
            }
        ) {
            OutlinedTextField(
                value = options[selectedCityId] ?: "Selecciona una ciudad",
                onValueChange = {},
                readOnly = true,
                enabled = !isLocked,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { (id, cityName) ->
                    DropdownMenuItem(
                        text = { Text(cityName) },
                        onClick = {
                            onSelectCity(id)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}