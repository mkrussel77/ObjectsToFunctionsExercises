package com.functional.chapter6

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ElevatorCommandTest {
    @Test
    fun `call elevator with elevator waiting for people to enter shall stay`() {
        val result = handleCommand(ElevatorWaitingAtFloor(5), CallElevatorCommand(12))
        expectThat(result).isEqualTo(ElevatorWaitingAtFloor(5))
    }

    @Test
    fun `call elevator with elevator having dropped people off shall wait at the floor`() {
        val result = handleCommand(ElevatorTravelingToFloor(5), CallElevatorCommand(12))
        expectThat(result).isEqualTo(ElevatorWaitingAtFloor(12))
    }

    @Test
    fun `send elevator with elevator waiting for people shall move to new floor`() {
        val result = handleCommand(ElevatorWaitingAtFloor(5), SendElevatorToFloorCommand(12))
        expectThat(result).isEqualTo(ElevatorTravelingToFloor(12))
    }

    @Test
    fun `send elevator with elevator having dropped people off shall shall stay waiting to be called`() {
        val result = handleCommand(ElevatorTravelingToFloor(5), SendElevatorToFloorCommand(12))
        expectThat(result).isEqualTo(ElevatorTravelingToFloor(5))
    }
}
