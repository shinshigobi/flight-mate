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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightmate.R

/**
 * 用於顯示錯誤狀態的元件。
 *
 * @param title 標題。
 * @param message 錯誤訊息。
 * @param buttonLabel 按鈕標籤。
 * @param buttonOnClick 按鈕的點擊事件。
 * @param iconResId 圖標的資源 ID。
 */
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
            .verticalScroll(rememberScrollState())
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
        title = stringResource(R.string.server_error_title),
        message = stringResource(R.string.server_error_msg),
        iconResId = R.drawable.ic_sentiment_very_dissatisfied,
        buttonLabel = stringResource(R.string.retry),
        buttonOnClick = buttonOnClick
    )
}

@Composable
fun NetworkErrorContent(buttonOnClick: () -> Unit) {
    ErrorContent(
        title = stringResource(R.string.network_error_title),
        message = stringResource(R.string.network_error_msg),
        iconResId = R.drawable.ic_signal_disconnected,
        buttonLabel = stringResource(R.string.retry),
        buttonOnClick = buttonOnClick
    )
}

@Composable
fun UnknownErrorContent(buttonOnClick: () -> Unit) {
    ErrorContent(
        title = stringResource(R.string.unknown_error_title),
        message = stringResource(R.string.unknown_error_msg),
        iconResId = R.drawable.ic_sentiment_very_dissatisfied,
        buttonLabel = stringResource(R.string.retry),
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
            text = stringResource(R.string.network_error_title),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.network_error_msg),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.height(80.dp))
        Button(
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.retry)
            )
        }
    }
}
