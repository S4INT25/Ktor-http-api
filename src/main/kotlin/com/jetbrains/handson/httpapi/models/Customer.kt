package com.jetbrains.handson.httpapi.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.newId

@Serializable
data class Customer(
    @Contextual val _id: String = newId<Customer>().toString(),
    val firstName: String,
    val lastName: String,
    val email: String
)
