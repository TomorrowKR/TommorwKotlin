package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import kotlinx.coroutines.channels.awaitClose

class LocationHelper(private val context: Context) {

    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    fun getLocationUpdates(): Flow<Location> = callbackFlow {
        val listener = LocationListener { location ->
            trySend(location)
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 10f, listener)

        awaitClose { locationManager.removeUpdates(listener) }
    }
}
