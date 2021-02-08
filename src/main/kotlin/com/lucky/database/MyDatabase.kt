package com.lucky.database

import com.lucky.models.Customer
import com.mongodb.DuplicateKeyException
import com.mongodb.MongoException
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

object MyDatabase {

    private val client = KMongo.createClient().coroutine
    private val database = client.getDatabase("MyShopData")
    private val collection = database.getCollection<Customer>()

    suspend fun insertCustomer(customer: Customer): Boolean {
        return try {
            collection.insertOne(customer).wasAcknowledged()
        } catch (e: MongoException) {
            println(e.message)
            false
        } catch (e: DuplicateKeyException) {
            println("key already exists")
            false
        }
    }

    suspend fun getCustomerById(id: String): Customer? {
        return collection.findOne(Customer::_id eq id)
    }

    suspend fun getAllCustomers(): List<Customer> {
        return collection.find().toList()
    }

    suspend fun deleteCustomerById(id: String): Boolean {
        return try {
            collection.deleteOne(Customer::_id eq id).wasAcknowledged()
        } catch (e: MongoException) {
            println("Failed to delete customer with $id")
            e.printStackTrace()
            return false
        }

    }

}