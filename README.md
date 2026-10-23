# Smart Pantry Manager

A Java Android app that stores pantry ingredients in SQLite and suggests recipes only when every required ingredient and quantity is available.

## Technology
- Java and XML layouts in Android Studio
- SQLite via `SQLiteOpenHelper` for persistent pantry and recipe data
- RecyclerView with a custom `PantryAdapter`
- Activities and Intents for navigation
- SharedPreferences for the expiry-reminder preference

## Open and run
1. Install Android Studio with Android SDK Platform 35 and a compatible JDK (Android Studio's bundled JDK is recommended).
2. Open Android Studio and choose **Open**, then select this `SmartPantryManager` folder (the folder containing `settings.gradle`).
3. Allow Gradle sync to finish. If Android Studio offers to install a missing SDK, accept it.
4. Create an Android emulator in Device Manager, or connect an Android phone with USB debugging enabled.
5. Press **Run** and choose the device.

## Use
- Add ingredients from the pantry screen. The quantity and unit must be entered; expiry date is optional and uses `YYYY-MM-DD`.
- Tap Recipes to see strict matches. Recipes missing any ingredient or enough quantity are excluded.
- Tap a recipe to see ingredients and method.
- Edit and delete pantry rows with their respective buttons.
- Pantry data persists in the app's local SQLite database after closing and reopening the app.

## Database choice
SQLite was chosen because this is a small, offline-first, single-user app. It requires no server or network connection and supports CRUD operations on the device.

## Strict-matching notes
Ingredient names are normalized for common singular/plural forms. Quantities are compared only when the normalized units match; the app deliberately does not assume that grams equal pieces or convert between incompatible units. Duplicate pantry rows with the same normalized ingredient and unit are added together.

