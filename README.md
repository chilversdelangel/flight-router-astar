# ✈️ flight-router-astar - A-star Route Discovery

A high-performance route-finding application built with **Compose Multiplatform** and **Kotlin**, designed to solve the shortest path problem between global cities using the **A-star Search Algorithm**.

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-Desktop-blue?style=for-the-badge)
![SQLite](https://img.shields.io/badge/database-H2%20In--Memory-orange?style=for-the-badge)

## 🚀 Overview

FlightRouter is a technical demonstration of graph theory and heuristic search. It calculates the most efficient flight path between two geographic points by analyzing a network of connections and using the **Haversine formula** as a heuristic for the A-star algorithm.

### Key Features
- **A-star Algorithm Implementation:** Optimized pathfinding with suboptimal step skipping.
- **Geographic Precision:** Uses the Haversine formula to calculate real-world distances between coordinates (Latitude/Longitude).
- **Modern UI:** Built with Jetpack Compose for Desktop.
- **Robust Architecture:** strictly follows the **MVVM (Model-View-ViewModel)** pattern.
- **In-Memory Reliability:** Powered by **H2 Database** and **Exposed ORM** for seamless portability.

## 🏗️ Architecture

The project is structured following clean architecture principles:

- **`domain`**: Contains the core logic, models (`City`, `Flight`), and the A-star implementation.
- **`data`**: (In progress) Database schemas and repository implementations using JetBrains Exposed.
- **`ui`**: (In progress) Reactive UI components and ViewModels.

## 🛠️ Tech Stack

- **Language:** Kotlin 2.x
- **UI Framework:** Compose Multiplatform (Desktop)
- **ORM:** JetBrains Exposed
- **Database:** H2 (In-memory)
- **Build System:** Gradle (Kotlin DSL)

## 🧠 The Algorithm

The heart of the application is the **A-star Search Algorithm**. It evaluates nodes based on the formula:
`f(n) = g(n) + h(n)`

Where:
- `g(n)`: The accumulated distance from the origin.
- `h(n)`: The heuristic (Haversine distance) from the current city to the destination.

This ensures the algorithm is both **optimal** and **complete**.

## 🚦 Getting Started

### Prerequisites
- JDK 17 or higher
- IntelliJ IDEA (Recommended)

### Running the App
```bash
./gradlew run
```