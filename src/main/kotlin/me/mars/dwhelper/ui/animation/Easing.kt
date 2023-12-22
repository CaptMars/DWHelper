package me.mars.dwhelper.ui.animation

import me.mars.dwhelper.core.math.Mathf.cos
import me.mars.dwhelper.core.math.Mathf.pow
import me.mars.dwhelper.core.math.Mathf.sin
import me.mars.dwhelper.core.math.Mathf.sqrt
import me.mars.dwhelper.core.math.PI
import me.mars.dwhelper.core.math.sin

open class Easing(val interpolate: (Float) -> Float) {
    fun apply(a: Float) = interpolate(a)
}

object Linear : Easing({ a -> a })

object EaseInQuad : Easing({ a -> a * a })
object EaseInCubic : Easing({ a -> a * a * a })
object EaseInQuart : Easing({ a -> a * a * a * a })
object EaseInQuint : Easing({ a -> a * a * a * a * a })
object EaseInSine : Easing({ a -> 1.0f - cos(a * PI / 2.0f) })
object EaseInExpo : Easing({ a -> if (a == 0.0f) 0.0f else pow(2.0f, 10.0f * (a - 1.0f)) })
object EaseInCirc : Easing({ a -> 1.0f - sqrt(1.0f - pow(a, 2.0f)) })

object EaseOutQuad : Easing({ a -> 1.0f - (1.0f - a) * (1.0f - a) })
object EaseOutCubic : Easing({ a -> 1.0f - pow(1.0f - a, 3.0f) })
object EaseOutQuart : Easing({ a -> 1.0f - pow(1.0f - a, 4.0f) })
object EaseOutQuint : Easing({ a -> 1.0f - pow(1.0f - a, 5.0f) })
object EaseOutSine : Easing({ a -> sin(a * PI / 2.0f) })
object EaseOutExpo : Easing({ a -> if (a == 1.0f) 1.0f else 1.0f - pow(2.0f, -10.0f * a) })
object EaseOutCirc : Easing({ a -> sqrt(1.0f - pow(a - 1.0f, 2.0f)) })

object EaseInOutQuad : Easing({ a ->
    if (a < 0.5f) 2.0f * a * a
    else 1.0f - pow(-2.0f * a + 2.0f, 2.0f) / 2.0f
})
object EaseInOutCubic : Easing({ a ->
    if (a < 0.5f) 4.0f * a * a * a
    else 1.0f - pow(-2.0f * a + 2.0f, 3.0f) / 2.0f
})
object EaseInOutQuart : Easing({ a ->
    if (a < 0.5f) 8.0f * a * a * a * a
    else 1.0f - pow(-2.0f * a + 2.0f, 4.0f) / 2.0f
})
object EaseInOutQuint : Easing({ a ->
    if (a < 0.5f) 16.0f * a * a * a * a * a
    else 1.0f - pow(-2.0f * a + 2.0f, 5.0f) / 2.0f
})
object EaseInOutSine : Easing({ a -> -(cos(PI * a) - 1.0f) / 2.0f })
object EaseInOutExpo : Easing({ a ->
    if (a == 0.0f) 0.0f
    else if (a == 1.0f) 1.0f
    else if (a < 0.5f) pow(2.0f, 20.0f * a - 10.0f) / 2.0f
    else -pow(2.0f, -20.0f * a + 10.0f) / 2.0f + 1.0f
})