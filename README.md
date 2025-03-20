# Invoice Manager App
This application allows users to add, edit, delete invoices, and capture photos, as well as include additional information such as date, title, and total amount.

## Features

- Add new invoices with title, date, and total amount.
- Edit existing invoices.
- Delete invoices.
- Capture photos directly from the camera or select images from the gallery to associate with invoices.
- Manage invoices using modern architectural patterns.

## Technologies and Patterns Used

This project was developed using the best practices recommended by Google for native Android development:

- **Kotlin**: The main language used in the project.
- **MVVM**: Architecture to separate business logic from the user interface.
- **Jetpack Compose**: Modern framework for building native user interfaces.
- **Room**: Database library for local persistence using SQLite.
- **Hilt**: Simplified dependency injection.
- **StateFlow**: To manage UI state reactively.
- **Coroutines**: For managing asynchronous operations.
- **UseCases**: Separation of business logic into use cases.
- **Native Camera and Gallery Libraries**: To capture and select images.

## Project Structure

The project is organized following the MVVM pattern, clearly separating the presentation, domain, and data layers. `ViewModel` is used to manage UI state, and `UseCases` are used to isolate business logic. Room is used as the local database, and Jetpack Compose is used for UI construction.

## How to Run the Project

1. Clone the repository.

2. Open the project in Android Studio.

3. Connect a device or start an emulator.

4. Run the application.
