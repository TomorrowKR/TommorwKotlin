package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marker_table")
data class MarkerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val latitude: Double,
    val longitude: Double
)