# Service ticket: JSON health check

The users service already answers `GET /users/{id}`, but our monitoring client has no lightweight way to check that the service responds. Add `GET /health` so a successful request returns HTTP 200 and a JSON object containing `"status": "ok"`. The response must identify its body as JSON through the HTTP media type.

Keep the existing users API working. The new health response should be testable alongside the existing route tests.
