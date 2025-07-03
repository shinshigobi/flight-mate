package com.example.flightmate.presentation.flight.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightmate.domain.model.flight.FlightAirline
import com.example.flightmate.domain.model.flight.FlightFilter
import com.example.flightmate.domain.model.flight.FlightStatus

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlightFilterDrawer(
    filter: FlightFilter,
    airlineList: List<FlightAirline>,
    onToggleStatus: (FlightStatus) -> Unit,
    onToggleAirline: (String) -> Unit,
) {
    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7f)) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "班機狀態",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FlightStatus.entries.filter { it != FlightStatus.UNKNOWN }.forEach { status ->
                    val isSelected = status in filter.flightStatus
                    FilterChip(
                        selected = isSelected,
                        label = { Text(status.displayLabel) },
                        onClick = { onToggleStatus(status) },
                        leadingIcon = { ChipLeadingIcon(isSelected) }
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = "航空公司",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                airlineList.forEach { airline ->
                    val isSelected = airline.code in filter.airlineCode
                    FilterChip(
                        selected = isSelected,
                        label = { Text(airline.name) },
                        onClick = { onToggleAirline(airline.code) },
                        leadingIcon = { ChipLeadingIcon(isSelected) }
                    )
                }
            }
        }
    }
}

@Composable
fun ChipLeadingIcon(isSelected: Boolean) {
    AnimatedVisibility(
        visible = isSelected,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally()
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
        )
    }
}
