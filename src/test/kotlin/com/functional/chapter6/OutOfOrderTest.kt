package com.functional.chapter6

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class OutOfOrderTest {
    @Test
    fun `unable to call elevator`() {
        expectThat(handleCommand(ElevatorOutOfOrder(5), CallElevatorCommand(12)))
            .isEqualTo(ElevatorOutOfOrder(5))
        expectThat(handleCommandForEvents(ElevatorOutOfOrder(5), CallElevatorCommand(12)))
            .isEmpty()
    }

    @Test
    fun `unable to send elevator`() {
        expectThat(handleCommand(ElevatorOutOfOrder(6), SendElevatorToFloorCommand(12)))
            .isEqualTo(ElevatorOutOfOrder(6))
        expectThat(handleCommandForEvents(ElevatorOutOfOrder(6), SendElevatorToFloorCommand(12)))
            .isEmpty()
    }

    @Test
    fun `unable to fix in order elevator`() {
        expectThat(handleCommand(ElevatorWaitingAtFloor(5), FixElevator))
            .isEqualTo(ElevatorWaitingAtFloor(5))
        expectThat(handleCommandForEvents(ElevatorWaitingAtFloor(5), FixElevator))
            .isEmpty()

        expectThat(handleCommand(ElevatorTravelingToFloor(5), FixElevator))
            .isEqualTo(ElevatorTravelingToFloor(5))
        expectThat(handleCommandForEvents(ElevatorTravelingToFloor(5), FixElevator))
            .isEmpty()
    }

    @Test
    fun `unable to break out of order elevator`() {
        expectThat(handleCommand(ElevatorOutOfOrder(5), BreakElevator))
            .isEqualTo(ElevatorOutOfOrder(5))
        expectThat(handleCommandForEvents(ElevatorOutOfOrder(5), BreakElevator))
            .isEmpty()
    }

    @Test
    fun `can break in order elevator`() {
        expectThat(handleCommand(ElevatorWaitingAtFloor(5), BreakElevator))
            .isEqualTo(ElevatorOutOfOrder(5))
        expectThat(
            handleCommandForEvents(ElevatorWaitingAtFloor(5), BreakElevator)
                .let {
                    listOf(
                        ElevatorMovedToFloor(3),
                        ElevatorButtonPressedEvent(5),
                        *it.toTypedArray()
                    )
                }.let { fold(it) }
        ).isEqualTo(ElevatorOutOfOrder(5))

        expectThat(handleCommand(ElevatorTravelingToFloor(3), BreakElevator))
            .isEqualTo(ElevatorOutOfOrder(3))
        expectThat(
            handleCommandForEvents(ElevatorTravelingToFloor(3), BreakElevator)
                .let {
                    listOf(
                        ElevatorMovedToFloor(3),
                        *it.toTypedArray()
                    )
                }.let { fold(it) }
        ).isEqualTo(ElevatorOutOfOrder(3))
    }

    @Test
    fun `can fix out of order elevator`() {
        expectThat(handleCommand(ElevatorOutOfOrder(5), FixElevator))
            .isEqualTo(ElevatorWaitingAtFloor(5))
        expectThat(
            handleCommandForEvents(ElevatorOutOfOrder(5), FixElevator)
                .let {
                    listOf(
                        ElevatorMovedToFloor(3),
                        ElevatorButtonPressedEvent(5),
                        *it.toTypedArray()
                    )
                }.let { fold(it) }
        ).isEqualTo(ElevatorWaitingAtFloor(5))
    }
}
