package com.github.books.presentation.list

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed class UiState<out D, out E : UiError>(open val data: D?) {
    @Stable
    class Success<out D, out E : UiError>(override val data: D) : UiState<D, E>(data)

    @Stable
    class Error<out D, out E : UiError>(data: D? = null, val error: E) : UiState<D, E>(data)

    @Stable
    class Loading<out D, out E : UiError>(data: D? = null) : UiState<D, E>(data)

    @Immutable
    data object Initial : UiState<Nothing, Nothing>(null)
}
