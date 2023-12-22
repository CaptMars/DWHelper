package me.mars.dwhelper

import me.mars.dwhelper.core.math.Vector2f
import me.mars.dwhelper.ui.animation.*
import me.mars.dwhelper.ui.context.Alignment
import me.mars.dwhelper.ui.element.*
import me.mars.dwhelper.ui.screen.Screen
import me.mars.dwhelper.ui.style.BorderType
import me.mars.dwhelper.util.minecraft.*
import me.mars.ui.Color
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.util.math.random.Random
import org.lwjgl.glfw.GLFW
import org.slf4j.LoggerFactory
import java.util.Timer

object DWHelper : ModInitializer {
    private val logger = LoggerFactory.getLogger("dwhelper")

    override fun onInitialize() {
        val binding = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "dwhelper.key.example", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "dwhelper.category.example"
            )
        )

        ClientTickEvents.END_CLIENT_TICK.register {
            if (binding.wasPressed()) {
                val screen = Screen().apply {
                    val data = mutableListOf<ItemStack>()

                    repeat(Random.create().nextBetween(1, 10)) {
                        data.add(ItemStack(Registries.ITEM.getRandom(Random.create()).get().value()))
                    }

                    +frame {
                        x = 10.0f
                        y = 10.0f

                        width = 24.0f
                        height = 4.0f + 20.0f * data.size

                        style.background.enabled = true
                        style.background.color = Color(0, 0, 0, 127)

                        val tween = Tween(::width, 100.0f, 1000, EaseInCirc)

                        mouseClicked { mouse, button ->
                            if (!TweenRunner.running(tween) && tween.valid) {
                                tween.start()
                            }
                            true
                        }

                        val entries = mutableMapOf<Int, ItemElement>()

                        for (i in data.indices) {
                            entries[i] = item {
                                width = 16.0f
                                height = 16.0f

                                x = 4.0f
                                y = 4.0f + i * 20.0f

                                item = data[i]

                                mouseClicked { _, _ ->
                                    Tween(::x, x - 32.0f, 333, EaseOutCubic).start()
                                    Tween(this@frame::height, this@frame.height - 20.0f, 333, EaseOutCubic).start()

                                    for (j in (i + 1)..<data.size) {
                                        Tween(entries[j]!!::y, entries[j]!!.y - 20.0f, 333, EaseOutCubic).start()
                                    }

                                    true
                                }
                            }
                            +entries[i]!!
                        }
                    }
                }

                it.setScreen(screen.handle())
            }
        }
    }
}