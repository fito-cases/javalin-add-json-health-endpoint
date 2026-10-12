package com.fito.cases.jsonendpoint

import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.testtools.JavalinTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AppTest {
    @Test
    fun `GET users-id returns the user as application-json`() {
        JavalinTest.test(createApp()) { _, client ->
            val response = client.get("/users/1")
            assertEquals(200, response.code)
            val contentType = response.headers().get("Content-Type").orEmpty()
            assertTrue(
                contentType.any { it.contains("application/json") },
                "expected a Content-Type of application/json but was <$contentType>",
            )
            val body = response.body.string()
            assertTrue(body.contains("\"id\":1"), "expected id field in <$body>")
            assertTrue(body.contains("\"name\":\"Ada\""), "expected name field in <$body>")
        }
    }

    @Test
    fun `GET users-id returns 404 for an unknown id`() {
        JavalinTest.test(createApp()) { _, client ->
            assertEquals(404, client.get("/users/999").code)
        }
    }

    @Test
    fun `GET health returns HTTP 200`() {
        JavalinTest.test(createApp()) { _, client ->
            assertEquals(200, client.get("/health").code)
        }
    }

    @Test
    fun `GET health returns status ok as application-json`() {
        JavalinTest.test(createApp()) { _, client ->
            val response = client.get("/health")
            assertEquals(200, response.code)
            val json = ObjectMapper().readTree(response.body.string())
            assertTrue(json.isObject, "expected a JSON object but was <$json>")
            assertEquals("ok", json.path("status").asText())
            val contentType = response.headers().get("Content-Type").orEmpty()
            assertTrue(
                contentType.any { it.substringBefore(';').trim().equals("application/json", ignoreCase = true) },
                "expected a Content-Type of application/json but was <$contentType>",
            )
        }
    }
}
