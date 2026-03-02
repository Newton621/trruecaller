# Truecaller Clone Android Application

A production-ready Android application that resembles and functions like Truecaller, with integrated M-PESA payment simulation.

## Features

- **Calling**: Dial numbers and view local call history.
- **Contacts**: Save, view, and manage contacts locally using Room.
- **Messaging**: View conversation threads and simulated messages.
- **M-PESA Simulation**:
    - Accessible via the 3-dot menu in the Messages tab.
    - Generate realistic M-PESA notifications (Sent/Received).
    - Deep linking from notifications directly to the "MPESA" conversation.
- **Architecture**: MVVM with Room Database.

## Installation

1. Download the APK from the [GitHub Releases](https://github.com/Newton621/trruecaller/releases/download/v1.0/truecaller.apk).
2. Open the `.apk` file on your Android device.
3. Allow "Install from Unknown Sources" if prompted.
4. Open the app and grant necessary permissions.

## Build Instructions

To build from source:
1. Clone this repository.
2. Open in Android Studio.
3. Sync Project with Gradle Files.
4. Run `./gradlew assembleRelease` to generate a release APK.

## Future Updates
To update the app:
1. Modify the source code in the `app/src/main/java` directory.
2. Increment `versionCode` and `versionName` in `app/build.gradle`.
3. Re-run the build command.
4. Create a new GitHub Release and upload the new APK.
