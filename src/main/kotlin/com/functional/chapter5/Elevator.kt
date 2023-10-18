package com.functional.chapter5

data class Elevator(val floor: Int) sealed class Direction
data object Up : Direction()
data object Down : Direction()
