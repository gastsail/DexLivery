package com.example.dexlivery.domain

import com.example.dexlivery.data.model.Product
import javax.inject.Inject

class MainRepoImpl @Inject constructor(): MainRepo {
    override suspend fun fetchProductList(): List<Product> {
        return dummyProductList
    }
}

val dummyProductList = listOf(
    Product(
        1,
        "Pizza 4 quesos",
        "Pizza a los 4 quesos",
        231.5f,
        "https://marios2for1pizza.com/img/placeholders/pizza_placeholder.png"
    ),
    Product(
        2,
        "Pizza Mozzarella",
        "Pizza mozzarella con oregano",
        153.5f,
        "https://marios2for1pizza.com/img/placeholders/pizza_placeholder.png"
    ),
    Product(
        3,
        "Pizza Calabresa",
        "Pizza con calabresa",
        55.0f,
        "https://marios2for1pizza.com/img/placeholders/pizza_placeholder.png"
    ),
    Product(
        4,
        "Pizza Especial",
        "Pizza especial con jamon ym orron",
        89.9f,
        "https://marios2for1pizza.com/img/placeholders/pizza_placeholder.png"
    )
)