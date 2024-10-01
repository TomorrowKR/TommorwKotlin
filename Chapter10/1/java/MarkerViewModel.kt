package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MarkerViewModel(application: Application) : AndroidViewModel(application) {
    private val markerDao = MarkerDatabase.getDatabase(application).markerDao()

    fun insert(marker: MarkerEntity) = viewModelScope.launch {
        markerDao.insert(marker)
    }

    fun getAllMarkers(): LiveData<List<MarkerEntity>> {
        return markerDao.getAllMarkers()
    }
}
