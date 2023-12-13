package com.functional.chapter8

sealed class ElevatorEvent {
    abstract val elevatorId: Int

    data class ButtonPressed(override val elevatorId: Int, val floor: Int) : ElevatorEvent()
    data class ElevatorMoved(override val elevatorId: Int, val fromFloor: Int, val toFloor: Int) :
        ElevatorEvent()

    data class ElevatorBroken(override val elevatorId: Int) : ElevatorEvent()
    data class ElevatorFixed(override val elevatorId: Int) : ElevatorEvent()
}

sealed class ElevatorState {
    abstract val floor: Int

    data class DoorsOpenAtFloor(override val floor: Int) : ElevatorState()
    data class TravelingAtFloor(override val floor: Int) : ElevatorState()
    data object OutOfOrder : ElevatorState() {
        override val floor: Int = 0
    }
}

private val initialState: ElevatorState = ElevatorState.DoorsOpenAtFloor(0)
fun foldEvents(events: List<ElevatorEvent>): ElevatorState =
    events.fold(initialState) { state, event ->
        when (event) {
            is ElevatorEvent.ButtonPressed -> if (state != ElevatorState.DoorsOpenAtFloor(event.floor))
                ElevatorState.TravelingAtFloor(event.floor)
            else
                state

            is ElevatorEvent.ElevatorMoved -> ElevatorState.DoorsOpenAtFloor(event.toFloor)

            is ElevatorEvent.ElevatorBroken -> ElevatorState.OutOfOrder
            is ElevatorEvent.ElevatorFixed -> ElevatorState.DoorsOpenAtFloor(0)
        }
    }
