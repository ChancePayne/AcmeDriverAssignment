package com.cmpayne.acmedriverassignment.presentation.drivershipmentscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cmpayne.acmedriverassignment.data.DomainError
import com.cmpayne.acmedriverassignment.presentation.drivershipmentscreen.DriverShipmentViewModel.Content.Companion.FAKE_DATA
import com.cmpayne.acmedriverassignment.presentation.ui.components.ErrorComponent
import com.cmpayne.acmedriverassignment.presentation.ui.components.LoadingComponent
import com.cmpayne.acmedriverassignment.presentation.ui.theme.AcmeDriverAssignmentTheme

class DriverShipmentActivity : ComponentActivity() {

    companion object {
        private const val EXTRA_DRIVER_NAME = "com.cmpayne.acmedriverassignment.e0de33a3-2641-46ea-99b8-3e45f8e83f8c"

        fun newIntent(context: Context, driverName: String): Intent {
            val intent = Intent(context, DriverShipmentActivity::class.java)
            intent.putExtra(EXTRA_DRIVER_NAME, driverName)
            return intent
        }
    }

    private val viewModel: DriverShipmentViewModel by viewModels {
        DriverShipmentViewModel.Factory(intent.getStringExtra(EXTRA_DRIVER_NAME))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewState: DriverShipmentViewModel.ViewState by viewModel.viewState.collectAsState()

            AcmeDriverAssignmentTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ScreenUi(viewState = viewState)
                }
            }
        }
    }
}

@Composable
private fun ScreenUi(
    modifier: Modifier = Modifier,
    viewState: DriverShipmentViewModel.ViewState
) {
    when (viewState) {
        is DriverShipmentViewModel.ViewState.Success -> Column(
            modifier = modifier
        ) {
            Text(
                modifier = Modifier,
                text = viewState.content.driverName,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                modifier = Modifier,
                text = viewState.content.assignedDestination.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        is DriverShipmentViewModel.ViewState.Error -> ErrorComponent(
            title = viewState.error.title,
            subtitle = viewState.error.subtitle
        )

        is DriverShipmentViewModel.ViewState.Loading -> LoadingComponent(
            loadingMessage = viewState.loadingMessage
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DriverShipmentActivityPreview_Success() {
    AcmeDriverAssignmentTheme {
        ScreenUi(viewState = DriverShipmentViewModel.ViewState.Success(FAKE_DATA))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DriverShipmentActivityPreview_Loading() {
    AcmeDriverAssignmentTheme {
        ScreenUi(viewState = DriverShipmentViewModel.ViewState.Loading())
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DriverShipmentActivityPreview_Error() {
    AcmeDriverAssignmentTheme {
        ScreenUi(
            viewState = DriverShipmentViewModel.ViewState.Error(DomainError.GeneralError)
        )
    }
}