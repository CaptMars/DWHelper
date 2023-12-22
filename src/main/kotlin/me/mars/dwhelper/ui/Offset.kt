package me.mars.dwhelper.ui

class Offset {
    private var _centerHorizontally: Boolean = false
    private var _centerVertically: Boolean = false

    private var _x: Int = 0
    private var _y: Int = 0

    @set:Synchronized
    var centerHorizontally: Boolean
        get() = _centerHorizontally
        set(value) {
            if (_centerHorizontally == value) return
            _centerHorizontally = value
        }

    @set:Synchronized
    var centerVertically: Boolean
        get() = _centerVertically
        set(value) {
            if (_centerVertically == value) return
            _centerVertically = value
        }

    @set:Synchronized
    var x: Int
        get() = _x
        set(value) {
            if (_x == value) return
            _x = value
        }

    @set:Synchronized
    var y: Int
        get() = _y
        set(value) {
            if (_y == value) return
            _y = value
        }
}