package me.mars.dwhelper.util.minecraft

import me.mars.dwhelper.core.math.Vector2f
import me.mars.dwhelper.ui.element.Element
import me.mars.dwhelper.ui.element.FrameElement
import me.mars.dwhelper.ui.element.ItemElement
import me.mars.dwhelper.ui.element.TextElement

inline fun <T: Element<T>> T.mouseMoved(crossinline handler: T.(mouse: Vector2f) -> Boolean) {
    mouseMove += { sender, args -> handler(sender as T, args.mouse) }
}

inline fun <T: Element<T>> T.mouseIn(crossinline handler: T.(mouse: Vector2f) -> Boolean) {
    mouseIn += { sender, args -> handler(sender as T, args.mouse) }
}

inline fun <T: Element<T>> T.mouseOut(crossinline handler: T.(mouse: Vector2f) -> Boolean) {
    mouseOut += { sender, args -> handler(sender as T, args.mouse) }
}

inline fun <T: Element<T>> T.mouseScrolled(crossinline handler: T.(mouse: Vector2f, delta: Float) -> Boolean) {
    mouseScroll += { sender, args -> handler(sender as T, args.mouse, args.delta) }
}

inline fun <T: Element<T>> T.mouseDragged(crossinline handler: T.(mouse: Vector2f, delta: Vector2f) -> Boolean) {
    mouseDrag += { sender, args -> handler(sender as T, args.mouse, args.delta) }
}

inline fun <T: Element<T>> T.mouseClicked(crossinline handler: T.(mouse: Vector2f, button: Int) -> Boolean) {
    mouseClick += { sender, args -> handler(sender as T, args.mouse, args.button) }
}

inline fun <T: Element<T>> T.mouseLeftClicked(crossinline handler: T.(mouse: Vector2f) -> Boolean) {
    mouseClicked { mouse, button -> if (button == 0) handler(mouse) else false }
}

inline fun <T: Element<T>> T.mouseRightClicked(crossinline handler: T.(mouse: Vector2f) -> Boolean) {
    mouseClicked { mouse, button -> if (button == 1) handler(mouse) else false }
}

inline fun <T: Element<T>> T.mouseReleased(crossinline handler: T.(mouse: Vector2f, button: Int) -> Boolean) {
    mouseRelease += { sender, args -> handler(sender as T, args.mouse, args.button) }
}

inline fun <T: Element<T>> T.keyPressed(crossinline handler: T.(key: Int, scan: Int, modifiers: Int) -> Boolean) {
    keyPress += { sender, args -> handler(sender as T, args.key, args.scan, args.modifiers)}
}

inline fun <T: Element<T>> T.keyReleased(crossinline handler: T.(key: Int, scan: Int, modifiers: Int) -> Boolean) {
    keyRelease += { sender, args -> handler(sender as T, args.key, args.scan, args.modifiers) }
}

inline fun frame(block: FrameElement.() -> Unit): FrameElement = FrameElement().apply(block)

inline fun text(block: TextElement.() -> Unit): TextElement = TextElement().apply(block)

inline fun item(block: ItemElement.() -> Unit): ItemElement = ItemElement().apply(block)