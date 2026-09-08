# LoginFormSample - Appium Test Automation Suite

A production-ready Android test automation framework using **Appium 2.x**, **Java**, and **JUnit 4** implementing the **Page Object Model (POM)** design pattern. This project validates authentication behavior for the `LoginFormSample` Android application across positive and negative test scenarios.

---

## 1. Project Overview

The **LoginFormSample Test Automation Suite** is designed to provide reliable, automated end-to-end regression testing for the `LoginFormSample` native Android app. 

### Key Highlights
* **Page Object Model (POM)**: Encapsulates UI locators and user interaction logic inside `LoginPage.java`, separating test assertions from DOM/UI mechanics.
* **Explicit Waits**: Employs `WebDriverWait` and `ExpectedConditions` to guarantee test stability against UI timing variations across different physical devices and emulators.
* **Clean State Execution**: Utilizes `@Before` and `@After` hooks with `noReset = false` to guarantee isolated test execution for every test case.

---

## 2. Tech Stack

| Component | Technology / Library | Version / Details |
| :--- | :--- | :--- |
| **Programming Language** | Java | JDK 11 or higher |
| **Automation Engine** | Appium | 2.x Server |
| **Client Library** | Appium Java Client | `io.appium:java-client:9.2.2` |
| **Android Driver** | UiAutomator2 Driver | `appium-uiautomator2-driver` |
| **Testing Framework** | JUnit | JUnit 4 (`junit:junit:4.13.2`) |
| **Automation Tooling** | Selenium Webdriver | `selenium-java:4.20.0` |
| **IDE** | Android Studio | 2024.1+ / Ladybug / Meerkat |
| **Build Tool** | Gradle | Gradle Wrapper |

---

## 3. Prerequisites & Requirements

Before running the test suite, ensure the following tools are installed and configured on your machine:

1. **Java Development Kit (JDK 11+)**:
   * Verify installation: `java -version`
   * Ensure `JAVA_HOME` environment variable is correctly set.
2. **Node.js (v18 or higher)** & **npm**:
   * Verify installation: `node -v` and `npm -v`
3. **Appium 2.x Server**:
   * Install globally:
     ```bash
     npm install -g appium
     ```
   * Verify installation: `appium -v`
4. **Appium UiAutomator2 Driver**:
   * Install driver:
     ```bash
     appium driver install uiautomator2
     ```
   * Verify installed drivers: `appium driver list`
5. **Android Studio & Android SDK**:
   * Ensure `ANDROID_HOME` (or `ANDROID_SDK_ROOT`) environment variable is configured and pointing to your Android SDK folder.
   * Add `platform-tools` and `emulator` to your system `PATH`:
     * **Windows**: `%ANDROID_HOME%\platform-tools`, `%ANDROID_HOME%\emulator`
     * **macOS/Linux**: `$ANDROID_HOME/platform-tools`, `$ANDROID_HOME/emulator`
   * Verify ADB connection: `adb devices`

---

## 4. Test Scenarios Covered

The test suite validates login functionality through three automated test cases located in `LoginFormAppiumTest.java`:

| Test ID | Test Scenario | Inputs | Expected Result |
| :--- | :--- | :--- | :--- |
| **TC1** | **Correct Login** | **Username**: `validUser` <br> **Password**: `securePassword123` | ✅ Welcome message (`tvWelcome`) is visible containing the username (`Welcome, validUser!`). Error message is hidden. |
| **TC2** | **Wrong Username** | **Username**: `wrongUser` <br> **Password**: `securePassword123` | ❌ Error message (`tvErrorMessage`) is displayed with text `"Invalid username or password."`. Welcome message is hidden. |
| **TC3** | **Wrong Password** | **Username**: `validUser` <br> **Password**: `wrongPassword` | ❌ Error message (`tvErrorMessage`) is displayed with text `"Invalid username or password."`. Welcome message is hidden. |

---

## 5. Setup & Installation Instructions

### Step 1: Clone the Repository
```bash
git clone https://github.com/your-username/LoginFormSample.git
cd LoginFormSample
```

### Step 2: Configure Environment Variables
Ensure the following variables are defined in your environment:

* **Windows**:
  ```cmd
  setx JAVA_HOME "C:\Program Files\Java\jdk-17"
  setx ANDROID_HOME "C:\Users\<YourUsername>\AppData\Local\Android\Sdk"
  setx PATH "%PATH%;%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools"
  ```
* **macOS / Linux (`~/.zshrc` or `~/.bashrc`)**:
  ```bash
  export JAVA_HOME=$(/usr/libexec/java_home -v 17)
  export ANDROID_HOME=$HOME/Library/Android/sdk
  export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
  ```

### Step 3: Install the App on Device or Build the APK
1. Launch an Android Emulator via Android Studio Device Manager, or connect a physical Android device with USB Debugging enabled.
2. Verify the device is detected:
   ```bash
   adb devices
   ```
3. Build and install the Debug APK onto the target device:
   ```bash
   ./gradlew assembleDebug
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

---

## 6. How to Run the Tests

### Option A: Running Tests via Gradle CLI

1. **Start the Appium Server**:
   Open a terminal window and start the Appium server:
   ```bash
   appium
   ```
   *By default, the server runs on `http://127.0.0.1:4723/`.*

2. **Execute the Test Suite**:
   Open a second terminal window in the project root directory and execute:
   ```bash
   ./gradlew test
   ```
   *(On Windows Command Prompt, use `gradlew.bat test`)*

3. **Run a Specific Test Class**:
   ```bash
   ./gradlew test --tests "com.example.loginformsample.LoginFormAppiumTest"
   ```

4. **Run a Specific Test Method**:
   ```bash
   ./gradlew test --tests "com.example.loginformsample.LoginFormAppiumTest.tc1_correctLogin_validUsernameAndValidPassword"
   ```

---

### Option B: Running Tests inside Android Studio

1. **Start Appium Server**:
   Ensure `appium` is running in your terminal.
2. **Open Project**:
   Open Android Studio and load the `LoginFormSample` project directory.
3. **Execute Test File**:
   * Navigate to `app/src/test/java/com/example/loginformsample/LoginFormAppiumTest.java`.
   * Right-click the class name or click the green **Run** arrow icon next to `public class LoginFormAppiumTest`.
   * Select **Run 'LoginFormAppiumTest'**.
4. **View Results**:
   The JUnit test execution results, assertion reports, and logs will display directly in Android Studio's **Run / Test** tool window.

---

## 📁 Project Structure

```
LoginFormSample/
├── app/
│   ├── build.gradle                   # Module-level Gradle configuration & dependencies
│   └── src/
│       ├── main/                      # Application Source Code
│       │   ├── java/com/example/loginformsample/
│       │   │   └── MainActivity.java  # Main Login Activity logic
│       │   └── res/                   # Layouts, Drawables, Themes, and Strings
│       │       └── layout/activity_main.xml
│       └── test/                      # Test Automation Source Code
│           └── java/com/example/loginformsample/
│               ├── LoginPage.java      # Page Object Model class for Login Screen
│               └── LoginFormAppiumTest.java # Appium JUnit test execution suite
├── build.gradle                       # Root-level Gradle build file
├── settings.gradle                    # Project settings & module inclusions
└── README.md                          # Framework documentation
```

---

## 🛡️ License & Acknowledgments
Created as a clean, standardized template for Appium Android UI automation using Java and JUnit.
