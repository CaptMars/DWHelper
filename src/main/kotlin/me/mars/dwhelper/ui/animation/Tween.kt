package me.mars.dwhelper.ui.animation

import me.mars.dwhelper.core.math.Mathf
import me.mars.dwhelper.core.math.Vector2f
import kotlin.reflect.KMutableProperty0

class Tween<T>(private val property: KMutableProperty0<T>, private val result: T, private val duration: Long, private val easing: Easing) {
    private val initial: T = property.get()

    var valid: Boolean = true
        private set

    fun start() {
        TweenRunner.add(this)
    }

    fun update(passed: Long) {
        if (!valid) return
        if (passed > duration) {
            property.set(result)
            valid = false
            return
        }
        lerp(passed.toFloat() / duration)
    }

    @Suppress("unchecked_cast")
    private fun lerp(progress: Float) {
        if (initial is Float && result is Float) {
            property.set(Mathf.lerp(initial, result, easing.interpolate(progress)) as T)
        }
        else if (initial is Vector2f && result is Vector2f) {
            with(property.get() as Vector2f) {
                x = initial.x + (result.x - initial.x) * progress
                y = initial.y + (result.y - initial.y) * progress
            }
        }
    }
}