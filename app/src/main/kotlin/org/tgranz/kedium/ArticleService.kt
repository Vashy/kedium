package org.tgranz.kedium

class ArticleService(private val portfolioRepository: PortfolioRepository) {
    fun publishArticle(customerId: CustomerId, article: Article) {
        val portfolio = portfolioRepository.findByCustomerId(customerId) ?: throw CustomerNotFoundException("1")
        portfolio.publish(article)
        portfolioRepository.save(portfolio)
    }
}
