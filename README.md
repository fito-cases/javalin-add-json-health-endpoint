# Users service

A small Javalin 7 service, in Kotlin. It serves one resource:

- `GET /users/{id}` returns the user as JSON, and `404` for an unknown id.

## Layout

- `src/main/kotlin/com/fito/cases/jsonendpoint/App.kt` — the Javalin app and its handlers
- `src/test/kotlin/com/fito/cases/jsonendpoint/AppTest.kt` — the tests

## Run the tests

```
./gradlew test
```
