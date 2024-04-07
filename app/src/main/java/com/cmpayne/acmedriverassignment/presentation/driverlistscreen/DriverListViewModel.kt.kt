package com.cmpayne.acmedriverassignment.presentation.driverlistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpayne.acmedriverassignment.data.DomainError
import com.cmpayne.acmedriverassignment.data.Result
import com.cmpayne.acmedriverassignment.data.services.AcmeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DriverListViewModel : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> =
        MutableStateFlow(ViewState.Loading("Loading List of Drivers"))
    val viewState: StateFlow<ViewState> = _viewState

    private val acmeService = AcmeService.getInstance()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val driverResult = acmeService.fetchDrivers()
            _viewState.update {
                when (driverResult) {
                    is Result.Success -> ViewState.Success(Content(driverResult.data))
                    is Result.Error -> ViewState.Error(driverResult.error)
                }
            }
        }
    }

    // region View Contract
    data class Content(
        val driverNames: List<String>
    ) {
        companion object {
            val FAKE_DATA = Content(
                listOf(
                    "Gregoria Jakubowski",
                    "Brando Kunze",
                    "Pearline Marvin",
                    "Jensen Prohaska",
                    "Ryann West DDS",
                    "Dean Wiegand PhD",
                    "Madalyn Lakin",
                    "Kenyatta Paucek",
                    "Tremaine Kilback DVM",
                    "Willard Willms DDS",
                    "Chasity Davis",
                    "Geoffrey Reichert",
                    "Jermaine Dickens",
                    "Horace Leffler",
                    "Pablo Wolff",
                    "Amy Schmidt",
                    "Vida Schowalter",
                    "Christy Terry",
                    "Autumn Kohler",
                    "Bernita Ratke",
                )
            )
        }
    }

    sealed class ViewState {
        data class Loading(val loadingMessage: String? = null) : ViewState()
        data class Error(val error: DomainError) : ViewState()
        data class Success(val content: Content) : ViewState()
    }
    // endregion
}