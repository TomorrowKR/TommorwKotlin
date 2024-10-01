package com.example.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class RouletteActivity : AppCompatActivity() {
    private lateinit var customRouletteView: CustomRouletteView
    private lateinit var resultTextView: TextView
    private lateinit var spinButton: Button
    private lateinit var confirmButton: Button
    private var isSpinning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)
        customRouletteView = findViewById(R.id.customRouletteView)
        resultTextView = findViewById(R.id.resultTextView)
        spinButton = findViewById(R.id.spinButton)
        confirmButton = findViewById(R.id.confirmButton)
        val sections = intent.getStringArrayListExtra("sections")

        customRouletteView.setSections(sections ?: listOf(""))
        spinButton.setOnClickListener {
            if (!isSpinning) {
                spinRoulette(sections ?: listOf(""))
            }
        }
        confirmButton.setOnClickListener {
            finish()
        }
    }

    private fun spinRoulette(sections: List<String>) {
        isSpinning = true
        val rotationAngle = 3600f + Random.nextInt(360)
        val resultIndex = Random.nextInt(sections.size)
        val finalAngle = rotationAngle + (360f / sections.size) * resultIndex
        val animator = ObjectAnimator.ofFloat(customRouletteView, "rotation", 0f, finalAngle)

        animator.duration = 3000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                val result = sections[resultIndex]
                resultTextView.text = "결과: $result"
                isSpinning = false
            }
        })
        animator.start()
    }
}