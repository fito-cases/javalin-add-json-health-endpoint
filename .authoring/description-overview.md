## Add a JSON Health Endpoint

A small Javalin 7 service serves user data but has no health response for a monitoring client. In this 35-minute implementation case, you’ll add a GET health endpoint that returns the requested JSON response while keeping the existing users API working.

You’ll practice adding a route to an existing service and producing a response whose body and HTTP media type agree. Start from the provided project, inspect its tests, make your change, and run `./gradlew test`.
