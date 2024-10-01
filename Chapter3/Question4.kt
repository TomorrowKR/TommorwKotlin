package com.example.myapplication

open class Unit(
    var name: String = "Unknown",
    var level: Int = 1,
    var health: Int = 100
) {
    open fun printInfo() {
        println("이름: $name")
        println("레벨: $level")
        println("체력: $health")
    }

    open fun attack() {
        println("${name}이(가) 공격을 시전합니다!")
    }
}

open class Character(
    name: String,
    level: Int,
    health: Int,
    var experience: Int
) : Unit(name, level, health) {
    override fun printInfo() {
        super.printInfo()
        println("현재 경험치: $experience")
    }

    open fun levelUp() {
        level++
        experience -= 100
        health += 10 * level
        println("레벨업! 현재 레벨: $level, 체력이 올랐습니다. 현재 체력: $health")
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
    name: String,
    level: Int,
    health: Int,
    experience: Int,
    var jobTitle: String
) : Character(name, level, health, experience) {
    override fun printInfo() {
        super.printInfo()
        println("직업 이름: $jobTitle")
    }

    override fun attack() {
        println("${jobTitle}이(가) 공격을 시전하였습니다!")
    }

    fun useSkill() {
        println("${jobTitle}의 스킬 사용!")
    }
}

class Monster(
    name: String,
    level: Int,
    health: Int,
    var power: Int
) : Unit(name, level, health) {
    override fun printInfo() {
        super.printInfo()
        println("공격력: $power")
    }

    override fun attack() {
        println("${name}이(가) ${power}의 힘으로 공격을 시전합니다!")
    }
}

fun main() {
    val character = Character("초보자", 1, 100, 0)
    val warrior = Job("장군", 1, 150, 0, "전사")
    val monster = Monster("드래곤", 10, 500, 100)

    character.printInfo()
    character.attack()
    character.getExperience(50)
    character.printInfo()

    println("-----------------------")

    warrior.printInfo()
    warrior.attack()
    warrior.getExperience(80)
    warrior.printInfo()
    warrior.useSkill()

    println("-----------------------")

    monster.printInfo()
    monster.attack()
}

