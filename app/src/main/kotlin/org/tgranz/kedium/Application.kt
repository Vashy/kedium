package org.tgranz.kedium

import org.tgranz.kedium.customer.CustomerId
import org.tgranz.kedium.event.EventBus
import org.tgranz.kedium.portfolio.Article
import org.tgranz.kedium.portfolio.ArticleService
import org.tgranz.kedium.portfolio.Portfolio

class Application(
    private val articleService: ArticleService
) {
    fun publishArticle(customerId: CustomerId, article: Article) {
        articleService.publishArticle(customerId, article)
    }
}
