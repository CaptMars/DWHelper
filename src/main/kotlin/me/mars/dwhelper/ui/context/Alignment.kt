package me.mars.dwhelper.ui.context

import me.mars.dwhelper.core.math.Vector2f

object Alignment {
    val UPPER_LEFT: Vector2f get() = Vector2f(0.0f, 0.0f)
    val UPPER_CENTER: Vector2f get() = Vector2f(-0.5f, 0.0f)
    val UPPER_RIGHT: Vector2f get() = Vector2f(-1.0f, 0.0f)

    val MIDDLE_LEFT: Vector2f get() = Vector2f(0.0f, -0.5f)
    val MIDDLE_CENTER: Vector2f get() = Vector2f(-0.5f, -0.5f)
    val MIDDLE_RIGHT: Vector2f get() = Vector2f(-1.0f, -0.5f)

    val BOTTOM_LEFT: Vector2f get() = Vector2f(0.0f, -1.0f)
    val BOTTOM_CENTER: Vector2f get() = Vector2f(-0.5f, -1.0f)
    val BOTTOM_RIGHT: Vector2f get() = Vector2f(-1.0f, -1.0f)
}