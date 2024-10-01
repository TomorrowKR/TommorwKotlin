package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var naverMap: NaverMap
    private val markerViewModel: MarkerViewModel by viewModels()
    private val infoWindow = InfoWindow() // InfoWindow 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)

        val addMarkerActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                loadMarkers()
            }
        }

        binding.addMarkerButton.setOnClickListener {
            val intent = Intent(this, AddMarkerActivity::class.java)
            addMarkerActivityResultLauncher.launch(intent)
        }
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        loadMarkers()

        // 지도 클릭 시 인포 윈도우 닫기
        naverMap.setOnMapClickListener { _, _ ->
            infoWindow.close()
        }
    }

    private fun loadMarkers() {
        markerViewModel.getAllMarkers().observe(this) { markers ->
            markers.forEach { markerEntity ->
                val marker = Marker().apply {
                    position = LatLng(markerEntity.latitude, markerEntity.longitude)
                    map = naverMap
                }

                marker.setOnClickListener {
                    // 마커 클릭 시 인포 윈도우에 텍스트 설정하고 마커 위에 표시
                    infoWindow.adapter =
                        object : InfoWindow.DefaultTextAdapter(applicationContext) {
                            override fun getText(infoWindow: InfoWindow): CharSequence {
                                return markerEntity.title
                            }
                        }
                    infoWindow.open(marker)
                    true
                }
            }
        }
    }
}