package com.example.flightmate.presentation.flight.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.flightmate.R
import com.example.flightmate.domain.model.flight.FlightInfo

/**
 * 航班資訊卡片。
 *
 * @param flight 航班資訊。
 * @param modifier Composable 修飾符。
 */
@Composable
fun FlightCard(
    flight: FlightInfo,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        modifier = modifier
    ) {
        Column {
            val rowModifier = Modifier.padding(12.dp)
            Row(modifier = rowModifier) {
                FlightAirlineInfo(
                    airlineName = flight.airline.name,
                    airlineCode = flight.airline.code,
                    logoUrl = flight.airline.logoUrl,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = flight.airline.no,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            HorizontalDivider()
            Row(modifier = rowModifier) {
                FlightTime(
                    label = stringResource(R.string.expected_time),
                    content = flight.expectTime,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                FlightTime(
                    label = stringResource(R.string.actual_time),
                    content = flight.realTime,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = flight.flightStatus.displayLabel,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(modifier = rowModifier) {
                IconTextRow(
                    iconResId = R.drawable.ic_location_on,
                    text = "${flight.upAirportName}．${flight.upAirportCode}",
                    modifier = Modifier.weight(1f)
                )
                if (flight.airBoardingGate.isNotBlank()) {
                    IconTextRow(
                        iconResId = R.drawable.ic_door_front,
                        text = stringResource(R.string.boarding_gate) + "．" + flight.airBoardingGate,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun IconTextRow(
    @DrawableRes iconResId: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 12.sp
        )
    }
}

@Composable
fun FlightTime(
    label: String,
    content: String,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp,
        )
        Text(
            text = content,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FlightAirlineInfo(
    airlineName: String,
    airlineCode: String,
    logoUrl: String,
    modifier: Modifier
) {
    ConstraintLayout(modifier = modifier) {
        val (logo, name, code) = createRefs()

        AsyncImage(
            model = logoUrl,
            contentDescription = airlineName + stringResource(R.string.logo),
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .constrainAs(logo) {
                    top.linkTo(name.top)
                    bottom.linkTo(name.bottom)
                }
        )
        Text(
            text = airlineName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(parent.top)
                start.linkTo(logo.end, margin = 8.dp)
            }
        )
        Text(
            text = airlineCode,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp,
            modifier = Modifier.constrainAs(code) {
                top.linkTo(name.bottom)
                start.linkTo(name.start)
            }
        )
    }
}
