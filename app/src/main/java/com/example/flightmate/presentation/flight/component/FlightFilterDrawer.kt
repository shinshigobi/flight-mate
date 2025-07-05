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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightmate.R
import com.example.flightmate.domain.model.flight.FlightAirline
import com.example.flightmate.domain.model.flight.FlightFilter
import com.example.flightmate.domain.model.flight.FlightStatus

/**
 * 航班篩選側邊欄，提供篩選航班狀態與航空公司。
 *
 * @param filter 當前的篩選條件。
 * @param airlineList 可供選擇的航空公司列表。
 * @param onToggleStatus 切換航班狀態篩選條件的回調函式。
 * @param onToggleAirline 切換航空公司篩選條件的回調函式（以航空公司代碼識別）。
 */
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
                text = stringResource(R.string.flight_status),
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
                text = stringResource(R.string.airline),
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
