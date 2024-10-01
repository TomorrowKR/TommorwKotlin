package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MarkerDao {
    @Insert
    suspend fun insert(marker: MarkerEntity)

    @Query("SELECT * FROM marker_table")
    fun getAllMarkers(): LiveData<List<MarkerEntity>>
}