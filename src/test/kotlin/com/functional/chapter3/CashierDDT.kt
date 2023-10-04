package com.functional.chapter3

import com.functional.chapter3.Item.carrot
import com.functional.chapter3.Item.milk
import com.ubertob.pesticide.core.DDT
import com.ubertob.pesticide.core.DomainDrivenTest

val allActions = setOf(DomainCashierActions())

class CashierDDT : DomainDrivenTest<CashierActions>(allActions) {
    val alice by NamedActor(::Customer)

    @DDT
    fun `customer can buy an item`() = ddtScenario {
        val prices = mapOf(carrot to 2.0, milk to 5.0)
        setUp {
            setupPrices(prices)
        }.thenPlay(
            alice.`can add #qty #item`(3, carrot),
            alice.`can add #qty #item`(1, milk),
            alice.`check total is #total`(11.0)
        )
    }

    @DDT
    fun `customer can benefit from 3x2 offer`() = ddtScenario {
        val prices = mapOf(carrot to 2.0, milk to 5.0)
        setUp {
            setupPrices(prices)
            add3x2Discount(milk)
        }.thenPlay(
            alice.`can add #qty #item`(3, carrot),
            alice.`can add #qty #item`(3, milk),
            alice.`check total is #total`(16.0)
        )
    }
}
