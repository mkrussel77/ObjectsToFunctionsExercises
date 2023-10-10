package com.functional.chapter4

import java.time.LocalDate

@JvmInline
value class ListName(val name: String)
data class ToDoList(val listName: ListName, val items: List<ToDoItem>)
data class ToDoItem(
    val description: String,
    val dueDate: LocalDate? = null,
    val status: ToDoStatus = ToDoStatus.Todo
)
enum class ToDoStatus { Todo, InProgress, Done, Blocked }
