package org.tgranz.kedium

import io.kotest.core.spec.IsolationMode.InstancePerTest
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.tgranz.kedium.fixtures.aEmptyPortfolio
import org.tgranz.kedium.fixtures.theArticle

class PortfolioTest : ShouldSpec({
    isolationMode = InstancePerTest

    should("there be no articles in empty portfolio") {
        val portfolio = aEmptyPortfolio()

        portfolio.articles shouldBe emptyList()
    }

    should("publish article in portfolio aggregate") {
        val customerId = CustomerId("10")
        val portfolio = aEmptyPortfolio(customerId)

        portfolio.publish(theArticle())

        portfolio.articles shouldContain theArticle()
    }

    should("publish ArticlePublished event") {
        val customerId = CustomerId("1")
        val portfolio = aEmptyPortfolio(customerId)

        portfolio.publish(theArticle())

        portfolio.emittedEvents shouldContain ArticlePublishedEvent(theArticle())
    }
})
