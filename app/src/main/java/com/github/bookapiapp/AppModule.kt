package com.github.bookapiapp

import android.content.Context
import com.github.books.database.BooksDatabase
import com.github.books.database.ProvideBooksDatabase
import com.github.books.domain.BooksRepository
import com.github.booksapi.BooksApiService
import com.github.booksapi.ProvideBooksApi
import com.github.data.BooksRepositoryPagingImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindBooksRepositoryReadAll(repositoryImpl: BooksRepositoryPagingImpl): BooksRepository.ReadAll

    @Binds
    abstract fun bindBooksRepositoryAll(repositoryImpl: BooksRepositoryPagingImpl): BooksRepository.All

    @Binds
    abstract fun bindBooksRepositoryDetails(repositoryImpl: BooksRepositoryPagingImpl): BooksRepository.Details

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): BooksDatabase =
            ProvideBooksDatabase(context)

        @Provides
        @Singleton
        fun provideBooksApi(): BooksApiService =
            ProvideBooksApi(BuildConfig.BOOKS_API_BASE_URL)
    }
}
