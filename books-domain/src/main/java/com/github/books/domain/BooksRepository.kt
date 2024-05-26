package com.github.books.domain

import com.github.books.domain.error.DataError
import com.github.books.domain.models.State
import com.github.books.domain.models.Volume
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun volumes(query: String): Flow<State<List<Volume>?, DataError>>
}
