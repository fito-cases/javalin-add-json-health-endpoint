# Intended approach (evaluator only)

Register a GET route for `/health` in the `Javalin.create` route configuration. Have its handler return a structured value (for example a map containing `status` → `ok`) through `Context.json`, so the outgoing body is serialized by the JSON mapper and the media type identifies JSON. Preserve the users route. The source-planning/build stage must prepare a starter with failing health assertions and a separate exemplar branch with this implementation; the current project has neither yet.

## Rubric evidence

- **health-route (55):** a request to `GET /health` receives HTTP 200; existing users tests continue to pass. The focused health test must be included in the source-work stage. The automated gate is `./gradlew test`.
- **health-json-response (45):** the response parses as a JSON object containing `status: "ok"`, and its Content-Type identifies `application/json` (allowing additional parameters). Add a focused test for the body and header in the source-work stage. The automated gate is `./gradlew test`.

The rubric's four levels distinguish quality of applying the change, not four Bloom depths. Revisit the evidence and test setup after building the starter and exemplar.
