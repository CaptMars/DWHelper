package me.mars.ui

class Color(red: Int, green: Int, blue: Int, alpha: Int) {
    var color: Int = 0xFFFFFFFF.toInt()
        private set

    var alpha: Int
        get() = color shr 24 and 255
        set(value) {
            color = color and 0x00FFFFFF or (value shl 24)
        }

    var red: Int
        get() = color shr 16 and 255
        set(value) {
            color = color and 0xFF00FFFF.toInt() or (value shl 16)
        }

    var green: Int
        get() = color shr 8 and 255
        set(value) {
            color = color and 0xFFFF00FF.toInt() or (value shl 8)
        }

    var blue: Int
        get() = color and 255
        set(value) {
            color = color and 0xFFFFFF00.toInt() or value
        }

    init {
        color = alpha shl 24 or (red shl 16) or (green shl 8) or blue
    }

    companion object {
        val TRANSPARENT get() = Color(0, 0, 0, 0)
        val BLACK get() = Color(0, 0, 0, 255)
        val WHITE get() = Color(255, 255, 255, 255)
        val RED get() = Color(255, 0, 0, 255)
        val GREEN get() = Color(0, 255, 0, 255)
        val BLUE get() = Color(0, 0, 255, 255)
        val YELLOW get() = Color(255, 255, 0, 255)
        val CYAN get() = Color(0, 255, 255, 255)
        val MAGENTA get() = Color(255, 0, 255, 255)
        val GRAY get() = Color(128, 128, 128, 255)
        val LIGHT_GRAY get() = Color(192, 192, 192, 255)
        val DARK_GRAY get() = Color(64, 64, 64, 255)
        val ORANGE get() = Color(255, 165, 0, 255)
        val PURPLE get() = Color(128, 0, 128, 255)
        val BROWN get() = Color(165, 42, 42, 255)
    }
}