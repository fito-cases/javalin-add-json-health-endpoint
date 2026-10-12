# Intended approach (evaluator only)

Register a GET route for `/health` in the `Javalin.create` route configuration. Have its handler return a structured value through `Context.json`, so the JSON mapper serializes the body and the media type identifies JSON. The exemplar on the solution branch does it in one line of `App.kt`, with a map that holds `status` → `ok`, and leaves the users route as it is.

## Rubric evidence

`AppTest.kt` holds four tests, the same on the starter and on the solution branch. On the starter, the two health tests fail at their first assertion, because `GET /health` answers 404. The automated gate for both criteria is `./gradlew test`.

- **health-route (55):** the test `GET health returns HTTP 200` passes, and the two users tests still pass.
- **health-json-response (45):** the test `GET health returns status ok as application-json` passes. It parses the body as a JSON object, reads `status` as `ok`, and accepts a Content-Type whose media type is `application/json`, with or without parameters.

The rubric's four levels distinguish the quality of applying the change, not four Bloom depths. A JSON-looking string written with a plain body writer fails the second test on the media type.
