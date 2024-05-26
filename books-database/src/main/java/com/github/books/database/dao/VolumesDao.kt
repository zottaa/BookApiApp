package com.github.books.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.books.database.models.VolumeCache

@Dao
interface VolumesDao {
    @Query("SELECT * from volumes")
    suspend fun selectAll(): List<VolumeCache>

    @Query("DELETE from volumes")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(volumes: List<VolumeCache>)

    @Delete
    suspend fun remove(volumes: List<VolumeCache>)
}
