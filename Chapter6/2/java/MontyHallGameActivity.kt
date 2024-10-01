package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MontyHallGameActivity : AppCompatActivity() {
    private var numberOfDoors = 3
    private var carBehindDoor = -1
    private var userSelection = -1
    private var revealedDoor = -1
    private lateinit var doorsContainer: LinearLayout
    private lateinit var resultTextView: TextView
    private lateinit var confirmButton: Button
    private lateinit var retryButton: Button
    private var gameStage = 0
    private val doors = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monty_hall_game)
        numberOfDoors = intent.getIntExtra("numberOfDoors", 3)
        carBehindDoor = Random.nextInt(numberOfDoors)
        doorsContainer = findViewById(R.id.doorsContainer)
        resultTextView = findViewById(R.id.resultTextView)
        confirmButton = findViewById(R.id.confirmButton)
        retryButton = findViewById(R.id.retryButton)
        setupDoors()
    }

    private fun setupDoors() {
        doorsContainer.removeAllViews()
        doors.clear()
        for (i in 0 until numberOfDoors) {
            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1f
                ).apply {
                    setMargins(8, 0, 8, 0) // Margin between doors
                }
                setImageResource(R.drawable.box)
                tag = i
                scaleType = ImageView.ScaleType.FIT_CENTER
                adjustViewBounds = true
                setOnClickListener { onDoorSelected(i) }
            }
            doors.add(imageView)
            doorsContainer.addView(imageView)
        }
    }

    private fun onDoorSelected(doorIndex: Int) {
        if (gameStage == 0) {
            userSelection = doorIndex
            revealGoatDoor()
            gameStage = 1
            disableDoors()
        } else if (gameStage == 1) {
            if (doorIndex != revealedDoor) {
                userSelection = doorIndex
            }
            revealResult()
        }
    }

    private fun disableDoors() {
        doors.forEach { it.isEnabled = false }
    }

    private fun revealGoatDoor() {
        val possibleDoors = (0 until numberOfDoors).filter {
            it !=
                    userSelection && it != carBehindDoor
        }
        revealedDoor = possibleDoors.random()
        doors[revealedDoor].setImageResource(R.drawable.goat)
        resultTextView.text = "문	${revealedDoor + 1} 뒤에	염소가	있습니다. 선택을	바꾸시겠습니까?"
        confirmButton.visibility = Button.VISIBLE
        confirmButton.setOnClickListener {
            enableDoorsExcept(revealedDoor)
            resultTextView.text = "문을 선택해주세요."
            confirmButton.visibility = Button.GONE
        }
    }

    private fun enableDoorsExcept(exceptIndex: Int) {
        doors.forEachIndexed { index, imageView ->
            imageView.isEnabled = index != exceptIndex
        }
    }

    private fun revealResult() {
        disableDoors()
        for (i in doors.indices) {
            val resId = if (i == carBehindDoor) R.drawable.car else R.drawable.goat
            doors[i].setImageResource(resId)
        }
        val resultMessage = if (userSelection == carBehindDoor) {
            "축하합니다! 자동차를 찾았습니다!"
        } else {
            "안타깝습니다. 염소를 찾았습니다."
        }
        resultTextView.text = resultMessage
        confirmButton.visibility = Button.VISIBLE
        retryButton.visibility = Button.VISIBLE
        confirmButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        retryButton.setOnClickListener {
            val intent = Intent(this, MontyHallGameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
