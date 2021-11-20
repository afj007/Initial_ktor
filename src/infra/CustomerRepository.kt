package br.com.estudo.ktor.infra

import br.com.estudo.ktor.domain.customers.Customer

class CustomerRepository {
    private val customerList = mutableListOf<Customer>()

    fun getAll(): List<Customer> {
        return customerList
    }

    fun byId(id: Int): Customer {
        return customerList.first { it.id == id }
    }

    fun insert(firstName: String, lastName: String): Customer {
        val newID = if (customerList.isEmpty()){
            1
        } else {
            customerList.sortByDescending { it.id }
            val lastInsert = customerList.first()
            lastInsert.id + 1
        }

        val insert = Customer(newID, firstName, lastName)
        customerList.add(insert)

        return insert
    }

    fun update(id: Int, firstName: String?, lastName: String?) {
        customerList.forEach { customer ->
            if (customer.id == id) {
                if (firstName != null) {
                    customer.firstName = firstName
                }

                if (lastName != null) {
                    customer.lastName = lastName
                }
            }
        }
    }

    fun delete(id: Int) {
        customerList.removeIf { it.id == id }
    }
}