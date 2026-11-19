# StudentDirectory

A simple Android app for browsing and searching a sample student directory. It uses a dark Black & Champagne theme and displays student details in elegant cards.

## Features

- Scrollable student list
- Search by student name, course, or email
- Tap a student card to view their course and email
- Black and Champagne color theme
- Java and XML layouts with RecyclerView

## Built With

- Java
- Android SDK
- AndroidX RecyclerView
- Material Components
- ConstraintLayout

## Requirements

- Android Studio
- Android SDK 24 or later

## Run the App

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Select an emulator or connected Android device.
5. Click **Run**.

## Project Structure

```text
StudentDirectory/
├── app/
│   └── src/main/
│       ├── java/com/example/studentdirectory/
│       │   ├── MainActivity.java
│       │   ├── Student.java
│       │   └── StudentAdapter.java
│       └── res/
│           ├── drawable/
│           ├── layout/
│           └── values/
└── README.md
```

## Sample Data

The app currently uses eight sample student records stored locally in `MainActivity.java`. It does not connect to an online database.

## Repository

[StudentDirectory on GitHub](https://github.com/SukunFatema/StudentDirectory)
