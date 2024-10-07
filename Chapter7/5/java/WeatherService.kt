package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherService : Service() {
    private lateinit var locationHelper: LocationHelper
    private lateinit var weatherServiceApi: WeatherServiceApi
    private lateinit var notificationHelper: NotificationHelper
    override fun onCreate() {
        super.onCreate()
        locationHelper = LocationHelper(this)
        weatherServiceApi = WeatherServiceApi.create()
        notificationHelper = NotificationHelper(this)
    }

    override fun onStartCommand(
        intent: Intent?, flags: Int, startId:
        Int
    ): Int {
        fetchWeatherAndNotify()
        return START_NOT_STICKY
    }

    private fun fetchWeatherAndNotify() {
        locationHelper.getLastLocation { location ->
            location?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Toast.makeText(this@WeatherService, "알림", Toast.LENGTH_SHORT).show()
                        val response = weatherServiceApi.getWeather(
                            it.latitude, it.longitude, "API KEY"
                        )
                        withContext(Dispatchers.Main) {
                            notificationHelper.sendNotification("Current temperature: ${response.main.temp}")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@WeatherService,
                                "Failed to fetch weather data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}