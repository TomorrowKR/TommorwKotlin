package com.example.myapplication

open class Unit(
    private var _name: String = "Unknown",
    private var _level: Int = 1,
    private var _health: Int = 100,
    private var _power: Int = 10
) {
    val name: String
        get() = _name
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
    val experience: Int
        get() = _experience

    override fun printInfo() {
        super.printInfo()
        println("현재 경험치: $experience")
    }

    open fun levelUp() {
        super.level++
        super.health += 10
        println(
            "레벨업! 현재 레벨: ${super.level}, 최대 체력이 증가하였습니다. 현재 체력: ${super.health}"
        )
    }

    open fun getExperience(exp: Int) {
        _experience += exp
        println("${exp}의 경험치를 획득하였습니다.")
        if (_experience >= 100) {
            levelUp()
            _experience -= 100 // 경험치	초기화
        }
    }
}

class Job(
    name: String,
    level: Int,
    health: Int,
    experience: Int,
    private var _jobTitle: String
) : Character(name, level, health, experience) {
    val jobTitle: String
        get() = _jobTitle

    override fun printInfo() {
        super.printInfo()
        println("직업: $jobTitle")
    }

    fun useSkill() {
        println("${jobTitle}의 스킬을 사용합니다!")
    }
}

class Monster(
    name: String,
    level: Int,
    health: Int,
    power: Int,
    private var _type: String
) : Unit(name, level, health, power) {
    val type: String
        get() = _type

    override fun printInfo() {
        super.printInfo()
        println("몬스터 타입: $type")
    }
}


fun main() {
    val character = Character("초보자", 1, 100, 0)
    val warrior = Job("장군", 1, 150, 0, "전사")
    val monster = Monster("드래곤", 10, 500, 100, "Fire")
    character.printInfo()
    character.attack(monster)
    character.getExperience(50)
    character.printInfo()
    println("-----------------------")
    warrior.printInfo()
    warrior.attack(monster)
    warrior.getExperience(80)
    warrior.printInfo()
    warrior.useSkill()
    println("-----------------------")
    monster.printInfo()
    monster.attack(character)
}
