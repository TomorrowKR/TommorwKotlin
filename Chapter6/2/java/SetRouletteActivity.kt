package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SetRouletteActivity : AppCompatActivity() {
    private lateinit var inputContainer: LinearLayout
    private lateinit var confirmButton: Button
    private val sectionInputs = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_roulette)
        inputContainer = findViewById(R.id.inputContainer)
        confirmButton = findViewById(R.id.confirmButton)
        val numberOfSlots = intent.getIntExtra("numberOfSlots", 4)
        for (i in 1..numberOfSlots) {
            val editText = EditText(this)
            editText.hint = "칸 $i 입력"
            sectionInputs.add(editText)
            inputContainer.addView(editText)
        }

        confirmButton.setOnClickListener {
            val sections = sectionInputs.map { it.text.toString() }
            val resultIntent = Intent().apply {
                putStringArrayListExtra("sections", ArrayList(sections))
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}