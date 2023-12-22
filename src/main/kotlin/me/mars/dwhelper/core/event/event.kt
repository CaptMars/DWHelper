package me.mars.dwhelper.core.event

typealias Observer<T> = (sender: Any, eventArgs: T) -> Boolean