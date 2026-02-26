# 🎬 FlickPick — Android Movie App

A production-quality Android movie browsing app built with **Jetpack Compose**, **Kotlin**, and the **TMDB API**. Browse popular and top-rated movies, search by title, and view detailed movie information — all wrapped in a polished Material 3 UI with dark mode support.

## 📸 Screenshots

> _Add screenshots here after running the app_

## 🏗️ Architecture

```
┌──────────────────────────────────────────────────┐
│                  Presentation                     │
│  ┌──────────┐  ┌──────────┐  ┌──────────────┐   │
│  │  Screens  │  │ViewModels│  │  Components  │   │
│  │ (Compose) │──│ (State)  │  │  (Reusable)  │   │
│  └──────────┘  └────┬─────┘  └──────────────┘   │
│                      │                            │
├──────────────────────┼───────────────────────────┤
│                Domain│                            │
│  ┌──────────┐  ┌────┴─────┐                      │
│  │  Models  │  │Repository│  (Interface)          │
│  └──────────┘  └────┬─────┘                      │
│                      │                            │
├──────────────────────┼───────────────────────────┤
│                 Data │                            │
│  ┌──────────┐  ┌────┴─────┐  ┌──────────────┐   │
│  │   DTOs   │  │ Repo Impl│  │  ApiService   │   │
│  └──────────┘  └──────────┘  └──────────────┘   │
│                                                   │
│  ┌──────────────────────────────────────────┐    │
│  │  OkHttp + ApiKeyInterceptor + Retrofit   │    │
│  └──────────────────────────────────────────┘    │
└──────────────────────────────────────────────────┘
```

## 🛠️ Tech Stack

| Component        | Technology                              |
|------------------|-----------------------------------------|
| Language         | Kotlin                                  |
| UI               | Jetpack Compose + Material 3            |
| Architecture     | MVVM + Repository Pattern + Clean Arch  |
| Networking       | Retrofit 2 + Gson                       |
| Images           | Coil (AsyncImage)                       |
| Async            | Coroutines + Flow                       |
| DI               | Hilt (Dagger-Hilt)                      |
| Navigation       | Navigation Compose                      |

## 🚀 Getting Started

### 1. Get a TMDB API Key

1. Create a free account at [themoviedb.org](https://www.themoviedb.org/)
2. Go to **Settings → API** → Request an API key
3. Copy your **API Key (v3 auth)**

### 2. Configure the API Key

Open `local.properties` in the project root and add:

```properties
TMDB_API_KEY=your_api_key_here
```

> ⚠️ Never commit `local.properties` to version control.

### 3. Build & Run

```bash
# Command line
./gradlew assembleDebug

# Or open in Android Studio and press Run ▶
```

**Requirements:** Android Studio Ladybug+, JDK 11+, Min SDK 24

## 📂 Project Structure

```
com.example.flickpick/
├── data/           # DTOs, Retrofit API, mappers, repository impl
├── domain/         # Clean domain models & repository interface
├── presentation/   # Screens, ViewModels, components, navigation
├── di/             # Hilt DI modules
├── ui/theme/       # Material 3 colors, typography, theme
├── util/           # UiState sealed class
├── MainActivity.kt
└── FlickPickApplication.kt
```

## 🔮 Future Enhancements

- **Paging 3** — Infinite scroll for movie lists
- **Room** — Offline caching
- **Favourites** — Save and manage favourite movies
- **Genre Filter** — Filter by genre on home screen
- **Multi-module** — Split into feature modules
