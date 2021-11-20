package br.com.estudo.ktor.application.cutomers.controller

import br.com.estudo.ktor.infra.CustomerRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

class CustomerController {
    private val customerRepository = CustomerRepository()

    fun customers(){
        fun Route.route(){
            route("/customers") {
                get("/{customersId}") {
                    val customerId = call.parameters["customersId"]?.toInt()
                    call.respondText(customerId.toString(),contentType = ContentType.Text.Plain)
                }
            }
        }
    }
}