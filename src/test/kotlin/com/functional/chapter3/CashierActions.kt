package com.functional.chapter3

import com.ubertob.pesticide.core.DdtActions
import com.ubertob.pesticide.core.DdtProtocol

interface CashierActions : DdtActions<DdtProtocol> {
    fun setupPrices(prices: Map<Item, Double>)
    fun add3x2Discount(item: Item)
    fun totalFor(actorName: String): Double
    fun addItem(actorName: String, qty: Int, item: Item)
}
