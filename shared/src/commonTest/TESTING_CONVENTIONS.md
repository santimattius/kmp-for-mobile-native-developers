# Testing Conventions — commonTest

**Scope:** `shared/src/commonTest/`

---

## Koin lifecycle (integration tests)

When tests use Koin (e.g. `ExampleUsingDIIntegrationTest`, `CheckModulesTest`):

1. **Always call `stopKoin()` in `@AfterTest`** so Koin is torn down after every test, even on failure. This avoids leftover state affecting other tests.
2. **Start Koin consistently:** either in `@BeforeTest` (e.g. `ExampleUsingDIIntegrationTest`) or inside the test method (e.g. `CheckModulesTest` with `startTestKoin(...).checkModules()`). Prefer starting in the test for module-check tests so `@AfterTest` always runs and cleans up.
3. Use `stopTestKoin()` (or `stopKoin()`) from `integration.di` so all test Koin usage goes through the same cleanup.

---

## Turbine and flow tests

- **Single emission:** `flow.test { assertTrue(awaitItem().isNotEmpty()) }` is fine; no extra cleanup needed.
- **Multiple emissions:** For tests that consume several items (e.g. ViewModel flow: first list, then updated list after action), call **`cancelAndIgnoreRemainingEvents()`** at the end of the test block (or in `finally`) so the collection is cancelled and remaining events are ignored. This avoids leaking coroutines and keeps tests deterministic.

Example:

```kotlin
viewModel.characters.test {
    val list = awaitItem()
    viewModel.addToFavorites(list.first())
    val updated = awaitItem()
    assertTrue(updated.first { it.id == list.first().id }.isFavorite)
    cancelAndIgnoreRemainingEvents()
}
```

---

## Test doubles

- **FakeCharacterNetworkDataSource:** Default success (loads `characters.json`). Use `setResponse` for empty/failure in a single test; cleared after one `all()` call.
- **InMemoryCharacterLocalDataSource:** Keeps both `all` and `favorites` flows in sync when adding/removing favorites; use for ViewModel tests that assert on flow updates.
