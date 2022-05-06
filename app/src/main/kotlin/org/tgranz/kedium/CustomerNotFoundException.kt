package org.tgranz.kedium

class CustomerNotFoundException(id: String) : RuntimeException("Customer not found with id: $id")
