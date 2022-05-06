package org.tgranz.kedium

interface PortfolioRepository {
    fun save(portfolio: Portfolio)
    fun findByCustomerId(customerId: CustomerId): Portfolio?
}
