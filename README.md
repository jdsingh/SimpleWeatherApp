# Simple Weather App

The Simple Weather App is a mobile application that allows users to search for and view weather information for different cities. It provides current weather conditions, including temperature.

# Tech Stack

The app is built using the following technologies:

- **Kotlin**: The primary programming language used for Android development.
- **Jetpack Compose**: A modern toolkit for building native Android UI.
- **Coroutines**: For managing background threads and performing asynchronous operations.
- **Retrofit**: For making network requests to the weather API.
- **DataStore**: For storing user preferences and data locally.

## Libraries used

- **Jetpack Compose**: For building the UI.
- **Retrofit**: For network operations.
- **Koin**: For dependency injection.
- **DataStore**: For local data storage.
- **Mockito**: For mocking dependencies in tests.
- **JUnit**: For unit testing.

# Architecture

The app follows the principles of Clean Architecture to ensure a scalable and maintainable codebase. The architecture is divided into three main layers:

1. **Presentation Layer**: Contains UI components and ViewModels. The UI is built using Jetpack Compose, and the ViewModels follow the MVVM pattern to manage UI-related data.
2. **Domain Layer**: Contains use cases that encapsulate the business logic of the application.
3. **Data Layer**: Responsible for data management, including network requests and local data storage.

## Usage of MVVM

The app uses the Model-View-ViewModel (MVVM) architecture pattern:

- **Model**: Represents the data and business logic.
- **View**: The UI components built using Jetpack Compose.
- **ViewModel**: Manages the UI-related data and handles user interactions.

# Functionality

The Simple Weather App provides the following functionalities:

- **Search Weather by City**: Users can search for weather information by entering the name of a city.
- **View Current Weather**: Displays current weather conditions, including temperature.
- **Error Handling**: Provides appropriate error messages for invalid city names or network issues.
