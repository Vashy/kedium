package org.tgranz.kedium


interface EventPublisher {
    fun publish(event: Event)
}

interface Subscriber {
    fun supports(event: Event): Boolean
    fun onEvent(event: Event)
}

class EventBus: EventPublisher {
    private val subscribers: MutableSet<Subscriber> = mutableSetOf()

    override fun publish(event: Event) {
        subscribers.forEach {
            if (it.supports(event)) {
                it.onEvent(event)
            }
        }
    }

    fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: Subscriber) {
        subscribers.remove(subscriber)
    }
}
