package org.tgranz.kedium.event

interface EventPublisher {
    fun publish(event: Event)
}

class EventBus: EventPublisher {
    private val eventPolicies: MutableSet<EventPolicy> = mutableSetOf()

    override fun publish(event: Event) {
        eventPolicies.forEach {
            if (it.supports(event)) {
                it.onEvent(event)
            }
        }
    }

    fun subscribe(eventPolicy: EventPolicy) {
        eventPolicies.add(eventPolicy)
    }

    fun unsubscribe(eventPolicy: EventPolicy) {
        eventPolicies.remove(eventPolicy)
    }
}
