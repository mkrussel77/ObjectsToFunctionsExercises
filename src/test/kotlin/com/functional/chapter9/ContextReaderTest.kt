package com.functional.chapter9

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

typealias ConfigurationReader = ContextReader<Map<String, String>, String?>

class ContextReaderTest {
    private val env = ConfigurationReader { ctx -> ctx["environment"] }
    private fun serverHost(env: String) = ConfigurationReader { ctx -> ctx["$env-server"] }

    private val data =
        mapOf("environment" to "local", "local-server" to "localhost", "prod-server" to "my-prod-serve.example.com")

    @Test
    fun `bind works as expected`() {

        val res = env.bind { env ->
            serverHost(env ?: "local")
        }.runWith(data)

        expectThat(res).isEqualTo("localhost")

    }

    @Test
    fun `transform works as expected`() {

        val res = env.transform { env ->
            serverHost(env ?: "local")
        }.join().runWith(data)

        expectThat(res).isEqualTo("localhost")
    }
}
