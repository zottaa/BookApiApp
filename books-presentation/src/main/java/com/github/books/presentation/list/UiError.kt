package com.github.books.presentation.list

import android.content.Context
import com.github.books.presentation.R

sealed interface UiError {
    fun message(context: Context): String

    object RequestTimeout : UiError {
        override fun message(context: Context): String =
            context.getString(R.string.request_timeout)

    }

    object TooManyRequests : UiError {
        override fun message(context: Context): String =
            context.getString(R.string.too_many_requests)
    }

    object ServerError : UiError {
        override fun message(context: Context): String =
            context.getString(R.string.server_error)
    }

    object PayloadTooLarge : UiError {
        override fun message(context: Context): String =
            context.getString(R.string.payload_too_large)
    }

    object NoInternet : UiError {
        override fun message(context: Context): String =
            context.getString(R.string.no_internet)
    }

    object Serialization : UiError {
        override fun message(context: Context): String =
            context.getString(R.string.serialization_error)
    }

    object Unknown : UiError {
        override fun message(context: Context): String =
            context.getString(R.string.unknown_error)
    }

    object NotFound : UiError {
        override fun message(context: Context): String =
            context.getString(R.string.not_found)
    }

    object DiskFull : UiError {
        override fun message(context: Context): String =
            context.getString(R.string.disk_full)
    }
}
