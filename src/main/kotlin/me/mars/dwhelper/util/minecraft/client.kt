package me.mars.dwhelper.util.minecraft

import me.mars.dwhelper.core.math.Dimensions
import me.mars.dwhelper.core.math.Vector2f
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.Window

val client: MinecraftClient get() = MinecraftClient.getInstance()

val textRenderer: TextRenderer get() = client.textRenderer

val itemRenderer: ItemRenderer get() = client.itemRenderer

val window: Window get() = client.window

fun TextRenderer.sizeOf(text: String): Dimensions = Dimensions(getWidth(text).toFloat(), fontHeight.toFloat())