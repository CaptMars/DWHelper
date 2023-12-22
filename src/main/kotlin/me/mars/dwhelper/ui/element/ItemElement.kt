package me.mars.dwhelper.ui.element

import me.mars.dwhelper.core.math.Vector2f
import me.mars.dwhelper.ui.context.DrawContext
import net.minecraft.item.ItemStack

class ItemElement : Element<ItemElement>() {
    var item: ItemStack = ItemStack.EMPTY

    override fun renderSelf(context: DrawContext, mouse: Vector2f, delta: Float) {
        super.renderSelf(context, mouse, delta)

        context.drawItem(item, rectangle)
    }
}