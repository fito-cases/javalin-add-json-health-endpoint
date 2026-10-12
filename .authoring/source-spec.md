# Source specification — Add a JSON Health Endpoint

## Project

Reuse the Kotlin/JVM users service at this repository root. Gradle 9 wrapper, Kotlin 2, Java 21 and Javalin 7; `build.gradle.kts` already declares Javalin, the Kotlin Jackson JSON module, Javalin testtools, JUnit Jupiter and the JUnit platform launcher, configures Java toolchain 21 and `useJUnitPlatform()`. `settings.gradle.kts` names `javalin-health-endpoint`; `.gitignore` excludes `.gradle/`, `.kotlin/` and `build/`. `src/main/kotlin/com/fito/cases/jsonendpoint/App.kt` provides `createApp()` and `GET /users/{id}`, returning the user as JSON or 404 when absent; `src/test/kotlin/com/fito/cases/jsonendpoint/AppTest.kt` exercises both user cases through `JavalinTest`. `README.md` describes the service and `./gradlew test`. The tree must support the service-ticket story in `.case/situation/01_intro.md`: monitoring needs a lightweight `GET /health` with HTTP 200, a JSON object containing `"status": "ok"`, and a JSON media type, without changing the users API.

The version-7 knowledge grounds registration via `config.routes.get("/health") { ctx -> ... }` inside `Javalin.create { config -> ... }` and structured output via `ctx.json(mapOf("status" to "ok"))`. The existing `App.kt` demonstrates the same registration/JSON mechanisms. Registration is not itself a running request; tests must send an actual GET. JSON-looking text alone does not establish a JSON response media type. The header may contain additional parameters; compare the media type, not the entire header string. Parse the JSON and check its `status` property rather than comparing formatting or property order.

## Starter state — branch carrying `.case/`

This is an **implementation gap**, not a broken users service. Retain the existing users route and existing users tests. `App.kt` must have **no working GET /health route**; it must not contain the exemplar handler, a commented-out solution, or code that hints at its implementation. `AppTest.kt` must contain two additional, focused health tests, named `GET health returns HTTP 200` and `GET health returns status ok as application-json`. The first sends `GET /health` and fails because the response is not 200 (missing route normally yields 404). The second sends `GET /health`, first checks for 200, then parses a JSON object and checks `status` equals `ok` and the Content-Type identifies `application/json` (permitting parameters); it fails at its 200 prerequisite while the route is absent. Both existing users tests must still pass; the project must compile and the underlying users service must remain healthy. The starter contract records `build_status: passing`: preserve a buildable project and passing preexisting users tests, while the *new full test gate* `./gradlew test` is intentionally red on exactly the two health tests. Do not claim the full suite passes at starter.

Known error, verbatim from `known_state.yaml`: `GET /health does not yet provide the requested successful JSON health response.` (type `missing_health_endpoint`). There are no `failing_tests` entries in `known_state.yaml`; the two required focused health checks are mandated by its accompanying description and solution notes, and must be made real by source work. No unrelated test, compile error, or build-tool failure is acceptable.

## Solution state — local branch `case/solution-v1.0.0`

`App.kt` adds a working `GET /health` inside the existing route configuration, writes a structured value using the configured JSON mapper and leaves the users behavior intact. `AppTest.kt` retains both users tests and both focused health tests. Deliverable `src/main/kotlin/com/fito/cases/jsonendpoint/App.kt`: a working JSON health endpoint in the existing service. Deliverable `./gradlew test`: the health contract and existing users behavior pass the project test suite. The branch must exist locally; source planning neither publishes nor pushes it.

All automated rubric gates, with command and passing condition **verbatim**:

| Criterion | Command | Passing condition |
| --- | --- | --- |
| `health-route` | `./gradlew test` | `The health-route status test and the existing users-route tests pass.` |
| `health-json-response` | `./gradlew test` | `The health-response tests for the JSON status field and application/json media type pass.` |

Expected observations: `GET /health` returns 200 and parseable JSON object with `status` equal to `ok`; Content-Type identifies `application/json`; users-success and users-missing tests stay green. Verify both gates by running the same `./gradlew test` command and inspecting the four test results.

## Constraints

No `task/constraints.md` or `task/steps.md` exists. Respect the ticket: add only the requested GET endpoint and tests alongside the users tests, retain both existing user cases and their HTTP behavior. Do not change `.case/` while doing source work; refine case metadata only in the later Fito hand-back. Use the already-declared build/test dependencies instead of replacing the project's build configuration. Verify the actual JSON parser API available in the installed dependencies before choosing the test's parsing expression; the test contract is parseable JSON and a checked field, not a particular invented parser signature.
