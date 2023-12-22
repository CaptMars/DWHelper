package me.mars.dwhelper.ui.screen

import me.mars.dwhelper.core.math.Vector2f
import me.mars.dwhelper.ui.context.DrawContext
import me.mars.dwhelper.ui.element.Element
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

typealias MinecraftScreen = net.minecraft.client.gui.screen.Screen

class Screen {
    private val children: MutableList<Element<*>> = mutableListOf()

    var title: Text = Text.literal("Screen")

    fun add(element: Element<*>) = children.add(element)

    fun handle(): MinecraftScreen = object : MinecraftScreen(title) {
        override fun shouldPause(): Boolean {
            return false
        }

        override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
            val context = DrawContext(matrices)
            val mouse = Vector2f(mouseX.toFloat(), mouseY.toFloat())

            children.forEach {
                it.render(context, mouse, delta)
            }
        }

        override fun mouseMoved(mouseX: Double, mouseY: Double) {
            val mouse = Vector2f(mouseX.toFloat(), mouseY.toFloat())
            children.forEach {
                it.mouseMoved(mouse)
            }
        }

        override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
            val mouse = Vector2f(mouseX.toFloat(), mouseY.toFloat())
            var handled = false
            children.forEach {
                if (it.mouseClicked(mouse, button))
                    handled = true
            }
            return handled
        }

        override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
            val mouse = Vector2f(mouseX.toFloat(), mouseY.toFloat())
            var handled = false
            children.forEach {
                if (it.mouseReleased(mouse, button))
                    handled = true
            }
            return handled
        }

        override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double): Boolean {
            return false
        }

        override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
            val mouse = Vector2f(mouseX.toFloat(), mouseY.toFloat())
            var handled = false
            children.forEach {
                if (it.mouseScrolled(mouse, amount.toFloat()))
                    handled = true
            }
            return handled
        }

        override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
            var handled = true
            children.forEach {
                if (it.keyPressed(keyCode, scanCode, modifiers))
                    handled = true
            }
            if (keyCode == 256) {
                this.close()
            }
            return handled
        }

        override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
            var handled = true
            children.forEach {
                if (it.keyReleased(keyCode, scanCode, modifiers))
                    handled = true
            }
            return handled
        }
    }

    operator fun <T: Element<T>> T.unaryPlus() {
        children.add(this)
    }
}