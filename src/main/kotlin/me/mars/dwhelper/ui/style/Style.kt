package me.mars.dwhelper.ui.style

import me.mars.ui.Color

class Style {
    var background: BackgroundStyle = BackgroundStyle()
    var border: BorderStyle = BorderStyle()
    val text: TextStyle = TextStyle()
}

class BackgroundStyle {
    var enabled: Boolean = false
    var color: Color = Color.TRANSPARENT
}

class BorderStyle {
    var enabled: Boolean = false;
    var color: Color = Color.BLACK
    var thickness: Float = 1.0f
    var type: BorderType = BorderType.INNER
}

enum class BorderType {
    INNER, OUTER
}

class TextStyle {
    var color: Color = Color.WHITE

    var shadow: Boolean = true
}