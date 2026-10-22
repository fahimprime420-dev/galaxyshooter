package com.example.galaxyshooter.entities

import kotlin.random.Random

class Player {
    var x: Float = 400f
    var y: Float = 1200f
    var hp: Int = 3
    var cooldown: Int = 0
    var power: Int = 0
    var powerTicks: Int = 0
}

class Enemy(var x: Float, var y: Float, var vy: Float) {
    var r: Float = 28f
    var hp: Int = 1
}

class Bullet(var x: Float, var y: Float, var vx: Float, var vy: Float, val fromEnemy: Boolean)

class Particle(x: Float, y: Float) {
    var x: Float = x
    var y: Float = y
    var vx: Float = (Random.nextFloat() - 0.5f) * 8f
    var vy: Float = (Random.nextFloat() - 0.5f) * 8f
    var life: Int = 30
}

data class Star(val x: Float, val y: Float, val speed: Float, val size: Float)
