package org.tgranz.kedium.fakes

import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import org.tgranz.kedium.portfolio.ArticlePublishedEvent
import org.tgranz.kedium.event.Event
import org.tgranz.kedium.event.EventPolicy

class FakeEmailSenderPolicy : EventPolicy {
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

    fun `has been sent`(email: Email, event: ArticlePublishedEvent) {
        withClue("Email with event: $event should be published to email: $email, but was not.\n" +
                "Configured emails: [${toList.joinToString("; ")}]") {
            mailSent.contains(email to event) shouldBe true
        }
    }
}

private typealias Email = String
