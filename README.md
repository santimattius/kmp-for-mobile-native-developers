# KMP for Mobile Native Developers
Accompanying project for the articles in the KMP for Native Mobile Developers series.

- [Part. 1: The beginning](https://medium.com/@santimattius/kmp-for-mobile-native-developer-part-1-b2856170e548) 
- [Part. 2: Project and Concepts](https://medium.com/@santimattius/kmp-for-mobile-native-developer-part-2-project-and-concepts-191a85e0c609)
- [Part. 3: Dependency Injection](https://medium.com/@santimattius/kmp-for-mobile-native-developer-part-3-dependency-injection-in-kotlin-multiplatform-kmp-15f9c97cfb09) 
- [Part. 4: Modularization](https://medium.com/@santimattius/kmp-for-mobile-native-developer-part-4-modularization-7b712a5d5e99)
- [Part. 5: Testing](https://medium.com/@santimattius/kmp-for-mobile-native-developers-part-5-testing-46e150d26750)
## Project

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
