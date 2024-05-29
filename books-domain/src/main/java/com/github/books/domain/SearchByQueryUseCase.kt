package com.github.books.domain

import com.github.books.domain.error.DataError
import com.github.books.domain.models.State
import com.github.books.domain.models.Volume
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SearchByQueryUseCase @Inject constructor(
    private val booksRepository: BooksRepository.ReadAll
) {
    suspend operator fun invoke(query: String): Flow<State<List<Volume>, DataError>> =
        if (query.isNotBlank()) {
            booksRepository.volumes(query)
        } else {
            flowOf(State.None)
        }
}
