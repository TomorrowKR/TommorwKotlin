package com.example.myapplication

open class Character(
    var level: Int = 1,
    var experience: Int = 0,
    var health: Int = 100
) {
    open fun printInfo() {
        println("현재 레벨: $level")
        println("현재 경험치: $experience")
        println("체력: $health")
    }

    open fun levelUp() {
        level++
        experience -= 100
        health += 10 * level
        println("레벨업! 현재 레벨: $level, 체력이 올랐습니다. 현재 체력: $health")
    }

    open fun attack() {
        println("캐릭터가 공격하였습니다!")
    }

    open fun getExperience(exp: Int) {
        experience += exp
        println("${exp}의 경험치를 획득하였습니다.")
        if (experience >= 100) {
            levelUp()
        }
    }
}

class Job(
    level: Int,
    experience: Int,
    health: Int,
    private val jobTitle: String
) : Character(level, experience, health) {
    fun printJob() {
        println("직업 이름: $jobTitle")
    }

    fun useSkill() {
        println("${jobTitle}의 스킬 사용!")
    }
}

fun main() {
    val character = Character()
    character.printInfo()
    character.attack()
    character.getExperience(50)
    character.getExperience(60)

    val warrior = Job(2, 10, 120, "전사")
    warrior.printInfo()
    warrior.printJob()
    warrior.attack()
    warrior.useSkill()
    warrior.getExperience(80)
}
