package com.functional.chapter6

val initialState = ElevatorWaitingAtFloor(0) as ElevatorState

sealed class ElevatorEvent
data class ElevatorButtonPressedEvent(val floor: Int) : ElevatorEvent()
data class ElevatorMovedToFloor(val floor: Int) : ElevatorEvent()
data object ElevatorBroken : ElevatorEvent()
data object ElevatorFixed : ElevatorEvent()


fun handleCommandForEvents(state: ElevatorState, command: ElevatorCommand) =
    when (command) {
        is CallElevatorCommand -> command.execute(state)
        is SendElevatorToFloorCommand -> command.execute(state)
        is BreakElevator -> command.execute(state)
        is FixElevator -> command.execute(state)
    }

fun fold(events: Iterable<ElevatorEvent>): ElevatorState {
    return events.fold(initialState) { state, event ->
        state.combine(event)
    }
}

private fun CallElevatorCommand.execute(state: ElevatorState) = when (state) {
    is ElevatorWaitingAtFloor -> emptyList()
    is ElevatorTravelingToFloor -> listOf(ElevatorButtonPressedEvent(floor))
    is ElevatorOutOfOrder -> emptyList()
}

private fun SendElevatorToFloorCommand.execute(state: ElevatorState) = when (state) {
    is ElevatorWaitingAtFloor -> listOf(ElevatorMovedToFloor(floor))
    is ElevatorTravelingToFloor -> emptyList()
    is ElevatorOutOfOrder -> emptyList()
}

// force it to only be called on given command
@Suppress("UnusedReceiverParameter")
private fun BreakElevator.execute(state: ElevatorState) = when (state) {
    is ElevatorWaitingAtFloor,
    is ElevatorTravelingToFloor -> listOf(ElevatorBroken)
    is ElevatorOutOfOrder -> emptyList()
}

// force it to only be called on given command
@Suppress("UnusedReceiverParameter")
private fun FixElevator.execute(state: ElevatorState) = when (state) {
    is ElevatorWaitingAtFloor,
    is ElevatorTravelingToFloor -> emptyList()
    is ElevatorOutOfOrder -> listOf(ElevatorFixed)
}
