package com.functional.chapter3

import com.ubertob.pesticide.core.DdtProtocol
import com.ubertob.pesticide.core.DomainOnly
import com.ubertob.pesticide.core.DomainSetUp
import com.ubertob.pesticide.core.Ready

class DomainCashierActions : CashierActions {
    override val protocol: DdtProtocol = DomainOnly

    private var cashier = Cashier()

    override fun prepare(): DomainSetUp {
        cashier = Cashier()
        return Ready
    }

    override fun setupPrices(prices: Map<Item, Double>) {
        cashier = cashier.setPrices(prices)
    }

    override fun add3x2Discount(item: Item) {
        cashier = cashier.add3x2Discount(item)
    }

    override fun totalFor(actorName: String) = cashier.totalFor(actorName)

    override fun addItem(actorName: String, qty: Int, item: Item) {
        cashier = cashier.addItem(actorName, qty, item)
    }
}
