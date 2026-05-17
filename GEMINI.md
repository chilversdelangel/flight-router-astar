# FlightRouter - A* Route Discovery

## Project Overview
Final course project for Route Determination using the A* algorithm.
- **Goal:** Find the shortest path between cities using a graph stored in a database.
- **Algorithm:** A* (Haversine formula for heuristic distance).
- **Target:** Desktop (Compose Multiplatform).

## Tech Stack
- **Framework:** Compose Multiplatform (Desktop only).
- **Database:** H2 (In-memory).
- **ORM:** JetBrains Exposed.
- **Architecture:** MVVM (Model-View-ViewModel).

## Architecture Guidelines
- **Model:** Domain entities (`City`, `Flight`, `Graph`) and Algorithm logic.
- **View:** Compose UI for Desktop.
- **ViewModel:** Managing UI state and triggering the A* calculation.
- **Repository/Data:** Database access via Exposed ORM (to be implemented).

## Project Structure
- `composeApp/src/jvmMain/kotlin/.../domain`: Core logic and models.
- `composeApp/src/jvmMain/kotlin/.../ui`: UI components and ViewModels.
- `composeApp/src/jvmMain/kotlin/.../data`: Database schema and repository implementation.

## Contextual Notes
- This is "Práctica 3 de 4". High quality and adherence to patterns (MVVM) are critical for scoring.
- Database is in-memory for fast development and portability.
