package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class LadderInputActivity : AppCompatActivity() {
    private lateinit var inputContainer: LinearLayout
    private lateinit var resultContainer: LinearLayout
    private lateinit var startButton: Button
    private var numberOfParticipants: Int = 4
    private val participantInputs = mutableListOf<EditText>()
    private val resultInputs = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ladder_input)
        numberOfParticipants = intent.getIntExtra(
            MainActivity.EXTRA_LADDER_PARTICIPANTS, 4
        )
        inputContainer = findViewById(R.id.inputContainer)
        resultContainer = findViewById(R.id.resultContainer)
        startButton = findViewById(R.id.startButton)
        setupInputFields()
        setupResultFields()
        startButton.setOnClickListener {
            startLadderGame()
        }
    }

    private fun setupInputFields() {
        for (i in 1..numberOfParticipants) {
            val editText = EditText(this)
            editText.hint = "참가자 $i"
            participantInputs.add(editText)
            inputContainer.addView(editText)
        }
    }

    private fun setupResultFields() {
        for (i in 1..numberOfParticipants) {
            val editText = EditText(this)
            editText.hint = "결과 $i"
            resultInputs.add(editText)
            resultContainer.addView(editText)
        }
    }

    private fun startLadderGame() {
        val participants = participantInputs.map { it.text.toString() }
        val results = resultInputs.map { it.text.toString() }
        val intent = Intent(this, LadderGameActivity::class.java).apply {
            putStringArrayListExtra("participants", ArrayList(participants))
            putStringArrayListExtra("results", ArrayList(results))
        }
        startActivity(intent)
    }
}