package org.tgranz.kedium.fixtures

import org.tgranz.kedium.*

fun aEmptyPortfolio(customerId: CustomerId = CustomerId("2")) = Portfolio(PortfolioId("1"), customerId, listOf())

fun theArticle() = Article(ArticleId("3"), ArticleTitle("title"), ArticleContent("content"))
