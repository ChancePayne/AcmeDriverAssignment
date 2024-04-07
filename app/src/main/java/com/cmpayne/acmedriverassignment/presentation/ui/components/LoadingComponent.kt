package com.cmpayne.acmedriverassignment.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmpayne.acmedriverassignment.presentation.ui.theme.AcmeDriverAssignmentTheme

@Composable
fun LoadingComponent(
    modifier: Modifier = Modifier,
    loadingMessage: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            trackColor = MaterialTheme.colorScheme.primary,
        )
        loadingMessage?.let {
            Text(
                modifier = Modifier.padding(top = 36.dp),
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadingComponentPreview() {
    AcmeDriverAssignmentTheme {
        LoadingComponent(loadingMessage = "Loading List of Drivers")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadingComponentPreview_NoMessage() {
    AcmeDriverAssignmentTheme {
        LoadingComponent()
    }
}