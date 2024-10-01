package com.example.myapplication

fun main() {
    val A: Int = 10
    val B: Int = 20
    val C: Int = 30

    // 1번
    if (A < B && A < C) {
        println("A가 가장 작다.")
    } else {
        println("A가 가장 작지 않다.")
    }
    // 2번
    if (A < B) {
        if (A < C) {
            println("A가 가장 작다.")
        }
    } else {
        println("A가 B보다 작지 않다.")
    }

    // 3번
    if (A >= B) {
        println("A가 B보다 크거나 같다.")
    } else if (A >= C) {
        println("A가 C보다 크거나 같다.")
    } else {
        println("A가 가장 작다.")
    }
}