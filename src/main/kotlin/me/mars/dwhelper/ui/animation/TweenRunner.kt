package me.mars.dwhelper.ui.animation

object TweenRunner {
    private var thread: Thread? = null

    var active: Boolean = false

    private var tweens: MutableMap<Tween<*>, Long> = mutableMapOf()

    init {
        active = true
        thread = Thread {
            var lastUpdateTime = System.currentTimeMillis()

            while (active) {
                val current = System.currentTimeMillis()
                if (current - lastUpdateTime > 4L) {
                    lastUpdateTime = System.currentTimeMillis()
                    update()
                }
            }
        }
        thread!!.start()
    }

    fun add(tween: Tween<*>) {
        tweens[tween] = System.currentTimeMillis()
    }

    fun running(tween: Tween<*>): Boolean {
        return tweens.containsKey(tween)
    }

    private fun update() {
        if (tweens.none()) return
        tweens.forEach { (tween, started) ->
            tween.update(System.currentTimeMillis() - started)
        }
        tweens.map { it.key }.filter { !it.valid }.forEach(tweens::remove)
    }
}