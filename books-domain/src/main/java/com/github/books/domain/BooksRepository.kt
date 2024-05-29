package com.github.books.domain

import com.github.books.domain.error.DataError
import com.github.books.domain.models.State
import com.github.books.domain.models.Volume
import com.github.books.domain.models.VolumeInfo
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    interface ReadAll {
        suspend fun volumes(query: String): Flow<State<List<Volume>, DataError>>
    }

    interface Details {
        suspend fun volumeInfo(id: Long): VolumeInfo
    }

    interface All : ReadAll, Details
}
