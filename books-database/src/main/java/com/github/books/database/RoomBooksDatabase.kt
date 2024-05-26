package com.github.books.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.github.books.database.dao.VolumesDao
import com.github.books.database.models.ImageLinksCache
import com.github.books.database.models.VolumeCache
import com.github.books.database.models.VolumeInfoCache
import com.github.books.database.utils.ListStringTypeConverter

@Database(
    entities = [VolumeCache::class, VolumeInfoCache::class, ImageLinksCache::class],
    version = 1
)
@TypeConverters(ListStringTypeConverter::class)
abstract class RoomBooksDatabase : RoomDatabase() {
    abstract fun volumesDao(): VolumesDao
}

class BooksDatabase internal constructor(
    private val db: RoomBooksDatabase
) {
    val volumesDao
        get() = db.volumesDao()
}

fun ProvideBooksDatabase(applicationContext: Context): BooksDatabase {
    val roomBooksDatabase = Room.databaseBuilder(
        applicationContext.applicationContext,
        RoomBooksDatabase::class.java,
        "books"
    )
        .build()
    return BooksDatabase(roomBooksDatabase)
}


