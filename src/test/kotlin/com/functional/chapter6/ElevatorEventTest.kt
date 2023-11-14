package com.functional.chapter6

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class ElevatorEventTest {
    @Test
    fun `valid commands shall move as expected`() {
        var events = emptyList<ElevatorEvent>()

        events += handleCommandForEvents(fold(events), SendElevatorToFloorCommand(5))
        expectThat(fold(events)).isEqualTo(ElevatorTravelingToFloor(5))

        events += handleCommandForEvents(fold(events), CallElevatorCommand(3))
        expectThat(fold(events)).isEqualTo(ElevatorWaitingAtFloor(3))
    }

    @Test
    fun `invalid commands shall not change state`() {
        expectThat(
            handleCommandForEvents(ElevatorTravelingToFloor(5), SendElevatorToFloorCommand(12)))
            .isEmpty()

        expectThat(
            handleCommandForEvents(ElevatorWaitingAtFloor(5), CallElevatorCommand(12)))
            .isEmpty()
    }
}
