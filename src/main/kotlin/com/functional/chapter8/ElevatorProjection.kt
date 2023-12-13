package com.functional.chapter8

data class ElevatorProjectionRow(
    val elevatorId: Int,
    val floor: Int,
    val state: ElevatorState
)

interface ElevatorProjection {
    fun allRows(): List<ElevatorProjectionRow>
    fun getRow(elevatorId: Int): ElevatorProjectionRow?
}

class ElevatorProjectionInMemory(events: List<ElevatorEvent>) : ElevatorProjection {
    private val table = events.groupBy { it.elevatorId }
        .mapValues { (elevatorId, eventsForElevator) ->
            foldEvents(eventsForElevator).let { state ->
                ElevatorProjectionRow(elevatorId, state.floor, state)
            }
        }

    override fun allRows(): List<ElevatorProjectionRow> = table.values.toList()
    override fun getRow(elevatorId: Int): ElevatorProjectionRow? = table[elevatorId]
}
