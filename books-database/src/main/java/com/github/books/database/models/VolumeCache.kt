package com.github.books.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "volumes")
data class VolumeCache(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @Embedded("volume_info")
    val volumeInfo: VolumeInfoCache
)
