package me.mars.dwhelper.core.math

import me.mars.dwhelper.core.Observable
import me.mars.dwhelper.core.dependent

class Dimensions(width: Float, height: Float) : Observable<Dimensions>() {
    @set:Synchronized
    var width: Float by observable(width)
    @set:Synchronized
    var height: Float by observable(height)

    val area: Float by dependent(this) { width * height }

    fun toVector2f(): Vector2f = Vector2f(width, height)

    operator fun minus(offset: Vector2f): Dimensions = Dimensions(this.width - offset.x, this.height - offset.y)

    companion object {
        val empty: Dimensions get() = Dimensions(0.0f, 0.0f)
    }
}