# LoginFormSample – Appium Test Suite

Android login form with automated UI tests using **Appium 2.x**, **Java**, and **JUnit 4** (Page Object Model).

---

## Test Scenarios

| Test | Inputs | Expected |
| :--- | :--- | :--- |
| **TC1** – Correct Login | `allainralphlegaspi@gmail.com` / `AllainPassword123` | ✅ Welcome message shown |
| **TC2** – Wrong Username | `wronguser@gmail.com` / `AllainPassword123` | ❌ Error message shown |
| **TC3** – Wrong Password | `allainralphlegaspi@gmail.com` / `wrongPassword` | ❌ Error message shown |

---

## Prerequisites

- JDK 11+
- Node.js + Appium 2.x (`npm install -g appium`)
- UiAutomator2 driver (`appium driver install uiautomator2`)
- Android Studio + ADB (`adb devices` to confirm device is connected)

---

## How to Run

**1. Start Appium server:**
```bash
appium
```

**2. Build & install the app:**
```bash
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

**3. Run all tests:**
```bash
./gradlew test
```

Or run inside **Android Studio** by right-clicking `LoginFormAppiumTest.java` → **Run**.
