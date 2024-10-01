package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 게임 시작 버튼 클릭 시
        findViewById<Button>(R.id.btn_start_game).setOnClickListener {
            startActivity(Intent(this, StartGameActivity::class.java))
        }
        // 랭킹 버튼 클릭 시
        findViewById<Button>(R.id.btn_ranking).setOnClickListener {
            startActivity(Intent(this, RankingActivity::class.java))
        }
        // 환경 설정 버튼 클릭 시
        findViewById<Button>(R.id.btn_settings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        // 나가기 버튼 클릭 시
        findViewById<Button>(R.id.btn_exit).setOnClickListener {
            finish()
        }
    }
}