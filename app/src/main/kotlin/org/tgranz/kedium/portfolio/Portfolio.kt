package org.tgranz.kedium.portfolio

import org.tgranz.kedium.customer.CustomerId
import org.tgranz.kedium.event.Event

class Portfolio(
    val id: PortfolioId,
    val ownerId: CustomerId,
    articles: List<Article>,
) {
    private val _emittedEvents: MutableList<Event> = mutableListOf()
    private val _articles: MutableList<Article> = articles.toMutableList()

    val emittedEvents: List<Event> = _emittedEvents
    val articles: List<Article> = _articles

    fun publish(article: Article) {
        _articles += article

        emit(ArticlePublishedEvent(article))
    }

    private fun emit(event: Event) {
        _emittedEvents += event
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Portfolio

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

data class PortfolioId(private val id: String)
