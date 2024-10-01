package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.NumberPicker
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private const val SET_ROULETTE_REQUEST = 1
        const val EXTRA_LOTTO_GAME_LEVEL = "com.example.myapplication.LOTTO_GAME_LEVEL"
        const val EXTRA_LADDER_PARTICIPANTS = "com.example.myapplication.LADDER_PARTICIPANTS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonLadderGame: Button = findViewById(R.id.buttonLadderGame)
        val buttonMontyHallGame: Button = findViewById(R.id.buttonMontyHallGame)
        val buttonRouletteGame: Button = findViewById(R.id.buttonRouletteGame)
        val buttonLottoGame: Button = findViewById(R.id.buttonLottoGame)
        val buttonExit: Button = findViewById(R.id.buttonExit)

        buttonLottoGame.setOnClickListener {
            showLottoGameDialog()
        }
        buttonMontyHallGame.setOnClickListener {
            showMontyHallGameDialog()
        }
        buttonRouletteGame.setOnClickListener {
            showRouletteGameDialog()
        }
        buttonLadderGame.setOnClickListener {
            showLadderGameDialog()
        }
        buttonExit.setOnClickListener {
            finish()
        }
    }

    private fun showLottoGameDialog() {
        val items = arrayOf("1등", "2등", "3등", "4등", "5등")
        AlertDialog.Builder(this)
            .setTitle("등수 선택")
            .setItems(items) { dialog, which ->
                val selected = items[which]
                val intent = Intent(this, LottoGameActivity::class.java)
                intent.putExtra(EXTRA_LOTTO_GAME_LEVEL, selected)
                startActivity(intent)
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun showMontyHallGameDialog() {
        val items = arrayOf("3개의 문", "4개의 문", "5개의	문")
        AlertDialog.Builder(this)
            .setTitle("문의 수 선택")
            .setItems(items) { dialog, which ->
                val selected = items[which].replace("개의 문", "").toInt()
                val intent = Intent(this, MontyHallGameActivity::class.java)
                intent.putExtra("numberOfDoors", selected)
                startActivity(intent)
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun showRouletteGameDialog() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.seekbar_dialog, null)
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val seekBarValueTextView = view.findViewById<TextView>(R.id.seekBarValueTextView)
        seekBar.progress = 0
        seekBarValueTextView.text = "4"
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?, progress: Int,
                fromUser: Boolean
            ) {
                val value = progress + 4
                seekBarValueTextView.text = value.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        AlertDialog.Builder(this)
            .setTitle("돌림판 칸 수 선택")
            .setView(view)
            .setPositiveButton("확인") { dialog, _ ->
                val selectedNumber = seekBar.progress + 4
                val intent = Intent(this, SetRouletteActivity::class.java)
                intent.putExtra("numberOfSlots", selectedNumber)
                startActivityForResult(intent, SET_ROULETTE_REQUEST)
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun showLadderGameDialog() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.number_picker_dialog, null)
        val numberPicker = view.findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.minValue = 4
        numberPicker.maxValue = 10
        numberPicker.wrapSelectorWheel = true
        AlertDialog.Builder(this)
            .setTitle("사다리타기 인원 수 선택")
            .setView(view)
            .setPositiveButton("확인") { dialog, _ ->
                val selectedNumber = numberPicker.value
                val intent = Intent(this, LadderInputActivity::class.java)
                intent.putExtra(EXTRA_LADDER_PARTICIPANTS, selectedNumber)
                startActivity(intent)
            }
            .setNegativeButton("취소", null)
            .show()
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data:
        Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SET_ROULETTE_REQUEST && resultCode == RESULT_OK) {
            val sections = data?.getStringArrayListExtra("sections")
            val intent = Intent(this, RouletteActivity::class.java)
            intent.putStringArrayListExtra("sections", sections)
            startActivity(intent)
        }
    }
}


