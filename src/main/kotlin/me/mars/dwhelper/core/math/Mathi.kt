package me.mars.dwhelper.core.math

import kotlin.math.max
import kotlin.math.min

class Mathi {

}

inline fun Int.clamp(min: Int, max: Int) = min(max, max(min, this))