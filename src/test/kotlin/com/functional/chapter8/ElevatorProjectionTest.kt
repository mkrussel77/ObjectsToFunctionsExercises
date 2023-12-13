package com.functional.chapter8

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ElevatorProjectionTest {
    @Test
    fun `generate projection from elevator state`() {

        val events = listOf(
            ElevatorEvent.ElevatorMoved(0, 0, 0),
            ElevatorEvent.ElevatorMoved(1, 0, 0),
            ElevatorEvent.ElevatorMoved(2, 0, 0),
            ElevatorEvent.ButtonPressed(1, 5),
            ElevatorEvent.ElevatorMoved(1, 0, 5),
            ElevatorEvent.ButtonPressed(0, 6),
            ElevatorEvent.ElevatorBroken(2)
        )

        val projection = ElevatorProjectionInMemory(events)

        expectThat(projection.getRow(0))
            .isEqualTo(ElevatorProjectionRow(0, 6, ElevatorState.TravelingAtFloor(6)))
        expectThat(projection.getRow(1))
            .isEqualTo(ElevatorProjectionRow(1, 5, ElevatorState.DoorsOpenAtFloor(5)))
        expectThat(projection.getRow(2))
            .isEqualTo(ElevatorProjectionRow(2, 0, ElevatorState.OutOfOrder))

    }
}
