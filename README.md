[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin_Multiplatform-2.3.0-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/docs/multiplatform.html)
[![Android](https://img.shields.io/badge/Android-AGP%209.0-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![iOS](https://img.shields.io/badge/iOS-Supported-8E8E93?logo=apple&logoColor=white)](https://developer.apple.com)
[![Gradle](https://img.shields.io/badge/Gradle-9.1-02303A?logo=gradle&logoColor=white)](https://gradle.org)
[![Android SDK](https://img.shields.io/badge/Android_SDK-36%20%7C%20min%2024-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
# KMP for Mobile Native Developers

A **Kotlin Multiplatform (KMP)** reference project that demonstrates how to build Android and iOS apps with shared business logic, designed for developers coming from native mobile development.

---

## Purpose

This repository is the **accompanying codebase** for the [*KMP for Mobile Native Developers*](https://medium.com/@santimattius/list/kmp-for-mobile-native-developers-000407b0ebd8) article series. It serves as:

- **A learning resource** — Step-by-step examples for adopting Kotlin Multiplatform when you already know Android (Kotlin/Java) or iOS (Swift/Objective-C).
- **A reference architecture** — A realistic structure with dependency injection, networking, local persistence, and testing that you can adapt to your own apps.
- **A hands-on playground** — Runnable Android and iOS apps (character list and favorites) backed by shared domain, data, and presentation logic.

The project shows how to share code across platforms while keeping **platform-specific UI**: Jetpack Compose on Android and SwiftUI on iOS, with a shared KMP module for everything else.

---

## Features

| Feature | Description |
|--------|-------------|
| **Character list** | Fetch and display characters from a remote API. |
| **Favorites** | Mark characters as favorites; state is persisted locally on both platforms. |
| **Shared logic** | Domain models, use cases, repositories, networking, and local DB live in the `shared` module. |
| **Native UI** | Android uses Compose; iOS uses SwiftUI, each consuming the shared Kotlin framework. |

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| **UI (Android)** | Jetpack Compose, Material 3, Navigation Compose, Coil |
| **UI (iOS)** | SwiftUI |
| **Networking** | Ktor (OkHttp on Android, Darwin on iOS) |
| **Local storage** | SQLDelight |
| **Dependency injection** | Koin |
| **Kotlin ↔ Swift** | Skie |
| **Testing** | Kotlin Test, Turbine, Ktor Mock, Koin Test, Mokkery, Kover |

---

## Project Structure

```
├── composeApp/          # Android app (Jetpack Compose)
│   └── src/main/kotlin/
│       └── com/santimattius/kmp/skeleton/
│           ├── core/           # Theme, base UI components
│           ├── features/       # Home, Favorites screens
│           └── MainApplication.kt
│
├── iosApp/              # iOS app (SwiftUI)
│   └── iosApp/          # SwiftUI views and app entry point
│
├── shared/              # Shared KMP module (Android + iOS)
│   └── src/
│       ├── commonMain/   # Shared Kotlin code
│       │   └── com/santimattius/kmp/
│       │       ├── data/       # Repository, network, local DB, mapping
│       │       ├── di/         # Koin modules
│       │       ├── domain/     # Character model, use cases
│       │       └── viewmodels/ # Shared ViewModels
│       ├── androidMain/  # Android-specific implementations (drivers, DI)
│       ├── iosMain/      # iOS-specific implementations (drivers, DI)
│       ├── commonTest/   # Shared unit and integration tests
│       └── sqldelight/   # SQLDelight schema and queries
│
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
    └── libs.versions.toml
```

- **`composeApp`** — Android application. Contains Compose UI, resources, and AndroidManifest. Depends on `shared`.
- **`iosApp`** — iOS application. Contains SwiftUI views and the app entry point; links to the `Shared` framework produced by `shared`.
- **`shared`** — Kotlin Multiplatform library. `commonMain` holds code shared by all targets; `androidMain` and `iosMain` hold platform-specific code (e.g., SQLDelight drivers, Ktor engines, Koin modules).

---

## Prerequisites

- **JDK 17+**
- **Android Studio** (Ladybug or newer recommended) or **IntelliJ IDEA** with Android and Kotlin Multiplatform plugins
- **Xcode** (for building and running the iOS app)
- **CocoaPods** (for iOS dependency management, if used by the iOS app)

---

## Getting Started

### Clone and open

```bash
git clone <repository-url>
cd kmp-for-mobile-native-developers
```

Open the project in Android Studio or IntelliJ IDEA (the root directory is the Gradle project).

### Run on Android

1. Select the `composeApp` run configuration (or attach a device/emulator).
2. Run the app. The character list and favorites screens use the shared logic from `shared`.

### Run on iOS

1. Open `iosApp/iosApp.xcworkspace` (or `iosApp.xcodeproj`) in Xcode.
2. Select a simulator or device and run. The iOS app uses the same `Shared` framework from the `shared` module.

### Run shared tests

From the project root:

```bash
./gradlew :shared:allTests
```

Coverage is reported with Kover; see `shared/build.gradle.kts` for the configured rules.

---

## Article Series

The project is built and explained in the following articles:

| Part | Topic | Link |
|------|--------|-----|
| 1 | The beginning | [Part 1: The beginning](https://medium.com/@santimattius/kmp-for-mobile-native-developer-part-1-b2856170e548) |
| 2 | Project and concepts | [Part 2: Project and Concepts](https://medium.com/@santimattius/kmp-for-mobile-native-developer-part-2-project-and-concepts-191a85e0c609) |
| 3 | Dependency injection | [Part 3: Dependency Injection](https://medium.com/@santimattius/kmp-for-mobile-native-developer-part-3-dependency-injection-in-kotlin-multiplatform-kmp-15f9c97cfb09) |
| 4 | Modularization | [Part 4: Modularization](https://medium.com/@santimattius/kmp-for-mobile-native-developer-part-4-modularization-7b712a5d5e99) |
| 5 | Testing | [Part 5: Testing](https://medium.com/@santimattius/kmp-for-mobile-native-developers-part-5-testing-46e150d26750) |

---

## Learn More

- [Kotlin Multiplatform — Get started](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
