# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build all modules
./gradlew build

# Build and install debug APK
./gradlew :app:installDebug

# Run unit tests
./gradlew test

# Run a single module's tests
./gradlew :feature:experience:test

# Run Android instrumentation tests
./gradlew connectedAndroidTest

# Lint
./gradlew lint

# Increment version code (custom task)
./gradlew incrementVersionCode
```

## Architecture Overview

This is a multi-module Android app following Clean Architecture with Jetpack Compose.

### Module Layers

- **`app/`** — Entry point. `MainActivity` hosts the `NavHost` and wires all feature routes.
- **`core/domain`** — Pure JVM library. Contains entities (`Cv`, `Experience`, etc.), the `CvRepository` interface, and use cases (`GetCvUseCases`, `ObserveCvUseCase`). No Android dependencies.
- **`core/data`** — Repository implementation (`CvRepositoryImpl`), Retrofit API (`CvApiService` → `GET /cv.json`), Moshi DTOs/mappers, and Hilt modules. Flavor-specific implementations live here (`real`/`mock`).
- **`core/dispatcher`** — Hilt-provided coroutine dispatchers with qualifiers: `@IoDispatcher`, `@DefaultDispatcher`, `@MainDispatcher`, `@MainImmediateDispatcher`.
- **`core/designsystem`** — Material Design 3 theme and shared UI components.
- **`core/common`** — Navigation routes defined as a serializable sealed `Route` interface.
- **`feature/*`** — One module per screen. Each feature depends on `core:common` and `core:designsystem` (injected automatically by the `cvshowcase-feature` convention plugin).

### Convention Plugins (`build-logic/convention`)

All modules use convention plugins instead of duplicating Gradle config:
- `cvshowcase-application` — App module (Compose, Hilt, Firebase, build types, flavors)
- `cvshowcase-library` — Base Android library
- `cvshowcase-feature` — Feature modules (extends library, auto-adds `core:common` + `core:designsystem`)
- `cvshowcase-hilt` — Adds Hilt to any module
- `cvshowcase-jvm-library` — Pure Kotlin library (used by `core:domain`)
- `cvshowcase-flavors` — `real` (Firebase/prod) and `mock` (fake data) build variants

### Build Flavors

| Flavor | Purpose |
|--------|---------|
| `real` | Production — uses Firebase and the live Retrofit endpoint |
| `mock` | Development/testing — uses an in-memory mock `CvRepository` |

### Navigation

Routes are declared in `core/common` as a serializable sealed interface:

```kotlin
interface Route {
    @Serializable data object Experience : Route
    @Serializable data object Profile : Route
    @Serializable data class DetailExperience(val title: String) : Route
}
```

The `NavHost` lives in `MainActivity`. Use Jetpack Navigation Compose with type-safe routes.

### Key Patterns

- **State**: ViewModels expose `StateFlow<ScreenState>`. Collect with `collectAsStateWithLifecycle()`.
- **Search debouncing**: 500 ms `debounce` on the search query flow (see `ExperienceViewModel`).
- **Immutable collections**: Use `toPersistentList()` from `kotlinx-collections-immutable` in UI state.
- **Dispatcher injection**: Always inject `@IoDispatcher` for network/disk work rather than hardcoding `Dispatchers.IO`.
- **Error handling**: Use `.catch { }` on flows; propagate errors as part of UI state.

### Tech Stack Highlights

- Kotlin 2.2.0 / Compose BOM 2024.09.00 / Navigation Compose 2.9.3
- Hilt (DI) + KSP (annotation processing)
- Retrofit 3.0 + Moshi 1.15.2 (Kotlin Codegen)
- Coil 3.3.0 (image loading)
- Firebase Analytics + Crashlytics (production flavor only)
- Timber for logging
- `kotlinx-coroutines-test` for ViewModel/flow unit tests