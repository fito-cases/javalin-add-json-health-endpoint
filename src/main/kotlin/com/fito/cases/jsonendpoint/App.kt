package com.fito.cases.jsonendpoint

import io.javalin.Javalin
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

/** The resource the endpoint returns. With the default Jackson mapper it serializes as-is. */
data class User(val id: Int, val name: String)

/** The data the service exposes. */
private val users = mapOf(1 to "Ada", 2 to "Linus", 3 to "Grace")

/**
 * Handler for `GET /users/{id}`.
 *
 * Reads and validates the `{id}` path parameter, looks the user up in [users], and responds
 * with the [User] as JSON. An unknown id raises [NotFoundResponse], which Javalin maps to a 404.
 */
fun getUser(ctx: Context) {
    val id = ctx.pathParamAsClass("id", Int::class.java).get()
    val name = users[id] ?: throw NotFoundResponse("no user with id $id")
    ctx.json(User(id, name))
}

/** Builds the (unstarted) Javalin app so tests can drive it without binding a port. */
fun createApp(): Javalin =
    Javalin.create { config ->
        config.routes.get("/users/{id}", ::getUser)
        config.routes.get("/health") { ctx -> ctx.json(mapOf("status" to "ok")) }
    }

fun main() {
    createApp().start(7070)
}
