package me.mars.dwhelper.core.event

class EventHandler<T : EventArgs> {
    private val subscribers: MutableSet<Observer<T>> = mutableSetOf()

    operator fun plusAssign(subscriber: Observer<T>) {
        synchronized(subscribers) {
            subscribers.add(subscriber)
        }
    }

    operator fun minusAssign(subscriber: Observer<T>) {
        synchronized(subscribers) {
            subscribers.remove(subscriber)
        }
    }

    operator fun invoke(publisher: Any, args: T): Boolean {
        var handled = false
        subscribers.forEach { if (it.invoke(publisher, args)) handled = true }
        return handled
    }
}