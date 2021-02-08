package com.lucky

import com.lucky.routes.registerCustomerRoutes
import com.lucky.routes.registerOrderRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }



    registerCustomerRoutes()
    registerOrderRoutes()
}
