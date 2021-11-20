package br.com.estudo.ktor

import br.com.estudo.ktor.application.cutomers.command.CustomerCreateCommandRequest
import br.com.estudo.ktor.application.cutomers.command.CustomerUpdateCommandRequest
import br.com.estudo.ktor.infra.CustomerRepository
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main(){
    val server = embeddedServer(Netty, port = 8080, host = "localhost") {
        install(ContentNegotiation){
            gson {
                setPrettyPrinting()
            }
        }
        routing {
            olaMundo()
            customers(CustomerRepository())
        }
    }

    server.start(wait = true)
}

fun Route.customers(custumerRepository: CustomerRepository){
    route("/customers") {

        get {
            call.respond(HttpStatusCode.OK, custumerRepository.getAll())
        }

        get("/{customersId}") {
            val customerId = call.parameters["customersId"]?.toInt()
            if (customerId != null){
                try {
                    val customer = custumerRepository.byId(customerId)
                    call.respond(HttpStatusCode.OK, customer)

                } catch (e: NoSuchElementException) {
                    call.respond(HttpStatusCode.NotFound, Response("Não encontramos um customer com o id $customerId"))
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, Response("Id nao pode ser nulo"))
            }
        }

        post("/create") {
            val request = call.receive<CustomerCreateCommandRequest>()
            val insert = custumerRepository.insert(request.firstName, request.lastName)

            call.respond(HttpStatusCode.OK, insert)
        }

        put("/update") {
            val request = call.receive<CustomerUpdateCommandRequest>()
            custumerRepository.update(request.id, request.firstName, request.lastName)

            call.respond(HttpStatusCode.OK, Response("Ok"))

        }

        delete("/{customerId}") {
            val idDelete = call.parameters["customerId"]?.toInt()

            if (idDelete != null){
                custumerRepository.delete(idDelete)
                call.respond(HttpStatusCode.OK, Response("Ok"))
            } else{
                call.respond(HttpStatusCode.BadRequest, Response("Id nao pode ser nulo"))
            }
        }

    }
}


private fun Route.olaMundo() {
    get("/") {
        call.respondText("Olá Mundo", contentType = ContentType.Text.Plain)
    }
}

data class Response(val message: String)
