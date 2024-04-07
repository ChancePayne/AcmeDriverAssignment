package com.cmpayne.acmedriverassignment.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.cmpayne.acmedriverassignment.presentation.ui.theme.AcmeDriverAssignmentTheme

@Composable
fun ErrorComponent(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier,
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            modifier = Modifier,
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ErrorComponentPreview() {
    AcmeDriverAssignmentTheme {
        ErrorComponent(
            title = "Please Try Again",
            subtitle = "Loading List of Drivers"
        )
    }
}
