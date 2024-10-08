package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startButton.setOnClickListener {
            val animation = CustomProgressAnimation(binding.circularProgress, 0f, 1f)
            animation.duration = 2000
            binding.circularProgress.startAnimation(animation)
            val linearAnimation = CustomProgressAnimation(binding.linearProgress, 0f, 1f)
            linearAnimation.duration = 2000
            binding.linearProgress.startAnimation(linearAnimation)
            val arcAnimation = CustomProgressAnimation(binding.arcProgress, 0f, 1f)
            arcAnimation.duration = 2000
            binding.arcProgress.startAnimation(arcAnimation)
        }
    }
}
