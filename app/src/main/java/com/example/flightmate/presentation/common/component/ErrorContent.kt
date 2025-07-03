package com.example.flightmate.presentation.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightmate.R

@Composable
fun ErrorContent(
    title: String,
    message: String,
    buttonLabel: String,
    buttonOnClick: () -> Unit,
    @DrawableRes iconResId: Int
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(144.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(80.dp))
        Button(
            onClick = buttonOnClick
        ) {
            Text(
                text = buttonLabel
            )
        }
    }
}

@Composable
fun ApiErrorContent(buttonOnClick: () -> Unit) {
    ErrorContent(
        title = "伺服器錯誤",
        message = "請重新嘗試",
        iconResId = R.drawable.ic_sentiment_very_dissatisfied,
        buttonLabel = "重新嘗試",
        buttonOnClick = buttonOnClick
    )
}

@Composable
fun NetworkErrorContent(buttonOnClick: () -> Unit) {
    ErrorContent(
        title = "網路連線失敗",
        message = "請確認您的網路是否正常，或稍後再試。",
        iconResId = R.drawable.ic_signal_disconnected,
        buttonLabel = "重新嘗試",
        buttonOnClick = buttonOnClick
    )
}

@Composable
fun UnknownErrorContent(buttonOnClick: () -> Unit) {
    ErrorContent(
        title = "發生未知錯誤",
        message = "請重新嘗試",
        iconResId = R.drawable.ic_sentiment_very_dissatisfied,
        buttonLabel = "重新嘗試",
        buttonOnClick = buttonOnClick
    )
}

@Preview
@Composable
fun ErrorContent() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_signal_disconnected),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(144.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "網路連線失敗",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "請確認您的網路是否正常，或稍後再試。",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(80.dp))
        Button(
            onClick = {}
        ) {
            Text(
                text = "重新嘗試"
            )
        }
    }
}
