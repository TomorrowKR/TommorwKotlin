package com.example.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.HealthData
import com.example.myapplication.Model.HealthDatabase
import com.example.myapplication.Model.MonthlyStatistics
import kotlinx.coroutines.launch

class HealthViewModel(application: Application) :
    AndroidViewModel(application) {
    private val healthDataDao = HealthDatabase.getDatabase(application)
        .healthDataDao()
    val allHealthData: LiveData<List<HealthData>> = healthDataDao
        .getAllHealthData()

    fun insertHealthData(healthData: HealthData) = viewModelScope.launch {
        healthDataDao.insert(healthData)
    }

    fun updateHealthData(healthData: HealthData) = viewModelScope.launch {
        healthDataDao.update(healthData)
    }

    fun deleteHealthData(healthData: HealthData) = viewModelScope.launch {
        healthDataDao.delete(healthData)
    }

    fun getMonthlyStatistics(month: String): LiveData<MonthlyStatistics> {
        return healthDataDao.getMonthlyStatistics(month)
    }
}