package com.github.books.domain.error

sealed interface DataError: Error {
    enum class NetworkError: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        SERVER_ERROR,
        PAYLOAD_TOO_LARGE,
        NO_INTERNET,
        SERIALIZATION,
        UNKNOWN,
        NOT_FOUND
    }

    enum class LocalError: DataError {
        DISK_FULL
    }
}
