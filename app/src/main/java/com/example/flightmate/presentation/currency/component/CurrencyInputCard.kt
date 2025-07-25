package com.example.flightmate.presentation.currency.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightmate.R
import com.example.flightmate.domain.model.currency.CurrencyInputState

/**
 * 幣值輸入欄位，包含輸入金額與選擇基準幣別的功能。
 *
 * @param state 當前輸入狀態，包含金額與選擇的幣別。
 * @param currencyList 可供選擇的幣別列表。
 * @param onValueChange 當金額變更時呼叫的回調函式。
 * @param onCurrencyChange 當幣別變更時呼叫的回調函式。
 */
@Composable
fun CurrencyInput(
    state: CurrencyInputState,
    currencyList: List<String>,
    onValueChange: (String) -> Unit,
    onCurrencyChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                var text by remember { mutableStateOf(state.inputAmount.toString()) }
                Text(
                    text = stringResource(R.string.amount),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                        onValueChange(newText)
                    },
                    textStyle = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        }
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.weight(0.5f)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var isMenuExpanded by remember { mutableStateOf(false) }
                Text(
                    text = state.baseCurrency,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        isMenuExpanded = !isMenuExpanded
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        currencyList.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(currency) },
                                onClick = {
                                    onCurrencyChange(currency)
                                    isMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CurrencyInput() {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(R.string.amount),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    state = rememberTextFieldState(initialText = "1,000"),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                )
            }
        }
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.weight(0.5f)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val currencyList = listOf("USA", "JPY", "TWD")
                Text(
                    text = "USA",
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = {}
                    ) {
                        currencyList.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(currency) },
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}
