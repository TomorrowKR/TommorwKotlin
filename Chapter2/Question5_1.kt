package com.example.myapplication

fun main() {
    val board = List(3) { MutableList(3) { " " } }
    var isPlayer1Turn = true
    var gameOver = false
    var winner = ""
    while (!gameOver) {
        printBoard(board)
        val currentPlayer = if (isPlayer1Turn) "플레이어 1 (X)" else "플레이어 2 (O)"
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
        board[row][col] = if (isPlayer1Turn) "X" else "O"
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

fun printBoard(board: List<List<String>>) {
    println("-------------")
    for (i in 0 until 3) {
        println("| ${board[i][0]} | ${board[i][1]} | ${board[i][2]} |")
        println("-------------")
    }
}

fun checkGameOver(board: List<List<String>>): Boolean {
    // 가로	체크
    for (row in 0 until 3) {
        if (board[row][0] != " " && board[row][0] == board[row][1] && board[row][0] == board[row][2]) {
            return true
        }
    }
    // 세로	체크
    for (col in 0 until 3) {
        if (board[0][col] != " " && board[0][col] == board[1][col] && board[0][col] == board[2][col]) {
            return true
        }
    }
    // 대각선	체크
    if (board[0][0] != " " && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
        return true
    }
    if (board[0][2] != " " && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
        return true
    }
    // 무승부	체크
    for (row in 0 until 3) {
        for (col in 0 until 3) {
            if (board[row][col] == " ") {
                return false // 아직	빈	칸이	남아있음
            }
        }
    }
    return true // 모든	칸이	채워져서	무승부
}

