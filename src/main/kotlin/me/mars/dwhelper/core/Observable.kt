package me.mars.dwhelper.core

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

typealias Handler<T> = Observable<T>.() -> Unit

abstract class Observable<T: Observable<T>> {
    protected val handlers: MutableList<Handler<T>> = mutableListOf()

    fun changed(handler: Handler<T>) = handlers.add(handler)

    protected fun notifyChanges() = handlers.forEach { it(this) }

    protected companion object {
        fun <T: Observable<T>, V> T.observable(initial: V) = object : ReadWriteProperty<T, V> {
            private var value = initial

            override fun getValue(thisRef: T, property: KProperty<*>): V = value

            override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
                this.value = value
                notifyChanges()
            }
        }

        fun <T: Observable<T>, V: Observable<V>> T.observable(initial: V) = object : ReadOnlyProperty<T, V> {
            init {
                initial.changed { this@observable.notifyChanges() }
            }

            override fun getValue(thisRef: T, property: KProperty<*>): V = initial
        }
    }
}

fun <T: Observable<T>, V> dependent(observable: T, initializer: T.() -> V): ReadOnlyProperty<Any, V> = object : ReadOnlyProperty<Any, V> {
    private var value: V? = null
    private val initializer: T.() -> V = initializer

    override fun getValue(thisRef: Any, property: KProperty<*>): V = value ?: initialize()

    private inline fun initialize(): V {
        value = initializer(observable)
        return value!!
    }
}