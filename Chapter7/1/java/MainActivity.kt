package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textViewResult: TextView
    private var currentInput = ""
    private var lastOperator = ""
    private var lastCharacter = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewResult = findViewById(R.id.textViewResult)
        val numberButtons = listOf(
            findViewById<Button>(R.id.button0),
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7),
            findViewById<Button>(R.id.button8),
            findViewById<Button>(R.id.button9)
        )
        val operatorButtons = listOf(
            findViewById<Button>(R.id.buttonAdd),
            findViewById<Button>(R.id.buttonSubtract),
            findViewById<Button>(R.id.buttonMultiply),
            findViewById<Button>(R.id.buttonDivide)
        )
        for (button in numberButtons) {
            button.setOnClickListener { onNumberClick(it as Button) }
        }
        for (button in operatorButtons) {
            button.setOnClickListener { onOperatorClick(it as Button) }
        }
        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            onDeleteClick()
        }
        findViewById<Button>(R.id.buttonEqual).setOnClickListener {
            onEqualClick()
        }
    }

    private fun onNumberClick(button: Button) {
        currentInput += button.text
        textViewResult.text = currentInput
        lastCharacter = button.text.toString()
    }

    private fun onOperatorClick(button: Button) {
        if (currentInput.isEmpty() || isOperator(lastCharacter)) {
            Toast.makeText(this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        currentInput += button.text
        textViewResult.text = currentInput
        lastOperator = button.text.toString()
        lastCharacter = button.text.toString()
    }

    private fun onDeleteClick() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            textViewResult.text = currentInput
            lastCharacter = if (currentInput.isNotEmpty())
                currentInput.last().toString() else ""
        }
    }

    private fun onEqualClick() {
        if (currentInput.isEmpty() || isOperator(lastCharacter)) {
            Toast.makeText(this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val result = evaluateExpression(currentInput)
            textViewResult.text = result.toString()
            currentInput = result.toString()
        } catch (e: Exception) {
            Toast.makeText(this, "계산 오류: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isOperator(character: String): Boolean {
        return character == "+" || character == "-" || character == "*" || character == "/"
    }

    private fun evaluateExpression(expression: String): Double {
        val tokens = expression.split("(?<=[-+*/])|(?=[-+*/])".toRegex())
        val values = mutableListOf<Double>()
        val operators = mutableListOf<String>()
        for (token in tokens) {
            when {
                token.isEmpty() -> continue
                token.matches("-?\\d+(\\.\\d+)?".toRegex()) -> values.add(
                    token.toDouble()
                )

                isOperator(token) -> operators.add(token)
            }
        }
        while (operators.isNotEmpty()) {
            val index = findHighestPrecedenceOperator(operators)
            val value1 = values[index]
            val value2 = values[index + 1]
            val operator = operators[index]
            if (operator == "/" && value2 == 0.0) {
                throw ArithmeticException("0으로 나눌 수 없습니다.")
            }
            val result = when (operator) {
                "+" -> value1 + value2
                "-" -> value1 - value2
                "*" -> value1 * value2
                "/" -> value1 / value2
                else -> throw IllegalArgumentException("알 수 없는 연산자: $operator")
            }
            values[index] = result
            values.removeAt(index + 1)
            operators.removeAt(index)
        }
        return values[0]
    }

    private fun findHighestPrecedenceOperator(operators: List<String>): Int {
        var highestPrecedenceIndex = 0
        var highestPrecedence = getPrecedence(operators[0])
        for (i in 1 until operators.size) {
            val precedence = getPrecedence(operators[i])
            if (precedence > highestPrecedence) {
                highestPrecedence = precedence
                highestPrecedenceIndex = i
            }
        }
        return highestPrecedenceIndex
    }

    private fun getPrecedence(operator: String): Int {
        return when (operator) {
            "*", "/" -> 2
            "+", "-" -> 1
            else -> 0
        }
    }
}


