# Coroutine Scope and Dispatchers with Hilt

This module follows the architecture described in this article by Manuel Vivo:  
👉 https://manuelvivo.dev/coroutinescope-hilt

It provides a clean and testable way to inject coroutine-related dependencies using Hilt.

---

## 🧩 What’s Included

### ✅ 1. `CoroutinesQualifiers.kt`

Defines custom `@Qualifier`s to distinguish between different coroutine dispatchers and scopes.

```kotlin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope
```

### ✅ 2.CoroutinesDispatchersModule.kt

Provides the standard Kotlin CoroutineDispatcher implementations via Hilt using the custom qualifiers.

```kotlin
@InstallIn(SingletonComponent::class)
@Module
object CoroutinesDispatchersModule {

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

```
### ✅ 3. CoroutinesScopesModule.kt
Provides a singleton CoroutineScope tied to the application lifecycle.

```kotlin
@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {

    @Singleton
    @ApplicationScope
    @Provides
    fun providesCoroutineScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)
}
```

### 🚀 Usage Example
You can now inject the dispatchers and the application-wide coroutine scope anywhere in your app:
```kotlin
class MyRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ApplicationScope private val appScope: CoroutineScope
) {
    fun loadData() {
        appScope.launch(ioDispatcher) {
            // Perform background work here
        }
    }
}

```