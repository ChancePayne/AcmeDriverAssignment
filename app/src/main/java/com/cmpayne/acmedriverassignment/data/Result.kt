package com.cmpayne.acmedriverassignment.data

sealed class Result<D, E> {
    data class Success<D, E>(val data: D) : Result<D, E>()
    data class Error<D, E>(val error: E) : Result<D, E>()
}

sealed class DomainError(val title: String, val subtitle: String) {
    data object GeneralError : DomainError(
        title = "Check Back Later",
        subtitle = "Shipment assignments aren't available at this time."
    )
}