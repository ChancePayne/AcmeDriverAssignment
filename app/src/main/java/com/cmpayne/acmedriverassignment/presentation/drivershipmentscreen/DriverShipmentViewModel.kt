package com.cmpayne.acmedriverassignment.presentation.drivershipmentscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cmpayne.acmedriverassignment.data.DomainError
import com.cmpayne.acmedriverassignment.data.Result
import com.cmpayne.acmedriverassignment.domain.models.Address
import com.cmpayne.acmedriverassignment.domain.usecases.ShipmentAssignmentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DriverShipmentViewModel(driverName: String?) : ViewModel() {

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading())
    val viewState: StateFlow<ViewState> = _viewState

    private val shipmentAssignmentUseCase = ShipmentAssignmentUseCase()

    init {
        if (driverName == null) {
            _viewState.update {
                ViewState.Error(DomainError.GeneralError)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val assignmentResult = shipmentAssignmentUseCase.getAssignmentForDriver(driverName)
                _viewState.update {
                    when (assignmentResult) {
                        is Result.Success -> ViewState.Success(
                            Content(
                                driverName = driverName,
                                assignedDestination = assignmentResult.data
                            )
                        )

                        is Result.Error -> ViewState.Error(assignmentResult.error)
                    }
                }
            }
        }
    }

    // region View Contract
    data class Content(
        val driverName: String,
        val assignedDestination: Address,
    ) {
        companion object {
            val FAKE_DATA = Content(
                driverName = "Archer Payne",
                assignedDestination = Address("7281", "Noah Ct", null, "Sparks", "NV", "89436")
            )
        }
    }

    sealed class ViewState {
        data class Loading(val loadingMessage: String? = null) : ViewState()
        data class Error(val error: DomainError) : ViewState()
        data class Success(val content: Content) : ViewState()
    }
    // endregion

    companion object {

        fun Factory(driverName: String?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DriverShipmentViewModel(driverName)
            }
        }
    }
}