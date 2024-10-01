package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.NaverMap
import com.example.myapplication.databinding.ActivityAddMarkerBinding

class AddMarkerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMarkerBinding
    private val markerViewModel: MarkerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMarkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMarkerButton.setOnClickListener {
            val markerTitle = binding.markerTitle.text.toString()
            val latitude = binding.latitude.text.toString().toDouble()
            val longitude = binding.longitude.text.toString().toDouble()

            val marker = MarkerEntity(
                title = markerTitle,
                latitude = latitude,
                longitude = longitude
            )

            markerViewModel.insert(marker)
            finish()
        }
    }
}