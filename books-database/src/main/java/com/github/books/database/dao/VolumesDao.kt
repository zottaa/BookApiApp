package com.github.books.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.books.database.models.VolumeCache

@Dao
interface VolumesDao {
    @Query("SELECT * from volumes")
    fun selectAll(): PagingSource<Int, VolumeCache>

    @Query("DELETE from volumes")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(volumes: List<VolumeCache>)

    @Delete
    suspend fun remove(volumes: List<VolumeCache>)

    @Query("SELECT * from volumes where  volume_info_id = :volume_info_id")
    suspend fun volumeInfo(volume_info_id: Long): VolumeCache
}
