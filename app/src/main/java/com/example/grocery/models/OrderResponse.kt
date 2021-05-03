package com.example.grocery.models

import java.io.Serializable

data class OrderResponse(
    val count: Int,
    val data: ArrayList<OrderData>,
    val error: Boolean
)

data class OrderData(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderStatus: String,
    val orderSummary: OrderSummary,
    val payment: Payment,
    val products: List<ProductOrder>,
    val shippingAddress: ShippingAddress,
    val user: User,
    val userId: String
) : Serializable {
    companion object {
        val KEY_ORDER = "order_data"
    }
}

data class OrderSummary(
    val _id: String,
    val deliveryCharges: Double,
    val discount: Double,
    val orderAmount: Double,
    val ourPrice: Double,
    val totalAmount: Double
) : Serializable

data class Payment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
) : Serializable

data class ProductOrder(
    val _id: String? = null,
    val image: String,
    val mrp: Double,
    val price: Double,
    val productName: String,
    val quantity: Int
) : Serializable

data class ShippingAddress(
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String
) : Serializable

data class User(
    val _id: String,
    val email: String,
    val mobile: String,
    val name: String
) : Serializable