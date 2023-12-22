package me.mars.dwhelper.ui.element.event

import me.mars.dwhelper.core.math.Vector2f

class MouseScrollEventArgs(mouse: Vector2f, val delta: Float) : MouseEventArgs(mouse)