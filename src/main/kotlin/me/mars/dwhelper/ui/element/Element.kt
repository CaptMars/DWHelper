package me.mars.dwhelper.ui.element

import me.mars.dwhelper.core.event.EventHandler
import me.mars.dwhelper.core.math.Dimensions
import me.mars.dwhelper.core.math.Rectangle
import me.mars.dwhelper.core.math.Vector2f
import me.mars.dwhelper.ui.context.Alignment
import me.mars.dwhelper.ui.context.DrawContext
import me.mars.dwhelper.ui.element.event.*
import me.mars.dwhelper.ui.style.BorderType
import me.mars.dwhelper.ui.style.Style
import me.mars.ui.Color

typealias MouseListener<T> = T.(mouse: Vector2f) -> Boolean
typealias MouseClickListener<T> = T.(mouse: Vector2f, button: Int) -> Boolean
typealias MouseDragListener<T> = T.(mouse: Vector2f, delta: Vector2f) -> Boolean

abstract class Element<T : Element<T>> {
    private var children: MutableList<Element<*>> = mutableListOf()

    private var lastMousePosition: Vector2f = Vector2f.empty

    var parent: Element<*>? = null
        private set

    val rectangle: Rectangle = Rectangle(Vector2f(0.0f, 0.0f), Dimensions(0.0f, 0.0f))

    val position: Vector2f by rectangle::position
    val size: Dimensions by rectangle::size

    var x: Float by position::x
    var y: Float by position::y
    var width: Float by size::width
    var height: Float by size::height

    var alignment: Vector2f = Alignment.UPPER_LEFT

    val style: Style = Style()

    var hovered: Boolean = false
        private set
    var dragging: Boolean = false
        private set

    val mouseMove: EventHandler<MouseEventArgs> = EventHandler()
    val mouseIn: EventHandler<MouseEventArgs> = EventHandler()
    val mouseOut: EventHandler<MouseEventArgs> = EventHandler()
    val mouseScroll: EventHandler<MouseScrollEventArgs> = EventHandler()
    val mouseDrag: EventHandler<MouseDragEventArgs> = EventHandler()
    val mouseClick: EventHandler<MouseButtonEventArgs> = EventHandler()
    val mouseRelease: EventHandler<MouseButtonEventArgs> = EventHandler()
    val keyPress: EventHandler<KeyEventArgs> = EventHandler()
    val keyRelease: EventHandler<KeyEventArgs> = EventHandler()

    open fun render(context: DrawContext, mouse: Vector2f, delta: Float) {
        context.push()
        context.translate(size.toVector2f() * alignment)
        renderSelf(context, mouse, delta)
        context.translate(position)
        renderChildren(context, mouse, delta)
        context.pop()
    }

    protected open fun renderSelf(context: DrawContext, mouse: Vector2f, delta: Float) {
        if (style.background.enabled) context.drawRectangle(rectangle, style.background.color)

        if (style.border.enabled) context.drawBorder(
            rectangle, style.border.thickness, style.border.color, style.border.type === BorderType.INNER
        )
    }

    protected open fun renderChildren(context: DrawContext, mouse: Vector2f, delta: Float) {
        children.forEach {
            it.render(context, mouse, delta)
        }
    }

    open fun mouseMoved(mouse: Vector2f): Boolean {
        for (child in children) if (child.mouseMoved(mouse - position - child.size.toVector2f() * child.alignment)) return true

        mouseMove(this, MouseEventArgs(mouse))

        if (dragging) {
            mouseDrag(this, MouseDragEventArgs(mouse, mouse - lastMousePosition))

            lastMousePosition = mouse
        }

        if (hovered) {
            if (rectangle.contains(mouse)) return false
            hovered = false
            return mouseOut(mouse)
        } else {
            if (rectangle.contains(mouse)) {
                hovered = true
                return mouseIn(mouse)
            }
        }
        return false
    }

    open fun mouseIn(mouse: Vector2f): Boolean {
        return false
    }

    open fun mouseOut(mouse: Vector2f): Boolean {
        return false
    }

    open fun mouseClicked(mouse: Vector2f, button: Int): Boolean {
        if (!hovered) return false

        for (child in children) if (child.mouseClicked(mouse - position, button)) return true

        if (!dragging && button == 0) {
            lastMousePosition = mouse
            dragging = true
        }

        return mouseClick(this, MouseButtonEventArgs(mouse, button))
    }

    open fun mouseReleased(mouse: Vector2f, button: Int): Boolean {
        if (dragging && button == 0) dragging = false

        for (child in children) if (child.mouseReleased(mouse - position, button)) return true

        mouseRelease(this, MouseButtonEventArgs(mouse, button))

        return false
    }

    open fun mouseScrolled(mouse: Vector2f, delta: Float): Boolean {
        for (child in children) if (child.mouseScrolled(mouse - position, delta)) return true

        return false
    }

    open fun keyPressed(key: Int, scan: Int, modifiers: Int): Boolean {
        for (child in children) if (child.keyPressed(key, scan, modifiers)) return true

        return false
    }

    open fun keyReleased(key: Int, scan: Int, modifiers: Int): Boolean {
        for (child in children) if (child.keyReleased(key, scan, modifiers)) return true

        return false
    }

    fun <T : Element<T>> add(child: T) {
        children.add(child)
        child.parent = this
    }

    operator fun <T : Element<T>> T.unaryPlus() = this@Element.add(this)
}
