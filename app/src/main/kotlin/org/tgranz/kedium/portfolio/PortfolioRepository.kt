package org.tgranz.kedium.portfolio

import org.tgranz.kedium.customer.CustomerId

interface PortfolioRepository {
    fun save(portfolio: Portfolio)
    fun findByCustomerId(customerId: CustomerId): Portfolio?
}
