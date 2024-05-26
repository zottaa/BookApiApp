package com.github.books.presentation

import com.github.books.domain.BooksRepository
import com.github.books.domain.models.Volume
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchByQueryUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) {
    suspend operator fun invoke(query: String): Flow<UiState<List<Volume>?, UiError>> =
        if (query.isNotBlank())
            booksRepository.volumes(query).map { state ->
                state.toUi()
            }
        else
            flowOf(UiState.Initial)

}
