package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var scheduleButton: Button
    private lateinit var scheduleInfoText: TextView
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // 권한이 부여된 경우 알림 예약
            scheduleWeatherUpdate()
        } else {
            // 권한이 부여되지 않은 경우 처리
            scheduleInfoText.text = "Permission denied. Cannot schedule weather updates ."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scheduleButton = findViewById(R.id.schedule_button)
        scheduleInfoText = findViewById(R.id.schedule_info_text)
        scheduleButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                when {
                    ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        // 권한이 이미 부여된 경우 알림 예약
                        scheduleWeatherUpdate()
                    }

                    shouldShowRequestPermissionRationale(
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) -> {
                        // 권한 설명 필요 시 처리
                        scheduleInfoText.text =
                            "Notification permission is required to schedule weather updates."
                    }

                    else -> {
                        // 권한 요청
                        requestPermissionLauncher.launch(
                            android.Manifest
                                .permission.POST_NOTIFICATIONS
                        )
                    }
                }
            } else {
                // Android 13 미만에서는 권한 요청 없이 바로 알림 예약
                scheduleWeatherUpdate()
            }
        }
    }

    private fun scheduleWeatherUpdate() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val time = prefs.getString("notification_time", "18:00") ?: "18:00"
        val parts = time.split(":").map { it.toInt() }
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, parts[0])
            set(Calendar.MINUTE, parts[1])
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as
                AlarmManager
        val intent = Intent(this, WeatherAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
        )
        // 예약된 시간 정보를 TextView에 출력
        val sdf = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        )
        val scheduledTime = sdf.format(calendar.time)
        scheduleInfoText.text = "Next update scheduled at: $scheduledTime"
    }
}
