package com.example.loginformsample;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model (POM) representing the Login Screen of the LoginFormSample app.
 *
 * <p>Encapsulates all locators and interaction logic for the Login Form components.
 * Uses explicit waits ({@link WebDriverWait}) for every element interaction to ensure
 * stability across emulators and physical devices with varying performance.</p>
 *
 * <p>Fluent API: setter methods return {@code this} so calls can be chained, e.g.
 * {@code loginPage.enterUsername("u").enterPassword("p").clickLogin();}</p>
 */
public class LoginPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // ── Locators (fully-qualified resource-id) ──────────────────────────────────
    private static final By USERNAME_FIELD =
            AppiumBy.id("com.example.loginformsample:id/etUsername");
    private static final By PASSWORD_FIELD =
            AppiumBy.id("com.example.loginformsample:id/etPassword");
    private static final By LOGIN_BUTTON =
            AppiumBy.id("com.example.loginformsample:id/btnLogin");
    private static final By ERROR_MESSAGE =
            AppiumBy.id("com.example.loginformsample:id/tvErrorMessage");
    private static final By WELCOME_MESSAGE =
            AppiumBy.id("com.example.loginformsample:id/tvWelcome");

    /** Default explicit-wait timeout applied to every element lookup. */
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    // ── Constructor ─────────────────────────────────────────────────────────────

    /**
     * Creates a new {@code LoginPage} bound to the supplied driver.
     *
     * @param driver an active {@link AndroidDriver} session
     */
    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    // ── Action Methods ──────────────────────────────────────────────────────────

    /**
     * Clears any existing text and types the given username into the Username field.
     *
     * @param username the value to enter
     * @return this page instance (fluent)
     */
    public LoginPage enterUsername(String username) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
        field.clear();
        field.sendKeys(username);
        return this;
    }

    /**
     * Clears any existing text and types the given password into the Password field.
     *
     * @param password the value to enter
     * @return this page instance (fluent)
     */
    public LoginPage enterPassword(String password) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
        field.clear();
        field.sendKeys(password);
        return this;
    }

    /**
     * Waits for the Login button to become clickable, then taps it.
     * Also hides the soft keyboard beforehand so it does not obscure the button.
     */
    public void clickLogin() {
        // Dismiss the soft keyboard so it does not cover the Login button
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // Keyboard may already be hidden – safe to ignore
        }

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        button.click();
    }

    /**
     * Convenience method that fills in both fields and submits the form.
     *
     * @param username the username to enter
     * @param password the password to enter
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // ── Validation / Query Methods ──────────────────────────────────────────────

    /**
     * @return {@code true} if the error message {@code tvErrorMessage} is visible
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * @return the text shown inside the error message TextView
     * @throws TimeoutException if the element does not become visible within the timeout
     */
    public String getErrorMessageText() {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        return element.getText();
    }

    /**
     * @return {@code true} if the welcome / success message {@code tvWelcome} is visible
     */
    public boolean isWelcomeMessageDisplayed() {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(WELCOME_MESSAGE));
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * @return the text shown inside the welcome TextView
     * @throws TimeoutException if the element does not become visible within the timeout
     */
    public String getWelcomeMessageText() {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(WELCOME_MESSAGE));
        return element.getText();
    }

    /**
     * @return {@code true} if the welcome message is <em>not</em> visible (hidden or gone)
     */
    public boolean isWelcomeMessageHidden() {
        try {
            // Use a short wait – we expect the element to stay hidden
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(WELCOME_MESSAGE));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * @return {@code true} if the error message is <em>not</em> visible (hidden or gone)
     */
    public boolean isErrorMessageHidden() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(ERROR_MESSAGE));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
