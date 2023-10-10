package com.functional.chapter4

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class DiscardUnlessTest {
    private val itemInProgress = ToDoItem("doing something", status = ToDoStatus.InProgress)
    private val itemBlocked = ToDoItem("must do something", status = ToDoStatus.Blocked)

    @Test
    fun `discardUnless with true`() {
        expectThat(
            itemInProgress.discardUnless { status == ToDoStatus.InProgress }
        ).isEqualTo(itemInProgress)
    }

    @Test
    fun `discardUnless with false`() {
        expectThat(
            itemBlocked.discardUnless { status == ToDoStatus.InProgress }
        ).isEqualTo(null)
    }
}
