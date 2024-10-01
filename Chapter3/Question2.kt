package com.example.myapplication

class Monster(val name: String, var health: Int) {
    fun printInfo() {
        println("이름: $name, 체력: $health")
    }

    fun attack() {
        println("${name}이(가) 공격을 시전합니다!")
    }
}

fun main() {
    val monster1 = Monster("슬라임", 50)
    val monster2 = Monster("고블린", 80)
    monster1.printInfo()
    monster1.attack()
    monster2.printInfo()
    monster2.attack()
}