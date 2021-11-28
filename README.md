# Minimalistic Github Client

## Library used
- [Retrofit] to create network requests.
- [Koin] for dependency injection.
- [Paging3] for implementing paging.
- [Room] for saving data to database
- [Glide] for loading image
- Kotlin [Coroutines]
- [Flow]
- [Livedata]

## Achievements

- Create search screen with input field.
- Create search result shows only network result from user's input and support pagination with loading state and retry in error state.
- Create detail screen shows user's common info and user's public repositories. Layout is different between portrait and landscape.
- Cache user's common info and user's public repositories
- Apply clean architecture structure.

## Improvements
- Migrate from kotlin-android-extensions to View Binding due to kotlin-android-extensions deprecated.
- Combine search screen and search result screen.
- Combine database search and network search.
- Apply testing.
