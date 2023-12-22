package me.mars.dwhelper.ui.element.event

import me.mars.dwhelper.core.math.Vector2f

class MouseDragEventArgs(mouse: Vector2f, val delta: Vector2f) : MouseEventArgs(mouse)