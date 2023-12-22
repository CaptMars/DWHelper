package me.mars.dwhelper.data

import me.mars.dwhelper.core.math.clamp
import java.util.regex.Pattern

class Color4f(r: Int, g: Int, b: Int, a: Int) {
    val int: Int

    val r: Int
    val g: Int
    val b: Int
    val a: Int

    init {
        this.r = r.clamp(0, 255)
        this.g = g.clamp(0, 255)
        this.b = b.clamp(0, 255)
        this.a = a.clamp(0, 255)
        this.int = (this.a shl 24) or (this.r shl 16) or (this.g shl 8) or this.b
    }

    fun alpha(value: Int) = Color4f(this.r, this.g, this.b, value)

    override fun equals(other: Any?): Boolean = other != null && other is Color4f && other.int == this.int

    override fun hashCode(): Int = this.int

    companion object {
        val HEX_8: Pattern = Pattern.compile("(?:0x|#)([a-fA-F0-9]{8})")
        val HEX_6: Pattern = Pattern.compile("(?:0x|#)([a-fA-F0-9]{6})")
        val HEX_4: Pattern = Pattern.compile("(?:0x|#)([a-fA-F0-9]{4})")
        val HEX_3: Pattern = Pattern.compile("(?:0x|#)([a-fA-F0-9]{3})")

        fun fromColor(color: Int) = fromColor(color, color shr 24 and 0xFF)

        fun fromColor(color: Int, alpha: Int) =
            Color4f(color shr 16 and 0xFF, color shr 8 and 0xFF, color and 0xFF, alpha)
    }
}