package me.mars.dwhelper.core.math

import me.mars.dwhelper.core.Observable
import me.mars.dwhelper.core.dependent
import me.mars.dwhelper.core.math.Mathf.sqrt
import net.fabricmc.loader.impl.lib.sat4j.core.Vec

class Vector2f(x: Float, y: Float) : Observable<Vector2f>() {
    @set:Synchronized
    var x: Float by observable(x)
    @set:Synchronized
    var y: Float by observable(y)

    val magnitude: Float by dependent(this) { sqrt(x * x + y * y) }

    operator fun times(multiplier: Float): Vector2f = Vector2f(x * multiplier, y * multiplier)

    operator fun timesAssign(other: Vector2f) {
        x *= other.x
        y *= other.y
    }

    operator fun plusAssign(other: Vector2f) {
        x += other.x
        y += other.y
    }

    operator fun times(other: Vector2f): Vector2f = Vector2f(x * other.x, y * other.y)

    operator fun plus(other: Vector2f): Vector2f = Vector2f(x + other.x, y + other.y)

    operator fun minus(other: Vector2f): Vector2f = Vector2f(x - other.x, y - other.y)

    operator fun unaryMinus(): Vector2f = Vector2f(-x, -y)

    companion object {
        val empty: Vector2f get() = Vector2f(0.0f, 0.0f)
    }
}