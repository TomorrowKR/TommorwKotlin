package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class LottoGameActivity : AppCompatActivity() {
    private var currentStage = 1
    private var maxStage = 23
    private lateinit var correctButton: Button
    private lateinit var wrongButton: Button
    private lateinit var stageTextView: TextView
    private var correctChoice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto_game)
        stageTextView = findViewById(R.id.stageTextView)
        correctButton = findViewById(R.id.button1)
        wrongButton = findViewById(R.id.button2)
        val gameLevel = intent.getStringExtra(MainActivity.EXTRA_LOTTO_GAME_LEVEL)

        maxStage = when (gameLevel) {
            "1등" -> 23
            "2등" -> 20
            "3등" -> 15
            "4등" -> Random.nextInt(9, 11)
            "5등" -> Random.nextInt(5, 7)
            else -> 1
        }
        setNewStage()
        correctButton.setOnClickListener { handleChoice(true) }
        wrongButton.setOnClickListener { handleChoice(false) }
    }

    private fun setNewStage() {
        stageTextView.text = "현재	단계: $currentStage / $maxStage"
        correctChoice = Random.nextInt(2)
    }

    private fun handleChoice(isCorrect: Boolean) {
        if (isCorrect == (correctChoice == 0)) {
            if (currentStage < maxStage) {
                currentStage++
                setNewStage()
            } else {
                Toast.makeText(this, "축하합니다! 게임을 완료했습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        } else {
            Toast.makeText(this, "게임 오버! 잘못된 선택입니다.", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
