package org.tgranz.kedium

import io.kotest.core.spec.IsolationMode.InstancePerTest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe

class EventBusTest : StringSpec({
    isolationMode = InstancePerTest

    "subscriber has no events when initialized" {
        val subscriber = FakeSubscriber1()

        subscriber.shouldBeEmpty()
    }

    "subscriber reacts to supported published event" {
        val subscriber = FakeSubscriber1()
        val eventBus = EventBus().apply { subscribe(subscriber) }

        eventBus.publish(FakeEvent1("Some event"))

        subscriber shouldHaveReactedTo FakeEvent1("Some event")
    }

    "subscriber doesn't react to unsupported published event" {
        val subscriber = FakeSubscriber1()
        val eventBus = EventBus().apply { subscribe(subscriber) }

        eventBus.publish(FakeEvent2("Some event"))

        subscriber shouldNotHaveReactedTo FakeEvent2("Some event")
    }

    "multiple subscriber should react only to supported events" {
        val subscriber1 = FakeSubscriber1()
        val subscriber2 = FakeSubscriber2()
        val eventBus = EventBus().apply {
            subscribe(subscriber1)
            subscribe(subscriber2)
        }

        eventBus.publish(FakeEvent1("Event #1"))
        eventBus.publish(FakeEvent2("Event #2"))
        eventBus.publish(FakeEvent2("Event #3"))

        subscriber1 shouldHaveReactedTo FakeEvent1("Event #1")
        subscriber1 shouldNotHaveReactedTo FakeEvent2("Event #2")
        subscriber1 shouldNotHaveReactedTo FakeEvent2("Event #3")
        subscriber2 shouldNotHaveReactedTo FakeEvent1("Event #1")
        subscriber2 shouldHaveReactedTo FakeEvent2("Event #2")
        subscriber2 shouldHaveReactedTo FakeEvent2("Event #3")
    }
})

private data class FakeEvent1(private val info: String) : Event

private class FakeSubscriber1 : Subscriber {
    private val publishedEvents: MutableList<FakeEvent1> = mutableListOf()

    override fun supports(event: Event): Boolean = event is FakeEvent1

    override fun onEvent(event: Event) {
        publishedEvents.add(event as FakeEvent1)
    }

    infix fun shouldHaveReactedTo(event: Event) {
        publishedEvents shouldContain event
    }

    infix fun shouldNotHaveReactedTo(event: Event) {
        publishedEvents shouldNotContain event
    }

    fun shouldBeEmpty() {
        publishedEvents shouldBe emptyList()
    }
}

private data class FakeEvent2(val info: String) : Event

private class FakeSubscriber2 : Subscriber {
    private val publishedEvents: MutableList<FakeEvent2> = mutableListOf()

    override fun supports(event: Event): Boolean = event is FakeEvent2

    override fun onEvent(event: Event) {
        publishedEvents.add(event as FakeEvent2)
    }

    infix fun shouldHaveReactedTo(event: Event) {
        publishedEvents shouldContain event
    }

    infix fun shouldNotHaveReactedTo(event: Event) {
        publishedEvents shouldNotContain event
    }
}
