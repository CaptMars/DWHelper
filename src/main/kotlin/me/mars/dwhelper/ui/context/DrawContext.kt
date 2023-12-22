package me.mars.dwhelper.ui.context

import com.mojang.blaze3d.systems.RenderSystem
import me.mars.dwhelper.core.math.Dimensions
import me.mars.dwhelper.core.math.Rectangle
import me.mars.dwhelper.core.math.Vector2f
import me.mars.dwhelper.util.minecraft.itemRenderer
import me.mars.dwhelper.util.minecraft.textRenderer
import me.mars.dwhelper.util.minecraft.window
import me.mars.ui.Color
import net.fabricmc.loader.impl.lib.sat4j.core.Vec
import net.minecraft.client.render.*
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import org.joml.Matrix4f
import kotlin.math.max

class DrawContext(private var matrices: MatrixStack) {
    private val scissorStack: ScissorStack = ScissorStack()

    private val matrix4f: Matrix4f get() = matrices.peek().positionMatrix

    private val offset: Vector2f get() = Vector2f(matrix4f.m30(), matrix4f.m31())

    fun push() = matrices.push()

    fun pop() = matrices.pop()

    fun translate(translation: Vector2f) {
        matrix4f.translate(translation.x, translation.y, 0.0f)
    }

    fun scale(scale: Vector2f) {
        matrices.scale(scale.x, scale.y, 1.0f)
    }

    fun drawText(text: String, rectangle: Rectangle, color: Color) {
        push()
        val scale = rectangle.size.height / textRenderer.fontHeight
        translate(rectangle.position)
        scale(Vector2f(scale, scale))
        enableScissors(Rectangle(offset, rectangle.size))
        textRenderer.drawWithShadow(matrices, text, 0f, 0f, color.color)
        disableScissors()
        pop()
    }

    fun drawBorder(rectangle: Rectangle, thickness: Float, color: Color, inner: Boolean = true) {
        if (inner)
            drawInnerBorder(rectangle, thickness, color)
        else
            drawOuterBorder(rectangle, thickness, color)
    }

    fun drawInnerBorder(rectangle: Rectangle, thickness: Float, color: Color) {
        val x1 by rectangle.position::x
        val y1 by rectangle.position::y
        val x2 by rectangle.limit::x
        val y2 by rectangle.limit::y
        drawRectangle(x1, y1, x2, y1 + thickness, color)
        drawRectangle(x1, y2 - thickness, x2, y2, color)
        drawRectangle(x1, y1, x1 + thickness, y2, color)
        drawRectangle(x2 - thickness, y1, x2, y2, color)
    }

    fun drawOuterBorder(rectangle: Rectangle, thickness: Float, color: Color) {
        val x1 by rectangle.position::x
        val y1 by rectangle.position::y
        val x2 by rectangle.limit::x
        val y2 by rectangle.limit::y
        drawRectangle(x1 - thickness, y1 - thickness, x2 + thickness, y1, color)
        drawRectangle(x1 - thickness, y2, x2 + thickness, y2 + thickness, color)
        drawRectangle(x1 - thickness, y1 - thickness, x1, y2 + thickness, color)
        drawRectangle(x2, y1 - thickness, x2 + thickness, y2 + thickness, color)
    }

    fun drawRectangle(rectangle: Rectangle, color: Color) {
        drawRectangle(rectangle.position.x, rectangle.position.y, rectangle.limit.x, rectangle.limit.y, color)
    }

    fun drawItem(stack: ItemStack, rectangle: Rectangle) {
        push()
        translate(rectangle.position)
        scale(Vector2f(rectangle.size.width / 16.0f, rectangle.size.height / 16.0f))
        itemRenderer.renderGuiItemIcon(matrices, stack, 0, 0)
        pop()
    }

    fun enableScissors(rectangle: Rectangle) = setScissors(scissorStack.push(rectangle))

    fun disableScissors() = setScissors(scissorStack.pop())

    private fun drawRectangle(x1: Float, y1: Float, x2: Float, y2: Float, color: Color) {
        RenderSystem.enableBlend()
        RenderSystem.setShader(GameRenderer::getPositionColorProgram)

        Tessellator.getInstance().buffer.apply {
            begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR)

            vertex(matrix4f, x1, y1, 0.0f).color(color).next()
            vertex(matrix4f, x1, y2, 0.0f).color(color).next()
            vertex(matrix4f, x2, y2, 0.0f).color(color).next()
            vertex(matrix4f, x2, y1, 0.0f).color(color).next()

            BufferRenderer.drawWithGlobalProgram(end())
        }

        RenderSystem.disableBlend()
    }

    private fun setScissors(rectangle: Rectangle?) {
        if (rectangle == null) {
            RenderSystem.disableScissor()
            return
        }
        val x = rectangle.position.x * window.scaleFactor
        val y = window.framebufferHeight - rectangle.limit.y * window.scaleFactor
        val w = rectangle.size.width * window.scaleFactor
        val h = rectangle.size.height * window.scaleFactor
        RenderSystem.enableScissor(x.toInt(), y.toInt(), max(0.0, w).toInt(), max(0.0, h).toInt())
    }

    private fun VertexConsumer.color(color: Color) = color(color.red, color.green, color.blue, color.alpha)

    private class ScissorStack {
        private val stack: ArrayDeque<Rectangle> = ArrayDeque()

        fun push(rectangle: Rectangle): Rectangle {
            if (stack.any()) {
                with(stack.last().intersection(rectangle) ?: Rectangle.empty) {
                    stack.addLast(this)
                    return this
                }
            }
            stack.addLast(rectangle)
            return rectangle
        }

        fun pop(): Rectangle? {
            if (stack.any()) {
                stack.removeLast()
                return if (stack.any()) stack.last() else null
            }
            throw IllegalStateException("Scissor stack underflow")
        }
    }
}