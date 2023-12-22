package me.mars.dwhelper.core.math

import me.mars.dwhelper.core.Observable
import me.mars.dwhelper.core.dependent
import me.mars.dwhelper.core.math.Mathf.max
import me.mars.dwhelper.core.math.Mathf.min

class Rectangle(val position: Vector2f, val size: Dimensions) {
    val center: Vector2f get() = Vector2f(position.x + size.width / 2.0f, position.y + size.height / 2.0f)

    val left: Float get() = position.x
    val right: Float get() = position.x + size.width
    val up: Float get() = position.y
    val down: Float get() = position.y + size.height

    val limit: Vector2f get() = Vector2f(right, down)

    fun contains(point: Vector2f): Boolean = point.x > position.x && point.y > position.y && point.x <= limit.x && point.y <= limit.y

    fun intersection(other: Rectangle): Rectangle? {
        val position = Vector2f(max(left, other.left), max(up, other.up))
        val size = Dimensions(min(right, other.right), min(down, other.down)) - position
        return if (size.width > 0 && size.height > 0) Rectangle(position, size) else null
    }

    companion object {
        val empty: Rectangle get() = Rectangle(Vector2f.empty, Dimensions.empty)
    }
}