package com.example.dexlivery.data.model

data class Order(
    val id: Long,
    val productList: List<Product>,
    val total: Float
)
