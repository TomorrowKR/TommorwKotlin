package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private var isNewInput = true // 입력이 처음인지 여부를 추적
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultTextView = findViewById(R.id.textViewResult)
        // 숫자 버튼 클릭 리스너 설정
        val numberButtons = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        )
        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                appendNumber(
                    (it as Button).text.toString()
                )
            }
        }
        // 연산자 버튼 클릭 리스너 설정
        findViewById<Button>(R.id.btn_plus).setOnClickListener {
            appendOperator("+")
        }
        findViewById<Button>(R.id.btn_minus).setOnClickListener {
            appendOperator("-")
        }
        findViewById<Button>(R.id.btn_multiply).setOnClickListener {
            appendOperator("*")
        }
        findViewById<Button>(R.id.btn_divide).setOnClickListener {
            appendOperator("/")
        }
        // 계산 버튼 클릭 리스너 설정
        findViewById<Button>(R.id.btn_calculate).setOnClickListener {
            calculateResult()
        }
        // 삭제 버튼 클릭 리스너 설정
        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            onDeleteClick()
        }
    }

    // 입력 필드에 숫자 추가
    private fun appendNumber(number: String) {
        if (isNewInput) {
            // 새로 입력할 때 이전 값을 무시하고 현재 숫자로 시작
            resultTextView.text = number
            isNewInput = false
        } else {
            val currentText = resultTextView.text.toString()
            resultTextView.text = currentText + number
        }
    }

    // 입력 필드에 연산자 추가
    private fun appendOperator(operator: String) {
        val currentText = resultTextView.text.toString()
        if (currentText.isNotEmpty() && currentText.last() !in listOf(
                '+', '-', '*', '/'
            )
        ) {
            resultTextView.text = currentText + operator
            isNewInput = false
        }
    }

    // 삭제 버튼 기능
    private fun onDeleteClick() {
        val currentText = resultTextView.text.toString()
        if (currentText.isNotEmpty()) {
            resultTextView.text = currentText.dropLast(1)
        }
    }

    // 계산 결과 표시
    private fun calculateResult() {
        val expression = resultTextView.text.toString()
        try {
            val result = eval(expression)
            resultTextView.text = result.toString()
            isNewInput = true // 계산 후 새로운 입력으로 전환
        } catch (e: Exception) {
            resultTextView.text = "Error"
            isNewInput = true
        }
    }

    // 계산 수행
    private fun eval(expression: String): Double {
        return expression.split("+", "-", "*", "/")
            .map { it.toDouble() }
            .reduce { acc, value ->
                when {
                    expression.indexOf('+') != -1 -> acc + value
                    expression.indexOf('-') != -1 -> acc - value
                    expression.indexOf('*') != -1 -> acc * value
                    expression.indexOf('/') != -1 -> acc / value
                    else -> throw IllegalArgumentException(
                        "Invalid expression "
                    )
                }
            }
    }
}
