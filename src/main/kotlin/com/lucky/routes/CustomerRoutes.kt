package com.lucky.routes

import com.lucky.database.MyDatabase
import com.lucky.models.Customer
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.customerRouting() {
    route("/customers") {
        get {
            val customers = MyDatabase.getAllCustomers()
            if (customers.isNotEmpty()) {
                call.respond(customers)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            }
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id", status = HttpStatusCode.BadRequest
            )
            MyDatabase.getCustomerById(id)?.let {
                call.respond(it)
            } ?: return@get call.respondText("No customer with id $id", status = HttpStatusCode.NotFound)

        }
        post {
            val customer = call.receive<Customer>()
            if (MyDatabase.insertCustomer(customer)) {
                call.respondText("Customer stored correctly", status = HttpStatusCode.Accepted)
            }

        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (MyDatabase.deleteCustomerById(id)) {
                call.respondText(
                    "com.lucky.models.Customer removed correctly",
                    status = HttpStatusCode.Accepted
                )
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }

        }

    }
}

fun Application.registerCustomerRoutes() {
    routing {
        customerRouting()
    }
}