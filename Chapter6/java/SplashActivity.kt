package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private var progressStatus = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.logo)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        logo.startAnimation(fadeIn)

        val appNameTextView = findViewById<TextView>(R.id.appNameTextView)
        val appVersionTextView = findViewById<TextView>(R.id.appVersionTextView)
        val versionName = getAppVersionName()
        appVersionTextView.text = getString(R.string.version_format, versionName)

        // 일정 시간 후 메인 액티비티로 전환
        progressBar = findViewById<ProgressBar>(R.id.loadingProgressBar)

        Thread {
            while (progressStatus < 100) {
                progressStatus += 1
                handler.post {
                    progressBar.progress = progressStatus
                }
                Thread.sleep(30)
            }
            // 메인 액티비티로 전환
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.start()
    }

    private fun getAppVersionName(): String {
        return try {
            val packageInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName ?: "1.0"
        } catch (e: PackageManager.NameNotFoundException) {
            "1.0"
        }
    }
}