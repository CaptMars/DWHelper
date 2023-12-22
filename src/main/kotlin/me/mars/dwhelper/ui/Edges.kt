package me.mars.dwhelper.ui

import me.mars.dwhelper.core.event.EventArgs
import me.mars.dwhelper.core.event.EventHandler

class Edges(left: Int, right: Int, top: Int, bottom: Int) {
    private var _dLeft: Int = left
    private var _dRight: Int = right
    private var _dTop: Int = top
    private var _dBottom: Int = bottom

    private var _left: Int = left
    private var _right: Int = right
    private var _top: Int = top
    private var _bottom: Int = bottom

    val defaultLeft: Int get() = _dLeft

    val defaultRight: Int get() = _dRight

    val defaultTop: Int get() = _dTop

    val defaultBottom: Int get() = _dBottom

    @set:Synchronized
    var left: Int
        get() = _left
        set(value) {
            if (_left == value) return
            _left = value
            invokeChanged()
        }

    @set:Synchronized
    var right: Int
        get() = _right
        set(value) {
            if (_right == value) return
            _right = value
            invokeChanged()
        }

    @set:Synchronized
    var top: Int
        get() = _top
        set(value) {
            if (_top == value) return
            _top = value
            invokeChanged()
        }

    @set:Synchronized
    var bottom: Int
        get() = _bottom
        set(value) {
            if (_bottom == value) return
            _bottom = value
            invokeChanged()
        }

    @set:Synchronized
    var vertical: Int
        get() = if (_top == _bottom) _top else Int.MIN_VALUE
        set(value) {
            if (_top == value && _bottom == value) return
            _top = value
            _bottom = value
            invokeChanged()
        }

    val verticalTotal: Int get() = _top + _bottom

    @set:Synchronized
    var horizontal: Int
        get() = if (_left == _right) _left else Int.MIN_VALUE
        set(value) {
            if (_left == value && _right == value) return
            _left = value
            _right = value
            invokeChanged()
        }

    val horizontalTotal: Int get() = _left + _right

    @set:Synchronized
    var all: Int
        get() = if (_left == _right && _left == _top && _left == _bottom) _left else Int.MIN_VALUE
        set(value) {
            if (_left == value && _right == value && _top == value && _bottom == value) return
            _left = value
            _right = value
            _top = value
            _bottom = value
            invokeChanged()
        }

    val changed = EventHandler<EdgesEventArgs>()

    constructor(value: Int) : this(value, value, value, value)

    constructor() : this(0)

    private fun invokeChanged() = this.changed(this, EdgesEventArgs(this))

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Edges) return false
        if (_left != other._left) return false
        if (_right != other._right) return false
        if (_top != other._top) return false
        return _bottom == other._bottom
    }

    override fun hashCode(): Int {
        var result = _left
        result = 31 * result + _right
        result = 31 * result + _top
        return 31 * result + _bottom
    }
}

data class EdgesEventArgs(val edges: Edges) : EventArgs

inline fun Edges.changed(crossinline handler: Edges.() -> Boolean) = changed.plusAssign { _, args -> handler(args.edges) }