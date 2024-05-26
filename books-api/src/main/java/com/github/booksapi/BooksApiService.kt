package com.github.booksapi

import com.github.booksapi.models.VolumeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {
    @GET("volumes")
    suspend fun volumes(
        @Query("q") query: String
    ): VolumeResponse
}

fun ProvideBooksApi(baseUrl: String): BooksApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(BooksApiService::class.java)
}
