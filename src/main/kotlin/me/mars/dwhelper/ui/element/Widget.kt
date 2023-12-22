package me.mars.dwhelper.ui.element

import me.mars.dwhelper.ui.Edges

class Widget {
    protected var margin: Edges = Edges()
    protected var padding: Edges = Edges()

    private var x: Int
    private var y: Int
    private var height: Int
    private var width: Int

    var autoWidth: Boolean = false
    var autoHeight: Boolean = false
}