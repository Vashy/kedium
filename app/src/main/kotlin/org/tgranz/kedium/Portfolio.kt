package org.tgranz.kedium

class Portfolio(
    val id: PortfolioId,
    val ownerId: CustomerId,
    articles: List<Article>,
) {
    private val mutableEvents: MutableList<Event> = mutableListOf()
    private val mutableArticles: MutableList<Article> = articles.toMutableList()

    val emittedEvents: List<Event> = mutableEvents
    val articles: List<Article> = mutableArticles

    fun publish(article: Article) {
        mutableArticles.add(article)

        emit(ArticlePublishedEvent(article))
    }

    private fun emit(event: Event) {
        mutableEvents.add(event)
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
