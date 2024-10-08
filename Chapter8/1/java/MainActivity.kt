package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var locationHelper: LocationHelper
    private lateinit var ttsHelper: TtsHelper
    private lateinit var locationTextView: TextView
    private lateinit var startTourButton: Button
    private lateinit var stopTourButton: Button

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val locationPermissionGranted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val audioPermissionGranted = permissions[Manifest.permission.RECORD_AUDIO] ?: false

            if (locationPermissionGranted && audioPermissionGranted) {
                startTour()
            } else {
                // 권한이 거부된 경우의 처리
                locationTextView.text = "권한이 필요합니다."
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationHelper = LocationHelper(this)
        ttsHelper = TtsHelper(this)

        locationTextView = findViewById(R.id.locationTextView)
        startTourButton = findViewById(R.id.startTourButton)
        stopTourButton = findViewById(R.id.stopTourButton)

        startTourButton.setOnClickListener {
            checkPermissionsAndStartTour()
        }

        stopTourButton.setOnClickListener {
            stopTour()
            startTourButton.visibility = Button.VISIBLE
            stopTourButton.visibility = Button.GONE
        }
    }

    private fun checkPermissionsAndStartTour() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this, Manifest.permission.RECORD_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED -> {
                // 모든 권한이 허용된 경우 투어 시작
                startTour()
            }

            else -> {
                // 권한 요청
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.RECORD_AUDIO
                    )
                )
            }
        }
    }

    private fun startTour() {
        observeLocationUpdates()
        startTourButton.visibility = Button.GONE
        stopTourButton.visibility = Button.VISIBLE
        lifecycleScope.launch {
            ttsHelper.speak("투어 가이드를 시작합니다.")
        }
    }

    private fun observeLocationUpdates() {
        lifecycleScope.launch {
            locationHelper.getLocationUpdates().collectLatest { location ->
                locationTextView.text = "현재 위치: ${location.latitude}, ${location.longitude}"
                handleLocationUpdate(location.latitude, location.longitude)
            }
        }
    }

    private suspend fun handleLocationUpdate(
        latitude: Double,
        longitude: Double
    ) {
        try {
            val locationInfo =
                ApiClient.service.getLocationInfo(latitude, longitude)
            ttsHelper.speak("${locationInfo.title}에 도착하셨습니다. ${locationInfo.description}")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopTour() {
        // 위치 업데이트 멈추는 로직 필요 시 추가
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsHelper.shutdown()
    }
}