package com.functional.chapter3

typealias Cart = Map<Item, Int>

private const val DISCOUNT_ITEM_RATE = 3

data class Cashier (
    val prices: Map<Item, Double> = emptyMap(),
    val carts: Map<String, Cart> = emptyMap(),
    val discountedItems: Set<Item> = emptySet()
) {
    fun setPrices(newPrices: Map<Item, Double>) = copy(prices = newPrices)

    fun add3x2Discount(item: Item) = copy(discountedItems = discountedItems + item)

    fun addItem(customerName: String, qty: Int, item: Item) =
        copy(carts = carts + mapOf(customerName to customerCart(customerName).add(qty, item)))

    fun totalFor(customerName: String) = customerCart(customerName).entries.sumOf { (item, qty) ->
        getPriceForItem(qty, item)
    }

    private fun getPriceForItem(qty: Int, item: Item) =
        prices.getOrDefault(item, 0.0) * (qty - freeItems(qty, item))

    private fun freeItems(qty: Int, item: Item) = if (item in discountedItems) {
       qty / DISCOUNT_ITEM_RATE
    } else {
        0
    }

    private fun customerCart(customerName: String) = carts[customerName] ?: emptyMap()
}

private fun Cart.add(qty: Int, item: Item): Cart {
    val mutableCart = toMutableMap()
    mutableCart[item] = getOrDefault(item, 0) + qty
    return mutableCart
}
