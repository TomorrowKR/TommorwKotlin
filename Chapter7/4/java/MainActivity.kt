package com.example.myapplication

import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var searchBar: EditText
    private lateinit var searchButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchBar = findViewById(R.id.search_bar)
        searchButton = findViewById(R.id.search_button)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        searchButton.setOnClickListener {
            val location = searchBar.text.toString()
            if (location.isNotEmpty()) {
                searchLocation(location)
            } else {
                Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val initialLocation = LatLng(-34.0, 151.0)
        mMap.addMarker(
            MarkerOptions().position(initialLocation)
            .title("Marker in Sydney"))
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
            initialLocation, 10f))
    }
    private fun searchLocation(location: String) {
        val geocoder = Geocoder(this)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val addressList = geocoder.getFromLocationName(location, 1)
                if (!addressList.isNullOrEmpty()) {
                    val address = addressList[0]
                    val latLng = LatLng(address.latitude,
                        address.longitude)
                    withContext(Dispatchers.Main) {
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(latLng)
                            .title(location))
                        mMap.animateCamera(CameraUpdateFactory
                            .newLatLngZoom(latLng, 10f))
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Location not found", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Geocoder service not available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}



