package com.example.myapplication

fun findValue(list: List<Int>, target: Int): Int {
    for (i in list.indices) {
        if (list[i] == target) {
            return i // 일치하는 값의 인덱스 반환
        }
    }
    return -1 // 일치하는 값이 없을 경우 -1 반환
}

fun main() {
    val numbers = listOf(10, 20, 30, 40, 50)
    val target = 30
    var found = false
    var index = 0
    for ((i, value) in numbers.withIndex()) {
        if (value == target) {
            found = true
            index = i
            break
        }
    }
    if (found) {
        println("원하는 값 $target 은 리스트에 있습니다. 인덱스: $index")
    } else {
        println("원하는 값 $target 은 리스트에 없습니다.")
    }

    val numbers2 = listOf(5, 10, 15, 20, 25, 30)
    val target2 = 15
    val index2 = findValue(numbers2, target2)
    if (index2 != -1) {
        println("원하는 값 $target2 은 리스트의 인덱스 $index2 에 위치해 있습니다.")
    } else {
        println("원하는 값 $target2 을 찾을 수 없습니다.")
    }
}