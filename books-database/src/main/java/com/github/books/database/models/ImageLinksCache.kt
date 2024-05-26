package com.github.books.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_links")
data class ImageLinksCache(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("smallThumbnail")
    val smallThumbnail: String,
    @ColumnInfo("thumbnail")
    val thumbnail: String
)
