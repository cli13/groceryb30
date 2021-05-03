package com.example.grocery.models


data class CartProduct(
    var _id: String,
    var name: String,
    var price: Double,
    var image: String,
    var quantity: Int,
    var mrp: Double
) {
    companion object {
        val MODE_ADD = 1
        val MODE_MINUS = -1
    }
}
