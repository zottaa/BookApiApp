package com.github.books.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("volume_info")
data class VolumeInfoCache(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("authors")
    val authors: List<String>,
    @ColumnInfo("publisher")
    val publisher: String,
    @ColumnInfo("publishedDate")
    val publishedDate: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("pageCount")
    val pageCount: Int,
    @Embedded("image_links")
    val imageLinks: ImageLinksCache,
    @ColumnInfo("language")
    val language: String,
    @ColumnInfo("previewLink")
    val previewLink: String,
    @ColumnInfo("infoLink")
    val infoLink: String
)
