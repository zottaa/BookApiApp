package com.github.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.github.books.database.BooksDatabase
import com.github.books.database.models.VolumeCache
import com.github.booksapi.BooksApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BooksMediator(
    private val db: BooksDatabase,
    private val api: BooksApiService,
    private val query: String
) : RemoteMediator<Int, VolumeCache>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, VolumeCache>
    ): MediatorResult {
        return withContext(Dispatchers.IO) {
            try {
                val loadKey = when (loadType) {
                    LoadType.REFRESH -> START_INDEX
                    LoadType.PREPEND -> return@withContext MediatorResult.Success(
                        endOfPaginationReached = true
                    )

                    LoadType.APPEND -> {
                        val lastItem = state.lastItemOrNull()
                        lastItem?.volumeInfo?.id?.plus(1) ?: START_INDEX
                    }
                }

                val volumes =
                    api.volumes(
                        query = query,
                        startIndex = loadKey.toInt()
                    ).items

                if (loadType == LoadType.REFRESH) {
                    db.volumesDao.clear()
                }
                db.volumesDao.insert(volumes.mapIndexed { index, volumeCloud ->
                    volumeCloud.toVolumeCache(index + loadKey.toLong())
                })

                MediatorResult.Success(
                    endOfPaginationReached = volumes.isEmpty()
                )

            } catch (e: IOException) {
                MediatorResult.Error(e)
            } catch (e: HttpException) {
                MediatorResult.Error(e)
            }
        }
    }

    companion object {
        private const val START_INDEX = 0
    }
}
