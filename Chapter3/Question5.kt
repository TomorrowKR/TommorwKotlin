package com.example.myapplication

open class Unit(
    private var name: String = "Unknown",
    private var level: Int = 1,
    private var health: Int = 100,
    private var power: Int = 10
) {
    fun getName(): String = name
    fun setName(newName: String) {
        name = newName
    }

    fun getLevel(): Int = level
    fun setLevel(newLevel: Int) {
        level = newLevel
    }

    fun getHealth(): Int = health
    fun setHealth(newHealth: Int) {
        health = newHealth
    }

    fun getPower(): Int = power
    fun setPower(newPower: Int) {
        power = newPower
    }

    open fun printInfo() {
        println("이름: $name")
        println("레벨: $level")
        println("체력: $health")
        println("공격력: $power")
    }

    open fun attack() {
        println("${name}이(가) 공격력 ${power}으로 공격을 시전합니다!")
    }
}

open class Character(
    name: String,
    level: Int,
    health: Int,
    private var experience: Int,
    power: Int = 10
) : Unit(name, level, health, power) {
    override fun printInfo() {
        super.printInfo()
        println("현재 경험치: $experience")
    }

    open fun levelUp() {
        setLevel(getLevel() + 1)
        experience -= 100
        setHealth(getHealth() + 10 * getLevel())
        setPower(getPower() + 5)
        println("레벨업! 현재 레벨: ${getLevel()}, 체력 증가: ${getHealth()}, 공격력 증가: ${getPower()}")
    }

    open fun getExperience(exp: Int) {
        experience += exp
        println("${exp}의 경험치를 획득하였습니다.")
        while (experience >= 100) {
            levelUp()
        }
    }
}

class Job(
    name: String,
    level: Int,
    health: Int,
    experience: Int,
    power: Int,
    private var jobTitle: String
) : Character(name, level, health, experience, power) {
    override fun printInfo() {
        super.printInfo()
        println("직업 이름: $jobTitle")
    }

    override fun attack() {
        println("${jobTitle} ${getName()}이(가) 공격력 ${getPower()}으로 공격을 시전하였습니다!")
    }

    fun useSkill() {
        println("${jobTitle}의 스킬 사용!")
    }
}

class Monster(
    name: String,
    level: Int,
    health: Int,
    private var type: String,
    power: Int = 20
) : Unit(name, level, health, power) {
    override fun printInfo() {
        super.printInfo()
        println("몬스터 타입: $type")
    }

    override fun attack() {
        println("${type} 몬스터 ${getName()}이(가) 공격력 ${getPower()}으로 공격을 시전합니다!")
    }
}

fun main() {
    val character = Character("초보자", 1, 100, 0)
    val warrior = Job("장군", 1, 150, 0, 20, "전사")
    val monster = Monster("드래곤", 10, 500, "파이어", 100)

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