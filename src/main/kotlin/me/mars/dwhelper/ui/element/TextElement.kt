package me.mars.dwhelper.ui.element

import me.mars.dwhelper.core.math.Vector2f
import me.mars.dwhelper.ui.context.DrawContext
import me.mars.dwhelper.util.minecraft.sizeOf
import me.mars.dwhelper.util.minecraft.textRenderer

class TextElement : Element<TextElement>() {
    var text: String = "TextElement"

    override fun renderSelf(context: DrawContext, mouse: Vector2f, delta: Float) {
        super.renderSelf(context, mouse, delta)

        context.drawText(text, rectangle, style.text.color)
    }

    override fun mouseMoved(mouse: Vector2f): Boolean {
        return super.mouseMoved(mouse)
    }

    override fun mouseIn(mouse: Vector2f): Boolean {
        return super.mouseIn(mouse)
    }
}