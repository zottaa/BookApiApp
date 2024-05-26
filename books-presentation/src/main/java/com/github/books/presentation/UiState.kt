package com.github.books.presentation

sealed class UiState<out D, out E : UiError>(val data: D?) {
    class Success<out D, out E : UiError>(data: D?) : UiState<D, E>(data)
    class Error<out D, out E : UiError>(data: D? = null, val error: E) : UiState<D, E>(data)
    class Loading<out D, out E: UiError>(data: D? = null) : UiState<D, E>(data)
    object Initial : UiState<Nothing, Nothing>(null)
}
