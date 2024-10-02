package com.example.myapplication

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private var textSize = 24f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        findViewById<Button>(R.id.buttonIncreaseSize).setOnClickListener {
            textSize += 2
            textView.textSize = textSize
        }
        findViewById<Button>(R.id.buttonDecreaseSize).setOnClickListener {
            textSize -= 2
            textView.textSize = textSize
        }
        findViewById<Button>(R.id.buttonAlignLeft).setOnClickListener {
            textView.gravity = Gravity.START
        }
        findViewById<Button>(R.id.buttonAlignCenter).setOnClickListener {
            textView.gravity = Gravity.CENTER
        }
        findViewById<Button>(R.id.buttonAlignRight).setOnClickListener {
            textView.gravity = Gravity.END
        }
        findViewById<Button>(R.id.buttonBold).setOnClickListener {
            val spannableString = SpannableString(textView.text)
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD), 0,
                spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannableString
        }
        findViewById<Button>(R.id.buttonItalic).setOnClickListener {
            val spannableString = SpannableString(textView.text)
            spannableString.setSpan(
                StyleSpan(Typeface.ITALIC), 0,
                spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannableString
        }
        findViewById<Button>(R.id.buttonUnderline).setOnClickListener {
            val spannableString = SpannableString(textView.text)
            spannableString.setSpan(
                UnderlineSpan(), 0,
                spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannableString
        }
        findViewById<Button>(R.id.buttonRed).setOnClickListener {
            textView.setTextColor(Color.RED)
        }
        findViewById<Button>(R.id.buttonGreen).setOnClickListener {
            textView.setTextColor(Color.GREEN)
        }
        findViewById<Button>(R.id.buttonBlue).setOnClickListener {
            textView.setTextColor(Color.BLUE)
        }
        findViewById<Button>(R.id.buttonBgRed).setOnClickListener {
            textView.setBackgroundColor(Color.RED)
        }
        findViewById<Button>(R.id.buttonBgGreen).setOnClickListener {
            textView.setBackgroundColor(Color.GREEN)
        }
        findViewById<Button>(R.id.buttonBgBlue).setOnClickListener {
            textView.setBackgroundColor(Color.BLUE)
        }
        findViewById<Button>(R.id.buttonReset).setOnClickListener {
            resetTextView()
        }
    }

    private fun resetTextView() {
        textView.text = "Hello, World!"
        textView.textSize = 24f
        textView.setTextColor(Color.BLACK)
        textView.setBackgroundColor(Color.TRANSPARENT)
        textView.gravity = Gravity.CENTER
    }
}

