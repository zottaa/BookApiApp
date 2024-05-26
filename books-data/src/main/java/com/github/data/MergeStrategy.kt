package com.github.data

import com.github.books.domain.error.Error
import com.github.books.domain.models.State
import jakarta.inject.Inject

interface MergeStrategy {
    fun <D, E : Error> combine(cache: State<D, E>, server: State<D, E>): State<D, E>

    class Base @Inject constructor() : MergeStrategy {
        override fun <D, E : Error> combine(cache: State<D, E>, server: State<D, E>): State<D, E> =
            when (server) {
                is State.Success -> State.Success(server.data)
                is State.Error -> handleError(cache, server)
                is State.Progress -> handleProgress(cache)
            }

        private fun <D, E : Error> handleProgress(cache: State<D, E>) =
            when (cache) {
                is State.Success -> State.Progress<D, E>(cache.data)
                else -> State.Progress()
            }

        private fun <D, E : Error> handleError(
            cache: State<D, E>,
            server: State.Error<D, E>
        ) = when (cache) {
            is State.Success -> State.Error(cache.data, server.error)
            else -> State.Error(null, server.error)
        }
    }
}
