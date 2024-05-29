package com.github.data

import com.github.books.database.BooksDatabase
import com.github.books.domain.BooksRepository
import com.github.books.domain.error.DataError
import com.github.books.domain.models.State
import com.github.books.domain.models.Volume
import com.github.books.domain.models.VolumeInfo
import com.github.booksapi.BooksApiService
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

class BooksRepositoryImpl @Inject constructor(
    private val booksApiService: BooksApiService,
    private val db: BooksDatabase,
    private val mergeStrategy: MergeStrategy
) : BooksRepository.All {
    override suspend fun volumes(query: String): Flow<State<List<Volume>, DataError>> =
        withContext(Dispatchers.IO) {
            val dbRequestFlow = flow<State<List<Volume>, DataError>> {
                emit(State.Progress())
                val volumes = db.volumesDao.selectAll().map { volumeCache ->
                    volumeCache.toVolume()
                }
                emit(State.Success(volumes))
            }

            val apiRequestFlow = flow {
                emit(State.Progress())
                try {
                    val volumes = booksApiService.volumes(query).items.mapIndexed { index, volumeCloud ->
                        volumeCloud.toVolume(System.currentTimeMillis() + index)
                    }
                    emit(State.Success(volumes))
                    db.volumesDao.clear()
                    db.volumesDao.insert(volumes.map {
                        it.toVolumeCache()
                    })
                    println(db.volumesDao.selectAll())
                } catch (exception: Exception) {
                    handleException(exception)
                }
            }

            dbRequestFlow.combine(apiRequestFlow, mergeStrategy::combine)
        }

    override suspend fun volumeInfo(id: Long): VolumeInfo =
        withContext(Dispatchers.IO) {
            val volumeInfo = db.volumesDao.volumeInfo(id).volumeInfo
            volumeInfo.toVolumeInfo()
        }

    private suspend fun FlowCollector<State<List<Volume>, DataError>>.handleException(
        exception: Throwable
    ) {
        println(exception.message)
        when (exception) {
            is HttpException -> mapHttpExceptionToStateError(exception)
            is UnknownHostException -> emit(State.Error(error = DataError.NetworkError.NO_INTERNET))
            is IllegalArgumentException -> emit(State.Error(error = DataError.NetworkError.SERIALIZATION))
            else -> emit(State.Error(error = DataError.NetworkError.UNKNOWN))
        }
    }

    private suspend fun FlowCollector<State<List<Volume>, DataError>>.mapHttpExceptionToStateError(
        exception: HttpException
    ) {
        when (exception.code()) {
            404 -> emit(State.Error(error = DataError.NetworkError.NOT_FOUND))
            408 -> emit(State.Error(error = DataError.NetworkError.REQUEST_TIMEOUT))
            429 -> emit(State.Error(error = DataError.NetworkError.TOO_MANY_REQUESTS))
            413 -> emit(State.Error(error = DataError.NetworkError.PAYLOAD_TOO_LARGE))
            500 -> emit(State.Error(error = DataError.NetworkError.SERVER_ERROR))
        }
    }
}
