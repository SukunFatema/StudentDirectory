# StudentDirectory

A polished Android student directory built with Java and XML. The app uses a premium Black & Champagne theme, RecyclerView cards, search, and local persistence with Room.

## Features

- Browse a scrollable list of students.
- Search by student name, course, or email.
- Tap a student card to view the student's course and email.
- Add a student with the floating **+** button.
- Save student records in a local Room database (SQLite).
- Reload saved records automatically whenever the app launches.
- Keep database reads and writes off the main thread with a single-threaded Java ExecutorService.
- Seed eight sample records only when the database is empty.

## Technology

- Java
- XML layouts
- AndroidX Room
- RecyclerView and ViewHolder pattern
- Material Components
- ConstraintLayout
- Java ExecutorService for background database work

## Requirements

- Android Studio
- Android SDK 36 (the project compiles against SDK 36)
- Android device or emulator running Android 7.0 (API 24) or later
- Internet connection for the first Gradle dependency sync

## Run the App

1. Clone or download this repository.
2. Open the project folder in Android Studio.
3. Wait for Gradle sync to finish and allow Android Studio to download dependencies.
4. Select an emulator or connect an Android device (USB debugging enabled).
5. Click **Run**.

## Test Local Persistence

1. Launch the app. The first launch inserts eight sample students into the Room database.
2. Tap the **+** button and enter a name, course, and email address.
3. Tap **Save student**. The new record appears in the directory.
4. Close the app completely and launch it again.
5. Confirm that the student you added is still present. This verifies that the app reloads records from local storage instead of recreating the list on every launch.

The database is stored locally on the device in a Room-managed SQLite file named `student_directory_database`. App data is removed if the app is uninstalled or its storage is cleared.

## Background Work and Responsiveness

All Room operations (counting records, inserting sample records, reading students, and saving a new student) run on a dedicated `ExecutorService`. Results are posted back to the UI thread only when the RecyclerView needs to be refreshed. Room's main-thread database access is not enabled.

## Project Structure

```text
StudentDirectory/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── java/com/example/studentdirectory/
│       │   ├── MainActivity.java
│       │   ├── Student.java
│       │   ├── StudentAdapter.java
│       │   ├── StudentEntity.java
│       │   ├── StudentDao.java
│       │   └── StudentDatabase.java
│       └── res/
│           ├── drawable/
│           ├── layout/
│           └── values/
├── gradle/
│   └── libs.versions.toml
└── README.md
```

## Repository

[StudentDirectory on GitHub](https://github.com/SukunFatema/StudentDirectory)
