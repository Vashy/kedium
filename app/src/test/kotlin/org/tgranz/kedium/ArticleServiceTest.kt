package org.tgranz.kedium

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode.InstancePerTest
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify
import org.tgranz.kedium.fakes.InMemoryPortfolioRepository
import org.tgranz.kedium.fixtures.aEmptyPortfolio
import org.tgranz.kedium.fixtures.theArticle

class ArticleServiceTest : ShouldSpec({
    isolationMode = InstancePerTest
    val eventPublisher = mockk<EventPublisher>(relaxUnitFun = true)
    val repository = InMemoryPortfolioRepository(eventPublisher)
    val service = ArticleService(repository)

    should("publish article in portfolio repository") {
        val customerId = CustomerId("10")
        val portfolio = aEmptyPortfolio(customerId)
        repository.save(portfolio)

        service.publishArticle(customerId, theArticle())

        repository.portfolios shouldContain portfolio
    }

    should("publish ArticlePublished event") {
        val customerId = CustomerId("1")
        val portfolio = aEmptyPortfolio(customerId)
        repository.save(portfolio)

        service.publishArticle(customerId, theArticle())

        verify { eventPublisher.publish(ArticlePublishedEvent(theArticle())) }
    }

    should("fail when customer not found") {
        val exception = shouldThrow<CustomerNotFoundException> { service.publishArticle(CustomerId("1"), theArticle()) }

        exception.message shouldBe "Customer not found with id: 1"
    }
})
