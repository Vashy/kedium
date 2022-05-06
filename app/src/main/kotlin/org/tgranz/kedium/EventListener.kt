package org.tgranz.kedium

interface EventListener<in E> {
    fun reactTo(event: E)
}
