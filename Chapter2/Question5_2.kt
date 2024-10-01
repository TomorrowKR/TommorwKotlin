package com.example.myapplication

import java.util.*
fun main() {
    val board = List(3) { MutableList(3) { " " } }
    var isPlayer1Turn = true
    var gameOver = false
    var winner = ""
    while (!gameOver) {
        printBoard(board)
        val currentPlayer = if (isPlayer1Turn) "플레이어 1 (X)" else "컴퓨터 (O)"
        if (isPlayer1Turn) {
            println("$currentPlayer 차례입니다. 행과 열을 입력하세요 (0-2 사이의 숫자를 공백으로 구분):")
            val input = readLine()
            val coordinates = input?.split(' ')
            if (coordinates?.size != 2) {
                println("잘못된 입력입니다. 다시 입력해주세요.")
                continue
            }
            val row = coordinates[0]?.toIntOrNull() ?: -1
            val col = coordinates[1]?.toIntOrNull() ?: -1
            if (row < 0 || row > 2 || col < 0 || col > 2) {
                println("잘못된 좌표입니다. 다시 입력해주세요.")
                continue
            }
            if (board[row][col] != " ") {
                println("이미 선택된 셀입니다. 다시 입력해주세요.")
                continue
            }
            board[row][col] = "X"
        } else {
            println("$currentPlayer 차례입니다.")
            val random = Random()
            var row: Int = 0
            var col: Int = 0
            var isValidMove = false
            while (!isValidMove) {
                row = random.nextInt(3)
                col = random.nextInt(3)
                if (board[row][col] == " ") {
                    isValidMove = true
                }
            }
            board[row][col] = "O"
        }
        if (checkGameOver(board)) {
            gameOver = true
            winner = currentPlayer
        }
        isPlayer1Turn = !isPlayer1Turn
    }
    printBoard(board)
    if (winner.isNotEmpty()) {
        println("승자: $winner")
    } else {
        println("무승부입니다!")
    }
}

// printBoard 함수와 checkGameOver 함수는 5_1과 동일합니다.