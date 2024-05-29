package com.github.books.presentation.list

import com.github.books.domain.error.DataError
import com.github.books.domain.models.State
import com.github.books.domain.models.Volume

internal fun State<List<Volume>, DataError>.toUi() : UiState<List<Volume>, UiError> {
    return when (val currentState = this) {
        is State.Success -> UiState.Success(currentState.data)
        is State.Error -> UiState.Error(currentState.data, currentState.error.toUiError())
        is State.Progress -> UiState.Loading(currentState.data)
        State.None -> UiState.Initial
    }
}

internal fun DataError.toUiError() : UiError {
    return when (this) {
        DataError.LocalError.DISK_FULL -> UiError.DiskFull
        DataError.NetworkError.REQUEST_TIMEOUT -> UiError.RequestTimeout
        DataError.NetworkError.TOO_MANY_REQUESTS -> UiError.TooManyRequests
        DataError.NetworkError.SERVER_ERROR -> UiError.ServerError
        DataError.NetworkError.PAYLOAD_TOO_LARGE -> UiError.PayloadTooLarge
        DataError.NetworkError.NO_INTERNET -> UiError.NoInternet
        DataError.NetworkError.SERIALIZATION -> UiError.Serialization
        DataError.NetworkError.UNKNOWN -> UiError.Unknown
        DataError.NetworkError.NOT_FOUND -> UiError.NotFound
    }
}
