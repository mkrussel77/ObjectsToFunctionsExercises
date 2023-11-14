package com.functional.chapter6

sealed class ElevatorState {
    abstract fun combine(event: ElevatorEvent): ElevatorState
}

data class ElevatorWaitingAtFloor(val floor: Int) : ElevatorState() {
    override fun combine(event: ElevatorEvent) = when (event) {
        is ElevatorButtonPressedEvent -> this
        is ElevatorMovedToFloor -> ElevatorTravelingToFloor(event.floor)
        is ElevatorBroken -> ElevatorOutOfOrder(floor)
        is ElevatorFixed -> this
    }
}

data class ElevatorTravelingToFloor(val floor: Int) : ElevatorState() {
    override fun combine(event: ElevatorEvent): ElevatorState = when (event) {
        is ElevatorButtonPressedEvent -> ElevatorWaitingAtFloor(event.floor)
        is ElevatorMovedToFloor -> this
        is ElevatorBroken -> ElevatorOutOfOrder(floor)
        is ElevatorFixed -> this
    }
}

data class ElevatorOutOfOrder(val floor: Int) : ElevatorState() {
    override fun combine(event: ElevatorEvent): ElevatorState {
        return when (event) {
            is ElevatorButtonPressedEvent -> this
            is ElevatorMovedToFloor -> this
            is ElevatorBroken -> this
            is ElevatorFixed -> ElevatorWaitingAtFloor(floor)
        }
    }
}

sealed class ElevatorCommand
data class CallElevatorCommand(val floor: Int) : ElevatorCommand()
data class SendElevatorToFloorCommand(val floor: Int) : ElevatorCommand()
data object BreakElevator : ElevatorCommand()
data object FixElevator : ElevatorCommand()

fun handleCommand(state: ElevatorState, command: ElevatorCommand) = when (command) {
    is CallElevatorCommand -> command.execute(state)
    is SendElevatorToFloorCommand -> command.execute(state)
    is BreakElevator -> command.execute(state)
    is FixElevator -> command.execute(state)
}

private fun CallElevatorCommand.execute(state: ElevatorState) = when (state) {
    is ElevatorWaitingAtFloor -> state
    is ElevatorTravelingToFloor -> ElevatorWaitingAtFloor(floor)
    is ElevatorOutOfOrder -> state
}

private fun SendElevatorToFloorCommand.execute(state: ElevatorState) = when (state) {
    is ElevatorWaitingAtFloor -> ElevatorTravelingToFloor(floor)
    is ElevatorTravelingToFloor -> state
    is ElevatorOutOfOrder -> state
}

// force it to only be called on given command
@Suppress("UnusedReceiverParameter")
private fun BreakElevator.execute(state: ElevatorState) = when (state) {
    is ElevatorWaitingAtFloor -> ElevatorOutOfOrder(state.floor)
    is ElevatorTravelingToFloor -> ElevatorOutOfOrder(state.floor)
    is ElevatorOutOfOrder -> state
}

// force it to only be called on given command
@Suppress("UnusedReceiverParameter")
private fun FixElevator.execute(state: ElevatorState) = when (state) {
    is ElevatorWaitingAtFloor,
    is ElevatorTravelingToFloor -> state
    is ElevatorOutOfOrder -> ElevatorWaitingAtFloor(state.floor)
}
