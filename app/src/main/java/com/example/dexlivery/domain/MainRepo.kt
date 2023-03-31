package com.example.dexlivery.domain

import com.example.dexlivery.data.model.Product

interface MainRepo {
    suspend fun fetchProductList(): List<Product>
}