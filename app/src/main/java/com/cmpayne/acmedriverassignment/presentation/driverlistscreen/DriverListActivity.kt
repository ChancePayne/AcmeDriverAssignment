package com.cmpayne.acmedriverassignment.presentation.driverlistscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmpayne.acmedriverassignment.R
import com.cmpayne.acmedriverassignment.data.DomainError
import com.cmpayne.acmedriverassignment.framework.extensions.numberOfConsonants
import com.cmpayne.acmedriverassignment.framework.extensions.numberOfVowels
import com.cmpayne.acmedriverassignment.presentation.driverlistscreen.DriverListViewModel.Content.Companion.FAKE_DATA
import com.cmpayne.acmedriverassignment.presentation.driverlistscreen.DriverListViewModel.ViewState
import com.cmpayne.acmedriverassignment.presentation.drivershipmentscreen.DriverShipmentActivity
import com.cmpayne.acmedriverassignment.presentation.ui.components.ErrorComponent
import com.cmpayne.acmedriverassignment.presentation.ui.components.ListItem
import com.cmpayne.acmedriverassignment.presentation.ui.components.LoadingComponent
import com.cmpayne.acmedriverassignment.presentation.ui.theme.AcmeDriverAssignmentTheme

class DriveListActivity : ComponentActivity() {

    private val viewModel: DriverListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewState: ViewState by viewModel.viewState.collectAsState()

            AcmeDriverAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenUi(viewState = viewState)
                }
            }
        }
    }
}

@Composable
private fun ScreenUi(
    viewState: ViewState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    when (viewState) {
        is ViewState.Success ->
            Column(
                modifier = modifier
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    text = stringResource(id = R.string.driver_list_activity_title),
                    style = MaterialTheme.typography.headlineMedium
                )
                LazyColumn {
                    items(viewState.content.driverNames) { name ->
                        ListItem(
                            title = name,
                            onTap = { context.startActivity(DriverShipmentActivity.newIntent(context, name)) },
                            subtitle = "Vowels: ${name.numberOfVowels()} Consonants: ${name.numberOfConsonants()}"
                        )
                    }
                }
            }

        is ViewState.Error -> ErrorComponent(
            title = viewState.error.title,
            subtitle = viewState.error.subtitle
        )

        is ViewState.Loading -> LoadingComponent(loadingMessage = viewState.loadingMessage)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DriverListActivityPreview_Success() {
    AcmeDriverAssignmentTheme {
        ScreenUi(viewState = ViewState.Success(FAKE_DATA))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DriverListActivityPreview_Loading() {
    AcmeDriverAssignmentTheme {
        ScreenUi(viewState = ViewState.Loading("Loading List of Drivers"))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DriverListActivityPreview_Error() {
    AcmeDriverAssignmentTheme {
        ScreenUi(ViewState.Error(DomainError.GeneralError))
    }
}
