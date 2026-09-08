package com.example.loginformsample;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Production-ready Appium + JUnit 4 test suite for the LoginFormSample Android app.
 *
 * <h3>Prerequisites</h3>
 * <ul>
 *   <li>Appium 2.x server running on {@code http://127.0.0.1:4723/}</li>
 *   <li>UiAutomator2 driver installed ({@code appium driver install uiautomator2})</li>
 *   <li>An Android emulator or physical device connected via ADB</li>
 *   <li>The LoginFormSample APK installed on the device, <b>or</b> uncomment
 *       {@code options.setApp(…)} in {@link #setUp()} and provide the path to the APK</li>
 * </ul>
 *
 * <h3>Test Cases Covered</h3>
 * <table>
 *   <tr><th>ID</th><th>Scenario</th><th>Username</th><th>Password</th><th>Expected</th></tr>
 *   <tr><td>TC1</td><td>Correct login</td><td>validUser</td><td>securePassword123</td><td>Welcome message visible</td></tr>
 *   <tr><td>TC2</td><td>Wrong username</td><td>wrongUser</td><td>securePassword123</td><td>Error message visible</td></tr>
 *   <tr><td>TC3</td><td>Wrong password</td><td>validUser</td><td>wrongPassword</td><td>Error message visible</td></tr>
 * </table>
 *
 * <p>The suite uses the <b>Page Object Model</b> pattern via {@link LoginPage} to keep
 * locators and interaction logic separate from test assertions.</p>
 */
public class LoginFormAppiumTest {

    // ── Constants ───────────────────────────────────────────────────────────────

    /** Appium 2.x server URL (default port). */
    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/";

    /** Valid credentials hard-coded in MainActivity's authentication logic. */
    private static final String VALID_USERNAME = "validUser";
    private static final String VALID_PASSWORD = "securePassword123";

    /** Deliberately incorrect values used to trigger error states. */
    private static final String INVALID_USERNAME = "wrongUser";
    private static final String INVALID_PASSWORD = "wrongPassword";

    /** The error string displayed by the app when credentials are wrong. */
    private static final String EXPECTED_ERROR_TEXT = "Invalid username or password.";

    // ── Fields ──────────────────────────────────────────────────────────────────

    private AndroidDriver driver;
    private LoginPage loginPage;

    // ── Setup & Teardown ────────────────────────────────────────────────────────

    /**
     * Initialises the Appium driver with UiAutomator2 options before <b>every</b>
     * test method, ensuring a clean app state for each test case.
     *
     * @throws MalformedURLException if the Appium server URL is invalid
     */
    @Before
    public void setUp() throws MalformedURLException {
        // 1. Configure UiAutomator2Options (Appium 2.x standard) ─────────────
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("Android Emulator");

        // Target application coordinates
        options.setAppPackage("com.example.loginformsample");
        options.setAppActivity(".MainActivity");

        // Set noReset = false so the app starts fresh for every test method
        options.setNoReset(false);

        // Uncomment and set the path below if the APK is not pre-installed:
        // options.setApp("C:\\path\\to\\app-debug.apk");

        // 2. Create AndroidDriver ────────────────────────────────────────────
        driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), options);

        // Baseline implicit wait (explicit waits in LoginPage take precedence)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // 3. Instantiate Page Object ─────────────────────────────────────────
        loginPage = new LoginPage(driver);
    }

    /**
     * Safely quits the driver session after <b>every</b> test method,
     * releasing the device / emulator for the next run.
     */
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ── Test Cases ──────────────────────────────────────────────────────────────

    /**
     * <b>TC1 – Correct Login</b>
     *
     * <p>Submits valid username and valid password, then verifies that:
     * <ol>
     *   <li>The welcome / success message becomes visible.</li>
     *   <li>The welcome text contains the authenticated username.</li>
     *   <li>No error message is shown.</li>
     * </ol>
     */
    @Test
    public void tc1_correctLogin_validUsernameAndValidPassword() {
        // When – submit valid credentials
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);

        // Then – welcome message is displayed with the correct username
        assertTrue("TC1: Welcome message should be displayed on successful login",
                loginPage.isWelcomeMessageDisplayed());

        String welcomeText = loginPage.getWelcomeMessageText();
        assertTrue("TC1: Welcome text should contain the username",
                welcomeText.contains(VALID_USERNAME));

        // And – no error message is present
        assertTrue("TC1: Error message should NOT be displayed on successful login",
                loginPage.isErrorMessageHidden());
    }

    /**
     * <b>TC2 – Wrong Username</b>
     *
     * <p>Submits an <em>invalid</em> username with a <em>valid</em> password, then
     * verifies that:
     * <ol>
     *   <li>The error message becomes visible.</li>
     *   <li>The error text matches the expected message.</li>
     *   <li>No welcome / success message is shown.</li>
     * </ol>
     */
    @Test
    public void tc2_wrongUser_invalidUsernameAndValidPassword() {
        // When – submit wrong username with correct password
        loginPage.login(INVALID_USERNAME, VALID_PASSWORD);

        // Then – error message is displayed
        assertTrue("TC2: Error message should be displayed for invalid username",
                loginPage.isErrorMessageDisplayed());

        assertEquals("TC2: Error message text should match",
                EXPECTED_ERROR_TEXT, loginPage.getErrorMessageText());

        // And – welcome message is NOT displayed
        assertTrue("TC2: Welcome message should NOT be displayed for invalid username",
                loginPage.isWelcomeMessageHidden());
    }

    /**
     * <b>TC3 – Wrong Password</b>
     *
     * <p>Submits a <em>valid</em> username with an <em>invalid</em> password, then
     * verifies that:
     * <ol>
     *   <li>The error message becomes visible.</li>
     *   <li>The error text matches the expected message.</li>
     *   <li>No welcome / success message is shown.</li>
     * </ol>
     */
    @Test
    public void tc3_wrongPassword_validUsernameAndInvalidPassword() {
        // When – submit correct username with wrong password
        loginPage.login(VALID_USERNAME, INVALID_PASSWORD);

        // Then – error message is displayed
        assertTrue("TC3: Error message should be displayed for invalid password",
                loginPage.isErrorMessageDisplayed());

        assertEquals("TC3: Error message text should match",
                EXPECTED_ERROR_TEXT, loginPage.getErrorMessageText());

        // And – welcome message is NOT displayed
        assertTrue("TC3: Welcome message should NOT be displayed for invalid password",
                loginPage.isWelcomeMessageHidden());
    }
}
