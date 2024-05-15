**ShoppingApp**

A small-scale Android shopping application demonstrating a modern and scalable architecture using MVVM + Clean Architecture principles, Jetpack Compose for UI, and Room for local storage.

**Table of Contents**
-   Introduction
-   Features
  - Screenshots
-   Architecture
-   Tech Stack and Dependencies
-   Strong Points
-   License

**Introduction**

ShoppingApp is a sample Android application designed to demonstrate modern Android development practices. It reads products from a JSON file, writes them into a Room database, and displays the product list using animated Compose views. Users can add items to their cart, view product details on a separate screen, and manage their cart by updating item quantities, removing items, or clearing the cart entirely.

**Features**

-   **Product List:** View a list of products with clear and animated Compose views.
-   **Product Details:** View details of a selected product on a separate screen.
-   **Add to Cart:** Add products to the cart from the product list or product detail screen.
-   **Cart Management:** View items in the cart, update item quantities, remove items, and clear the cart.
-   **Total Calculation:** See the total number of items and the total cost in the cart.


**Screenshots**
<p align="center">
<img src="/Screenshots/Product_List.png" width="32%"/>
<img src="/Screenshots/Product_Detail.png" width="32%"/>
<img src="/Screenshots/Cart.png" width="32%"/>
<img src="/Screenshots/Cart_Quantity.png" width="32%"/>
<img src="/Screenshots/Cart_Empty_Prompt.png" width="32%"/>
<img src="/Screenshots/Cart_Empty.png" width="32%"/>
</p>

**Architecture**

The application follows a clean architecture approach with MVVM, using the following components:

**Presentation Layer**

-   **ViewModels:** Handle UI-related logic and data preparation.
-   **UI Components:** Built with Jetpack Compose for a reactive and animated UI.

**Domain Layer**

-   **UseCases:** Encapsulate business logic and coordinate between repositories and ViewModels.

**Data Layer**

-   **Repositories:** Abstract data sources, providing a clean API for data access.
-   **Data Sources:** Manage local data operations with Room database and JSON parsing.

**Dependency Injection**

-   **Dagger Hilt:** Manages dependencies across the application, ensuring a modular and testable codebase.

**Utils**

-   **JsonFileParser:** Parses JSON files using Moshi.
-   **ResourceHelper:** Provides easy access to resources.

**Tech Stack and Dependencies**

This project leverages several modern libraries and frameworks to ensure scalability, maintainability, and efficiency:

**Core Libraries**

-   **Jetpack Compose:** Used for building reactive UIs with animations.
-   **Room:** Provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
-   **Kotlin Coroutines:** Simplifies asynchronous programming with concise code and built-in support for structured concurrency.
-   **Dagger Hilt:** Simplifies dependency injection in Android apps, making it easier to manage dependencies and improve testability.

**Testing Libraries**

-   **JUnit:** A simple framework to write repeatable tests.
-   **Mockito:** A mocking framework that lets you write tests with a clean and simple API.
-   **MockK:** A modern mocking library for Kotlin.
-   **Google Truth:** Provides fluent assertions for testing in Java and Android.

**UI Libraries**

-   **Coil:** An image loading library for Android backed by Kotlin Coroutines.
-   **Material Components:** Implements Material Design to ensure a cohesive visual experience.

**JSON Parsing**

-   **Moshi:** A modern JSON library for Android and Java, used for parsing JSON files.

**Additional Libraries**

-   **LeakCanary:** A memory leak detection library for Android.
-   **AppCompat:** Provides backward-compatible versions of Android framework APIs.

**Strong Points**

-   **Clean Architecture:** Separation of concerns ensures a scalable and maintainable codebase.
-   **Modern UI:** Uses Jetpack Compose for a reactive and visually appealing UI with animations.
-   **Scalability:** Modular architecture with distinct layers and feature modules.
-   **Local Storage:** Efficiently manages local data storage with Room.
-   **Error Handling:** Well handled Errors for different cases and states.
-   **Asynchronous Operations:** Utilizes Kotlin Coroutines and Flows for efficient background processing.
-   **Dependency Injection:** Dagger Hilt simplifies dependency management and improves testability.
-   **Extensive Testing:** Detailed unit tests ensure reliability and robustness of the application.
