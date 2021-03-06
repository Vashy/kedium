package org.tgranz.kedium

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.tgranz.kedium.event.Event
import org.tgranz.kedium.event.EventBus
import org.tgranz.kedium.event.EventPolicy

class EventBusTest : StringSpec({
    "subscriber has no events when initialized" {
        val subscriber = FakeEventPolicy1()

        subscriber.shouldBeEmpty()
    }

    "subscriber reacts to supported published event" {
        val subscriber = FakeEventPolicy1()
        val eventBus = EventBus().apply { subscribe(subscriber) }

        eventBus.publish(FakeEvent1("Some event"))

        subscriber `should have reacted to` FakeEvent1("Some event")
    }

    "subscriber doesn't react to unsupported published event" {
        val subscriber = FakeEventPolicy1()
        val eventBus = EventBus().apply { subscribe(subscriber) }

        eventBus.publish(FakeEvent2("Some event"))

        subscriber `should not have reacted to` FakeEvent2("Some event")
    }

    "multiple subscriber should react only to supported events" {
        val subscriber1 = FakeEventPolicy1()
        val subscriber2 = FakeEventPolicy2()
        val eventBus = EventBus().apply {
            subscribe(subscriber1)
            subscribe(subscriber2)
        }

        eventBus.publish(FakeEvent1("Event #1"))
        eventBus.publish(FakeEvent2("Event #2"))
        eventBus.publish(FakeEvent2("Event #3"))

        subscriber1 `should have reacted to` FakeEvent1("Event #1")
        subscriber1 `should not have reacted to` FakeEvent2("Event #2")
        subscriber1 `should not have reacted to` FakeEvent2("Event #3")
        subscriber2 `should have reacted to` FakeEvent1("Event #1")
        subscriber2 `should not have reacted to` FakeEvent2("Event #2")
        subscriber2 `should not have reacted to` FakeEvent2("Event #3")
    }

    "unsubscribed subscriber doesn't react to supported published event" {
        val subscriber = FakeEventPolicy1()
        val eventBus = EventBus().apply {
            subscribe(subscriber)
            unsubscribe(subscriber)
        }

        eventBus.publish(FakeEvent1("Some event"))

        subscriber `should not have reacted to`  FakeEvent1("Some event")
    }
})

private data class FakeEvent1(private val info: String) : Event

private class FakeEventPolicy1 : EventPolicy {
    private val publishedEvents: MutableList<FakeEvent1> = mutableListOf()

    override fun supports(event: Event): Boolean = event is FakeEvent1

    override fun onEvent(event: Event) {
        publishedEvents.add(event as FakeEvent1)
    }

    infix fun `should have reacted to`(event: Event) {
        publishedEvents shouldContain event
    }

    infix fun `should not have reacted to`(event: Event) {
        publishedEvents shouldNotContain event
    }

    fun shouldBeEmpty() {
        publishedEvents shouldBe emptyList()
    }
}

private data class FakeEvent2(val info: String) : Event

private class FakeEventPolicy2 : EventPolicy {
    private val publishedEvents: MutableList<FakeEvent2> = mutableListOf()

    override fun supports(event: Event): Boolean = event is FakeEvent2

    override fun onEvent(event: Event) {
        publishedEvents.add(event as FakeEvent2)
    }

    infix fun `should not have reacted to`(event: Event) {
        publishedEvents shouldContain event
    }

    infix fun `should have reacted to`(event: Event) {
        publishedEvents shouldNotContain event
    }
}
