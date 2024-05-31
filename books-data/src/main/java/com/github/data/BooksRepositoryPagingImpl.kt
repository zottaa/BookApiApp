package com.github.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.github.books.database.BooksDatabase
import com.github.books.domain.BooksRepository
import com.github.books.domain.models.Volume
import com.github.books.domain.models.VolumeInfo
import com.github.booksapi.BooksApiService
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BooksRepositoryPagingImpl @Inject constructor(
    private val db: BooksDatabase,
    private val api: BooksApiService,
) : BooksRepository.All {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun volumes(query: String): Flow<PagingData<Volume>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = BooksMediator(
                db = db,
                api = api,
                query = query
            ),
            pagingSourceFactory = { db.volumesDao.selectAll() }
        )

        val scope = CoroutineScope(currentCoroutineContext())

        return pager.flow.map { pagingData ->
            pagingData.map { volumeCache ->
                volumeCache.toVolume()
            }
        }.cachedIn(scope)
    }

    override suspend fun volumeInfo(id: Long): VolumeInfo =
        db.volumesDao.volumeInfo(id).volumeInfo.toVolumeInfo()

}
