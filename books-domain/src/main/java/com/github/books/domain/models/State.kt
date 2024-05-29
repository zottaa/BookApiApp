package com.github.books.domain.models

import com.github.books.domain.error.Error

typealias RootError = Error

sealed interface State<out D, out E : RootError> {
    class Success<out D, out E : RootError>(val data: D) : State<D, E>
    class Error<out D, out E : RootError>(val data: D? = null, val error: E) : State<D, E>
    class Progress<out D, out E : RootError>(val data: D? = null) : State<D, E>
    data object None : State<Nothing, Nothing>
}
