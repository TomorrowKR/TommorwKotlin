package com.example.myapplication

fun main() {
    // 1번
    for (i in 1..5) {
        for (j in 1..i) {
            print('*')
        }
        println()
    }

    // 2번
    for (i in 1..5) {
        for (j in 5 downTo i + 1) {
            print(' ')
        }
        for (k in 1..i) {
            print('*')
        }
        println(' ')
    }

    // 3번
    for (i in 5 downTo 1) {
        for (j in 1..i) {
            print('*')
        }
        println(' ')
    }

    // 4번
    for (i in 5 downTo 1) {
        for (j in 5 downTo i + 1) {
            print(' ')
        }
        for (k in 1..i) {
            print('*')
        }
        println(' ')
    }

    // 5번
    for (i in 1..9) {
        if (i <= 5) {
            for (j in 5 downTo i + 1) {
                print(' ')
            }
            for (k in 1..(2 * i)) {
                print('*')
            }
            println()
        } else {
            for (j in 1..(i - 5)) {
                print(' ')
            }
            for (k in 1..(20 - 2 * i)) {
                print('*')
            }
            println()
        }
    }
}