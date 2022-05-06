package org.tgranz.kedium.event

interface EventPolicy {
    fun supports(event: Event): Boolean
    fun onEvent(event: Event)
}
