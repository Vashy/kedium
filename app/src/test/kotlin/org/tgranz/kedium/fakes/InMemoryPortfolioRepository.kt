package org.tgranz.kedium.fakes

import org.tgranz.kedium.customer.CustomerId
import org.tgranz.kedium.event.EventPublisher
import org.tgranz.kedium.portfolio.Portfolio
import org.tgranz.kedium.portfolio.PortfolioRepository

class InMemoryPortfolioRepository(private val eventPublisher: EventPublisher) : PortfolioRepository {
    val portfolios = mutableListOf<Portfolio>()

    override fun save(portfolio: Portfolio) {
        portfolios.removeIf { it.id == portfolio.id }
        portfolios.add(portfolio)

        portfolio.emittedEvents.forEach { eventPublisher.publish(it) }
    }

    override fun findByCustomerId(customerId: CustomerId): Portfolio? {
        return portfolios.find { portfolio -> portfolio.ownerId == customerId }
    }
}
