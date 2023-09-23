package com.functional.chapter2

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@JvmInline
value class User(val name: String)

@JvmInline
value class ListName(val name: String)

data class Request(val user: User, val listName: ListName)
data class ToDoItem(val description: String)
data class ToDoList(val listName: ListName, val items: List<ToDoItem>)

@JvmInline
value class HtmlPage(val content: String)

class AndThenTest {
    private val items = listOf("write chapter", "insert code", "draw diagrams")
    private val toDoList = ToDoList(ListName("book"), items.map(::ToDoItem))
    private val lists = mapOf(User("uberto") to listOf(toDoList))

    private fun extractListData(request: Request) = request.user to request.listName
    private fun fetchListContent(listId: Pair<User, ListName>): ToDoList = lists[listId.first]
        ?.firstOrNull { it.listName == listId.second }
        ?: error("List unknown")

    private fun renderHtml(todoList: ToDoList): HtmlPage = HtmlPage("""
<html>
    <body>
        <h1>Zettai</h1>
        <h2>${todoList.listName.name}</h2>
        <table>
            <tbody>${renderItems(todoList.items)}</tbody>
        </table>
    </body>
</html>
""".trimIndent() )

    private fun renderItems(items: List<ToDoItem>) = items.joinToString("") {
        """<tr><td>${it.description}</td></tr>""".trimIndent()
    }

    @Test
    fun `test andThen`() {
        val processFun = ::extractListData andThen
                ::fetchListContent andThen
                ::renderHtml

        val request = Request(User("uberto"), ListName("book"))

        val actual = processFun(request)

        val expect = """
<html>
    <body>
        <h1>Zettai</h1>
        <h2>book</h2>
        <table>
            <tbody><tr><td>write chapter</td></tr><tr><td>insert code</td></tr><tr><td>draw diagrams</td></tr></tbody>
        </table>
    </body>
</html>""".trimIndent()

        assertEquals(expect, actual.content)
    }
}
