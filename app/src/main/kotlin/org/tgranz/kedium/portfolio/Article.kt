package org.tgranz.kedium.portfolio

class Article(val id: ArticleId, title: ArticleTitle, content: ArticleContent) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

data class ArticleId(val id: String)
data class ArticleTitle(private val title: String)
data class ArticleContent(private val content: String)
