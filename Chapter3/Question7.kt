package com.example.myapplication

abstract class Unit(
    private var _name: String = "Unknown",
    private var _level: Int = 1,
    private var _health: Int = 100,
    private var _power: Int = 10
) {
    var name: String
        get() = _name
        set(value) {
            _name = value
        }
    var level: Int
        get() = _level
        set(value) {
            _level = value
        }
    var health: Int
        get() = _health
        set(value) {
            _health = value
        }
    val power: Int
        get() = _power

    open fun printInfo() {
        println("이름: $name")
        println("레벨: $level")
        println("체력: $health")
        println("공격력: $power")
    }

    open fun attack(target: Unit) {
        println("${name}이(가) ${target.name}을(를) 공격합니다!")
        target.takeDamage(power)
    }

    open fun takeDamage(damage: Int) {
        _health -= damage
        if (_health <= 0) {
            _health = 0
            onDeath()
        }
    }

    open fun onDeath() {
        println("${name}이(가) 사망했습니다!")
    }
}

open class Character(
    name: String,
    level: Int,
    health: Int,
    private var _experience: Int
) : Unit(name, level, health) {
    var experience: Int
        get() = _experience
        set(value) {
            _experience = value
        }

    override fun printInfo() {
        super.printInfo()
        println("현재 경험치: $experience")
    }

    open fun levelUp() {
        level++
        health += 10
        println("레벨업! 현재 레벨: $level, 최대 체력이 증가하였습니다. 현재 체력: $health")
    }

    open fun getExperience(exp: Int) {
        _experience += exp
        println("${exp}의 경험치를 획득하였습니다.")
        if (_experience >= 100) {
            levelUp()
            _experience -= 100 // 경험치	초기화
        }
    }

    override fun attack(target: Unit) {
        println("${name}이(가) ${target.name}에게 ${power}의 피해를 입힙니다.")
        target.takeDamage(power)
        if (target.health <= 0) {
            println("${target.name}을(를) 처치하여 경험치를 획득합니다!")
            getExperience(100)
        }
    }
}

abstract class Job(
    name: String,
    level: Int,
    health: Int,
    experience: Int,
    private var _jobTitle: String
) : Character(name, level, health, experience) {
    var jobTitle: String
        get() = _jobTitle
        set(value) {
            _jobTitle = value
        }

    abstract fun useSkill(target: Unit)
    abstract fun calculateDamage(target: Unit): Int
}

class Warrior(
    name: String,
    level: Int,
    health: Int,
    experience: Int,
    jobTitle: String
) : Job(name, level, health, experience, jobTitle) {
    private val skillDamage = 20
    override fun printInfo() {
        super.printInfo()
        println("직업: $jobTitle")
    }

    override fun useSkill(target: Unit) {
        val damage = calculateDamage(target)
        if (target is Monster) {
            println("${jobTitle}의 스킬 사용! ${target.name}에게 ${damage}의 데미지를 입힙니다.")
            target.takeDamage(damage)
        }
        if (target.health <= 0) {
            println("${target.name}을(를) 처치하여 경험치를 획득합니다!")
            getExperience(100)
        }
    }

    override fun calculateDamage(target: Unit): Int {
        return if (target is Monster) {
            if (target.type == "Fire") {
                skillDamage - 20
            } else if (target.type == "Water") {
                skillDamage + 20
            } else {
                skillDamage
            }
        } else {
            skillDamage
        }
    }
}

class Monster(
    name: String,
    level: Int,
    health: Int,
    power: Int,
    private var _type: String
) : Unit(name, level, health, power) {
    var type: String
        get() = _type
        set(value) {
            _type = value
        }

    override fun printInfo() {
        super.printInfo()
        println("몬스터 타입: $type")
    }
}


fun main() {
    val character = Character("초보자", 1, 100, 0)
    val warrior = Warrior("장군", 1, 150, 0, "전사")
    val slime = Monster("슬라임", 1, 50, 20, "Water")
    val dragon = Monster("드래곤", 10, 500, 100, "Fire")
    character.printInfo()
    character.attack(slime)
    character.printInfo()
    println("-----------------------")
    warrior.printInfo()
    warrior.attack(slime)
    warrior.useSkill(slime)
    warrior.printInfo()
    println("-----------------------")
    dragon.printInfo()
    dragon.attack(character)
    dragon.attack(warrior)
    println("-----------------------")
    warrior.printInfo()
    warrior.attack(dragon)
    warrior.useSkill(dragon)
    warrior.printInfo()
}
