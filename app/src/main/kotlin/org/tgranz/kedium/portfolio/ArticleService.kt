package org.tgranz.kedium.portfolio

import org.tgranz.kedium.customer.CustomerId
import org.tgranz.kedium.customer.CustomerNotFoundException

class ArticleService(private val portfolioRepository: PortfolioRepository) {
    fun publishArticle(customerId: CustomerId, article: Article) {
        val portfolio = portfolioRepository.findByCustomerId(customerId) ?: throw CustomerNotFoundException("1")
        portfolio.publish(article)
        portfolioRepository.save(portfolio)
    }
}
