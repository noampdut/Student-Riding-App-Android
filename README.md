
### Android Client README

# Student Share Drive Application - Android Client

#### Project Overview
This repository contains the Android client application for the student share drive. The client connects with the server built using ASP.NET Core and MongoDB. It features real-time notifications, an AI-driven recommendation system for matching drivers and passengers, and integration with third-party APIs. 

## Features
- **Search Functionality**: Enhanced search using NLP techniques.
- **User Authentication**: Register and log in to access personalized content.
- **Real-Time Notifications**: Instant updates and alerts.
- **Recommendation System**: AI-powered driver-passenger matching.
- **Third-Party API Integration**: Connectivity with external services.

## Prerequisites
Before running the Android client, ensure you have:
- [Android Studio](https://developer.android.com/studio) (latest version)
- A configured and running instance of the server as described in the [Server-Side README](https://github.com/noampdut/Student-Share-Drive-App-Server-Side-.git)

## Setup Instructions

### Step 1: Clone the Repository
```bash
git clone https://github.com/noampdut/Student-Share-Drive-Android.git
cd student-share-drive-android
```

### Step 2: Open the Project
1. Open Android Studio.
2. Select "Open an existing project" and navigate to the cloned repository directory.

### Step 3: Sync Gradle
Allow Android Studio to sync the project with Gradle files.

### Step 4: Configure Server URL
Update the server URL in the `SettingsActivity` to point to your running server instance. For example:
```java
private static final String SERVER_URL = "http://10.0.2.2:5001/api/";
```

### Step 5: Build and Run
1. Connect an emulator or physical device.
2. Click "Run" in Android Studio to build and launch the application.

## User Credentials

### Default Admin Account
- **Username:** admin
- **Password:** admin123

### Additional Registered Users
- **Username:** student1
- **Password:** password1
- **Username:** student2
- **Password:** password2

## Additional Notes
- Ensure the server is running and accessible before using the Android client.
- The client app includes configurations for local testing. Adjust settings as needed for production environments.
- The project has been well-received, with positive feedback on its functionality and user experience.
