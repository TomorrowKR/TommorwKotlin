package com.example.myapplication.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HealthDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(healthData: HealthData)

    @Update
    suspend fun update(healthData: HealthData)

    @Delete
    suspend fun delete(healthData: HealthData)

    @Query(
        """
        SELECT 
        SUM(exerciseDuration) AS totalExercise, 
        SUM(foodCalories) AS totalCalories, 
        SUM(sleepDuration) AS totalSleep 
        FROM health_data 
        WHERE strftime('%Y-%m', date / 1000, 'unixepoch') = :month
    """
    )

    fun getMonthlyStatistics(month: String): LiveData<MonthlyStatistics>

    @Query("SELECT * FROM health_data ORDER BY date ASC")
    fun getAllHealthData(): LiveData<List<HealthData>>
}