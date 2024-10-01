package com.example.myapplication

fun main() {
    val sports = "soccer"
    when (sports) {
        "soccer" -> println("축구입니다.")
        "basketball" -> println("농구입니다.")
        "baseball" -> println("야구입니다.")
        "tennis" -> println("테니스입니다.")
        else -> println("기타 스포츠입니다.")
    }
}