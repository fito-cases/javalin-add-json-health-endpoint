package com.fito.cases.jsonendpoint

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
}
