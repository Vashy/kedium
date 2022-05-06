package org.tgranz.kedium.fakes

import io.kotest.matchers.shouldBe
import org.tgranz.kedium.ArticlePublishedEvent
import org.tgranz.kedium.Event
import org.tgranz.kedium.Subscriber

class FakeEmailSenderPolicy : Subscriber {
    private val toList = mutableListOf<Email>()
    private val mailSent = mutableListOf<Pair<Email, ArticlePublishedEvent>>()

    override fun supports(event: Event): Boolean = event is ArticlePublishedEvent

    override fun onEvent(event: Event) {
        event as ArticlePublishedEvent
        toList.forEach { mailSent += it to event }
    }

    fun addEmailReceivers(vararg emails: Email) {
        toList += emails
    }

    fun hasBeenSent(email: Email, event: ArticlePublishedEvent) = mailSent.contains(email to event) shouldBe true
}

private typealias Email = String
