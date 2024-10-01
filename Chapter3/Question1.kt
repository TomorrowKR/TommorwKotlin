package com.example.myapplication

class Character(var level: Int = 1, var experience: Int = 0) {
    fun levelUp() {
        level++
        experience -= 100
        println("Level Up! 현재 레벨: $level")
    }

    fun getExperience(exp: Int) {
        experience += exp
        println("${exp} 의 경험치를 획득하였습니다.")
        while (experience >= 100) {
            levelUp()
        }
    }
}

fun main() {
    val character = Character()
    character.getExperience(50)
    character.getExperience(30)
    character.getExperience(40)
}