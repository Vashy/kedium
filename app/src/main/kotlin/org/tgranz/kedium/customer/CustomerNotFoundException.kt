package org.tgranz.kedium.customer

class CustomerNotFoundException(id: String) : KediumException("Customer not found with id: $id")
