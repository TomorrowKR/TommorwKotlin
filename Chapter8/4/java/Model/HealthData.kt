package com.example.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_data")
data class HealthData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val exerciseDuration: Int, // in minutes
    val foodCalories: Int,
    val sleepDuration: Int // in hours
)

data class MonthlyStatistics(
    val totalExercise: Int,
    val totalCalories: Int,
    val totalSleep: Int
)