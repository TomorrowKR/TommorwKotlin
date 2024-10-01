package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class LadderLine(val from: Int, val to: Int, val position: Float)
data class Ladder(val lines: List<LadderLine>)

class LadderGameActivity : AppCompatActivity() {
    private lateinit var customLadderView: CustomLadderView
    private lateinit var resultTextView: TextView
    private lateinit var confirmButton: Button
    private var ladder: Ladder = Ladder(emptyList())
    private var gameResults: List<Pair<String, String>> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ladder_game)
        customLadderView = findViewById(R.id.customLadderView)
        resultTextView = findViewById(R.id.resultTextView)
        confirmButton = findViewById(R.id.confirmButton)
        val participants = intent.getStringArrayListExtra("participants") ?: listOf()
        val results = intent.getStringArrayListExtra("results") ?: listOf()
        confirmButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent
                    .FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
            finish()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            ladder = generateLadder(participants.size)
            customLadderView.setLadder(ladder, participants.size)
            startLadderGame(participants, results)
        }, 1500)
    }

    private fun generateLadder(participantCount: Int): Ladder {
        val lines = mutableListOf<LadderLine>()
        val random = kotlin.random.Random
        for (i in 0 until participantCount - 1) {
            val numLines = random.nextInt(1, participantCount)
            for (j in 0 until numLines) {
                val from = i
                val position = random.nextFloat() * 100
                lines.add(LadderLine(from, from + 1, position))
            }
        }
        return Ladder(lines)
    }

    private fun startLadderGame(
        participants: List<String>, results:
        List<String>
    ) {
        gameResults = calculateGameResults(participants, results)
        showResultsSequentially(participants)
    }

    private fun showResultsSequentially(participants: List<String>) {
        var delay = 0L
        participants.forEachIndexed { index, _ ->
            customLadderView.postDelayed({
                val result = gameResults[index]
                resultTextView.append("${result.first} -> ${result.second}\n")
            }, delay)
            delay += 1500L
        }
        customLadderView.postDelayed({
            confirmButton.visibility = Button.VISIBLE
        }, delay)
    }

    private fun calculatePath(startIndex: Int): List<Int> {
        var currentIndex = startIndex
        val path = mutableListOf(currentIndex)
        ladder.lines.sortedBy { it.position }.forEach { line ->
            if (line.from == currentIndex) {
                currentIndex = line.to
                path.add(currentIndex)
            } else if (line.to == currentIndex) {
                currentIndex = line.from
                path.add(currentIndex)
            }
        }
        return path
    }

    private fun calculateGameResults(
        participants: List<String>, results:
        List<String>
    ): List<Pair<String, String>> {
        return participants.mapIndexed { index, participant ->
            val path = calculatePath(index)
            val finalPosition = path.last()
            participant to results[finalPosition]
        }
    }
}
