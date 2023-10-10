package com.functional.chapter4

import com.functional.chapter2.User
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull


data class Request(val user: User?, val listName: ListName?)
data class ToDoList(val listName: ListName, val items: List<ToDoItem>)

@JvmInline
value class HtmlPage(val content: String)

private val items = listOf("write chapter", "insert code", "draw diagrams")
private val toDoList = ToDoList(ListName("book"), items.map(::ToDoItem))
private val lists = mapOf(User("uberto") to listOf(toDoList))

class AndUnlessNullTest {
    private fun extractListData(request: Request) = if (request.user != null && request.listName != null) {
        request.user to request.listName
    } else {
        null
    }

    private fun fetchListContent(listId: Pair<User, ListName>): ToDoList? = lists[listId.first]
        ?.firstOrNull { it.listName == listId.second }

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

    private val processUnlessNull = ::extractListData andUnlessNull ::fetchListContent andUnlessNull
            ::renderHtml

    @Test
    fun `test andUnlessNull with no nulls`() {
        val request = Request(User("uberto"), ListName("book"))

        val actual = processUnlessNull(request)

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

        expectThat(actual?.content)
            .isNotNull()
            .isEqualTo(expect)
    }

    @Test
    fun `test andUnlessNull with no list in request`() {
        val request = Request(null, null)

        val actual = processUnlessNull(request)

        expectThat(actual?.content).isNull()
    }

    @Test
    fun `test andUnlessNull with unknown list`() {
        val request = Request(User("uberto"), ListName("unknown"))

        val actual = processUnlessNull(request)

        expectThat(actual?.content).isNull()
    }
}
