package com.github.books.domain

import androidx.paging.PagingData
import com.github.books.domain.models.Volume
import com.github.books.domain.models.VolumeInfo
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    interface ReadAll {
        suspend fun volumes(query: String): Flow<PagingData<Volume>>
    }

    interface Details {
        suspend fun volumeInfo(id: Long): VolumeInfo
    }

    interface All : ReadAll, Details
}
