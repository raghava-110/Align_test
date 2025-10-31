package com.selenium.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class AlignTestAutomation {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    // =================================================================================
    // LOCATORS - Extracted from provided XPATH MAPPINGS
    // =================================================================================
    private static class Locators {
        // --- Login Page ---
        private static final String[] EMAIL_INPUT = new String[]{"//input[@id='usernameField']", "//input[@name='username']", "//input[@type='email']", "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']"};
        private static final String[] CONTINUE_BUTTON = new String[]{"//button[contains(., 'Continue')]", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[contains(@class,'icon')]"};
        private static final String[] PASSWORD_INPUT = new String[]{"//input[@id='passwordField']", "//input[@name='password']", "//input[@type='password']", "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']"};
        private static final String[] LOGIN_BUTTON = new String[]{"//button[contains(., 'Login')]", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[contains(@class,'icon')]"};

        // --- Company Selection ---
        // NOTE: A dynamic XPath is required here, constructed in the test method.
        // Example: "//div[contains(@class, 'company-list-item')]//span[text()='{COMPANY_NAME}']"

        // --- Navigation ---
        private static final String[] NAVIGATION_HEADER_DROPDOWN_INPUT = new String[]{"//input[@id='navigation-header-dropdown']", "//input[contains(@data-bind,'value: headerNavigation')]", "//input[contains(@class,'header-dropdown')]"};
        // NOTE: Specific navigation links like 'Metrics' or 'My Dashboard' are selected from a dropdown.
        // A dynamic XPath will be used: "//li[normalize-space()='{MENU_ITEM}']"

        // --- Metrics Page ---
        private static final String[] ADD_METRIC_BUTTON = new String[]{"//button[contains(., 'Add Metric')]", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[contains(@class,'icon')]"};
        private static final String[] METRIC_NAME_INPUT = new String[]{"//input[@placeholder='Name of the Metric']", "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']"};
        private static final String[] VALUE_SOURCE_DROPDOWN = new String[]{"//label[contains(text(),'Value Source')]/following-sibling::span//span[contains(@class, 'k-select')]"};
        private static final String[] SAVE_BUTTON = new String[]{"//button[contains(., 'Save')]", "//button[@id='saveButton']", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[contains(@class,'icon')]"};

        // --- Formula Builder ---
        private static final String[] FORMULA_BUILDER_SEARCH_METRIC_INPUT = new String[]{"//input[@placeholder='Name or Owner']", "//input[@data-bind='events: { keyup: calculated.onFilterChange }']"};
        private static final String[] FORMULA_VALIDATE_BUTTON = new String[]{"//span[@title='Validate and Calculate']"};
        // NOTE: Operator buttons require dynamic XPath, e.g., "//div[@class='operators']//button[text()='{OPERATOR}']"

        // --- Dashboard ---
        private static final String[] EDIT_KPI_ICON = new String[]{"//span[@title='Edit Kpi']", "//span[contains(@class,'icon') and contains(@class, 'ico-edit') and @data-bind='click: clickEditKpis']"};
        // NOTE: Metric card locators are dynamic and constructed in the test methods.

        // --- KPI Selection Modal ---
        private static final String[] KPI_MODAL_SAVE_BUTTON = new String[]{"//div[contains(@class, 'k-window-content')]//button[contains(., 'Save')]"};
        // NOTE: Metrics in the modal are found dynamically by text.

        // --- Edit Metric Page (Targets) ---
        private static final String[] TARGET_TAB = new String[]{"//a[normalize-space()='Target']"};
        private static final String[] CUSTOM_RADIO_BUTTON = new String[]{"//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']", "//input[@name='target-type' and @value='custom']"};
        private static final String[] TIME_BASED_RADIO_BUTTON = new String[]{"//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']", "//input[@name='target-type' and @value='time-based']"};
        private static final String[] START_INPUT = new String[]{"//label[normalize-space()='Start']/following-sibling::input", "//input[@data-bind='value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }']"};
        private static final String[] TARGET_INPUT = new String[]{"//label[normalize-space()='Target']/following-sibling::input", "//input[@data-bind='value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }']"};
        // NOTE: Custom target level inputs are not in mappings and will be located by index/placeholder.
        private static final String[] CUSTOM_TARGET_LEVEL_INPUTS = new String[]{"//div[contains(@class, 'custom-target-levels')]//input"};
    }

    // =================================================================================
    // CONFIGURATION - Extracted from provided TEST DATA
    // =================================================================================
    private static class Config {
        private static final String BASE_URL = "https://app.align.com/login"; // Assumed from test steps

        // --- Test Data ---
        public static final String EMAIL = "[User email address]";
        public static final String PASSWORD = "[User password]";
        public static final String COMPANY_NAME = "[Company to be selected]";
        public static final String METRIC_NAME_TC001 = "[Name of the metric to be created]";
        public static final String METRIC_NAME_TC002 = "[Name of the metric to be created]";
        public static final String METRIC_NAME_TC003 = "[Name of the metric to be created]";
        public static final String VALUE_SOURCE = "Formula";
        public static final String FIRST_METRIC = "[Name of first metric to use in formula]";
        public static final String SECOND_METRIC = "[Name of second metric to use in formula]";
        public static final String OPERATOR_TC001 = "+";
        public static final String OPERATOR_TC002 = "/";
        public static final String OPERATOR_TC003 = "*";
        public static final String TARGET_TYPE_CUSTOM = "Custom";
        public static final String TARGET_TYPE_TIME_BASED = "Time-Based";
        public static final String CUSTOM_TARGET_VALUES = "[4 values from highest to lowest - Level 1, Level 2, Level 3, Level 4]";
        public static final String TIME_BASED_START_VALUE = "[Starting value]";
        public static final String TIME_BASED_TARGET_VALUE = "[Target value to achieve]";

        // --- Timing Constants ---
        public static final int EXPLICIT_WAIT_SEC = 30;
        public static final int TYPE_DELAY_MS = 100;
        public static final int ACTION_PAUSE_MS = 1500;
        public static final int PAGE_SETTLE_MS = 2000;
    }

    // =================================================================================
    // HELPER METHODS
    // =================================================================================

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return driver.findElement(By.xpath(xpath));
            } catch (NoSuchElementException e) {
                // Ignore and try the next one
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided XPaths.");
    }

    private void jsClick(WebElement element) {
        try {
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            jsExecutor.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            log("JavaScript click failed: " + e.getMessage());
        }
    }

    private void typeSlowly(WebElement element, String text) {
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(Config.TYPE_DELAY_MS);
        }
    }

    private WebElement waitVisible(String[] xpaths, String elementName) {
        log("Waiting for " + elementName + " to be visible...");
        for (String xpath : xpaths) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            } catch (Exception e) {
                // Continue to next xpath
            }
        }
        throw new RuntimeException(elementName + " was not visible with any provided locators.");
    }

    private WebElement waitClickable(String[] xpaths, String elementName) {
        log("Waiting for " + elementName + " to be clickable...");
        for (String xpath : xpaths) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            } catch (Exception e) {
                // Continue to next xpath
            }
        }
        throw new RuntimeException(elementName + " was not clickable with any provided locators.");
    }

    private void clickElement(String[] xpaths, String elementName) {
        try {
            WebElement element = waitClickable(xpaths, elementName);
            element.click();
            log("Clicked on " + elementName);
        } catch (Exception e) {
            log("Standard click failed for " + elementName + ". Trying JavaScript click.");
            try {
                WebElement element = findElementWithFallbacks(xpaths);
                jsClick(element);
                log("Clicked on " + elementName + " using JavaScript.");
            } catch (Exception jsE) {
                handleTestFailure("Failed to click " + elementName, jsE);
            }
        }
    }

    private void sendKeysToElement(String[] xpaths, String text, String elementName) {
        WebElement element = waitVisible(xpaths, elementName);
        element.clear();
        typeSlowly(element, text);
        log("Entered '" + text + "' into " + elementName);
    }

    private void assertElementVisible(String[] xpaths, String elementName) {
        try {
            waitVisible(xpaths, elementName);
            log("ASSERT PASSED: " + elementName + " is visible.");
        } catch (Exception e) {
            handleTestFailure("ASSERT FAILED: " + elementName + " is not visible.", e);
        }
    }

    private void waitForPageLoad() {
        log("Waiting for page to load completely...");
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        sleep(Config.PAGE_SETTLE_MS);
    }

    private void assertUrlContains(String expectedUrlPart) {
        try {
            wait.until(ExpectedConditions.urlContains(expectedUrlPart));
            log("ASSERT PASSED: URL contains '" + expectedUrlPart + "'.");
        } catch (Exception e) {
            handleTestFailure("ASSERT FAILED: URL does not contain '" + expectedUrlPart + "'. Current URL: " + driver.getCurrentUrl(), e);
        }
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void takeScreenshot(String testName) {
        try {
            File srcFile = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = "screenshots/" + testName + "_" + timestamp + ".png";
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(srcFile.toPath(), Paths.get(screenshotPath));
            log("Screenshot saved to: " + screenshotPath);
        } catch (Exception e) {
            log("Failed to take screenshot: " + e.getMessage());
        }
    }

    private void log(String message) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - " + message);
    }

    private void logStart(String testId, String testName) {
        log("================================================================================");
        log("STARTING TEST: [" + testId + "] " + testName);
        log("================================================================================");
    }

    private void logPass(String testId) {
        log("================================================================================");
        log("PASSED TEST: [" + testId + "]");
        log("================================================================================");
    }

    private void handleTestFailure(String message, Exception e) {
        log("!!!!!!!!!! TEST FAILED !!!!!!!!!!");
        log(message);
        if (e != null) {
            e.printStackTrace();
        }
        if (driver != null) {
            takeScreenshot("FAILURE");
        }
        throw new AssertionError(message, e);
    }

    // =================================================================================
    // TEST METHODS
    // =================================================================================

    public void runTC001() {
        String testId = "TC001";
        logStart(testId, "E2E - Create Formula Metric and Configure 4-Level Custom Target");

        try {
            // Step 1-4: Login and select company
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);

            // Step 5: Verify dashboard
            assertUrlContains("/dashboard");
            log("Successfully navigated to the main dashboard.");

            // Step 6: Navigate to Metrics page
            navigateTo("Metrics");

            // Step 7-16: Create a formula metric
            createFormulaMetric(Config.METRIC_NAME_TC001, Config.FIRST_METRIC, Config.OPERATOR_TC001, Config.SECOND_METRIC);

            // Step 17-21: Add the new metric to the dashboard
            addMetricToDashboard(Config.METRIC_NAME_TC001);

            // Step 22-29: Edit the metric and configure a 4-level custom target
            configureCustomTarget(Config.METRIC_NAME_TC001, Config.CUSTOM_TARGET_VALUES);

            // Step 30-31: Final verification on the dashboard
            log("Verifying metric card on dashboard for custom target indicators...");
            WebElement metricCard = findMetricCardOnDashboard(Config.METRIC_NAME_TC001);
            // This assertion would check for a specific class or element indicating a custom target is active
            assert metricCard.findElement(By.xpath(".//*[contains(@class, 'target-visualization')]")).isDisplayed();
            log("Final verification successful. Metric card displays custom target visualization.");

            logPass(testId);
        } catch (Exception e) {
            handleTestFailure("Test " + testId + " failed.", e);
        }
    }

    public void runTC002() {
        String testId = "TC002";
        logStart(testId, "E2E - Create Formula Metric and Configure Time-Based Target");

        try {
            // Step 1-4: Login and select company
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);

            // Step 5: Navigate to Metrics page
            navigateTo("Metrics");

            // Step 6-13: Create a formula metric
            createFormulaMetric(Config.METRIC_NAME_TC002, Config.FIRST_METRIC, Config.OPERATOR_TC002, Config.SECOND_METRIC);

            // Step 14-17: Add the new metric to the dashboard
            addMetricToDashboard(Config.METRIC_NAME_TC002);

            // Step 18-24: Edit the metric and configure a time-based target
            configureTimeBasedTarget(Config.METRIC_NAME_TC002, Config.TIME_BASED_START_VALUE, Config.TIME_BASED_TARGET_VALUE);

            // Step 25-26: Final verification on the dashboard
            log("Verifying metric card on dashboard for time-based target indicators...");
            WebElement metricCard = findMetricCardOnDashboard(Config.METRIC_NAME_TC002);
            assert metricCard.findElement(By.xpath(".//*[contains(text(), 'Start')]")).isDisplayed();
            assert metricCard.findElement(By.xpath(".//*[contains(text(), 'Target')]")).isDisplayed();
            log("Final verification successful. Metric card displays time-based target visualization.");

            logPass(testId);
        } catch (Exception e) {
            handleTestFailure("Test " + testId + " failed.", e);
        }
    }

    public void runTC003() {
        String testId = "TC003";
        logStart(testId, "Functional - Verify Formula Builder UI and Formula Construction");

        try {
            // Step 1-2: Login and select company
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);

            // Step 3: Navigate to Metrics page
            navigateTo("Metrics");

            // Step 4: Click 'Add Metric'
            clickElement(Locators.ADD_METRIC_BUTTON, "'Add Metric' button");
            waitForPageLoad();

            // Step 5: Enter metric name
            sendKeysToElement(Locators.METRIC_NAME_INPUT, Config.METRIC_NAME_TC003, "'Name' input field");

            // Step 6: Select 'Formula' from 'Value Source'
            clickElement(Locators.VALUE_SOURCE_DROPDOWN, "'Value Source' dropdown");
            sleep(500);
            WebElement formulaOption = driver.findElement(By.xpath("//li[normalize-space()='" + Config.VALUE_SOURCE + "']"));
            jsClick(formulaOption);
            log("Selected '" + Config.VALUE_SOURCE + "' from dropdown.");
            sleep(Config.ACTION_PAUSE_MS);

            // Step 7: Verify Formula Builder components
            log("Verifying components of the Formula Builder...");
            assertElementVisible(Locators.FORMULA_BUILDER_SEARCH_METRIC_INPUT, "'Search Metric' input field");
            assert driver.findElement(By.xpath("//button[text()='+']")).isDisplayed();
            assert driver.findElement(By.xpath("//button[text()='-']")).isDisplayed();
            assert driver.findElement(By.xpath("//button[text()='/']")).isDisplayed();
            assert driver.findElement(By.xpath("//button[text()='*']")).isDisplayed();
            log("Formula Builder UI components are visible.");

            // Step 8-12: Construct and verify the formula
            buildFormula(Config.FIRST_METRIC, Config.OPERATOR_TC003, Config.SECOND_METRIC);
            log("Formula construction and verification successful.");

            logPass(testId);
        } catch (Exception e) {
            handleTestFailure("Test " + testId + " failed.", e);
        }
    }

    // =================================================================================
    // REUSABLE BUSINESS LOGIC
    // =================================================================================

    private void loginAndSelectCompany(String email, String password, String companyName) {
        log("Navigating to application URL: " + Config.BASE_URL);
        driver.get(Config.BASE_URL);
        waitForPageLoad();
        assertElementVisible(Locators.EMAIL_INPUT, "Email input field");

        sendKeysToElement(Locators.EMAIL_INPUT, email, "Email input field");
        clickElement(Locators.CONTINUE_BUTTON, "'Continue' button");

        sendKeysToElement(Locators.PASSWORD_INPUT, password, "Password input field");
        clickElement(Locators.LOGIN_BUTTON, "'Login' button");
        waitForPageLoad();

        log("Locating and clicking on company: " + companyName);
        String companyXpath = String.format("//*[contains(@class, 'company-list-item')]//*[text()='%s']", companyName);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(companyXpath))).click();
        waitForPageLoad();
    }

    private void navigateTo(String menuItem) {
        log("Navigating to '" + menuItem + "' page...");
        clickElement(Locators.NAVIGATION_HEADER_DROPDOWN_INPUT, "Navigation dropdown");
        sleep(500);
        String menuItemXpath = String.format("//ul[contains(@id, 'navigation-header-dropdown')]//li[normalize-space()='%s']", menuItem);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(menuItemXpath))).click();
        waitForPageLoad();
        log("Successfully navigated to '" + menuItem + "' page.");
    }

    private void buildFormula(String firstMetric, String operator, String secondMetric) {
        // Select first metric
        sendKeysToElement(Locators.FORMULA_BUILDER_SEARCH_METRIC_INPUT, firstMetric, "'Search Metric' field");
        sleep(Config.ACTION_PAUSE_MS);
        WebElement firstMetricResult = driver.findElement(By.xpath("//li[contains(., '" + firstMetric + "')]"));
        jsClick(firstMetricResult);
        log("Selected first metric: " + firstMetric);

        // Select operator
        String operatorXpath = String.format("//div[contains(@class, 'operators')]//button[text()='%s']", operator);
        driver.findElement(By.xpath(operatorXpath)).click();
        log("Clicked operator: " + operator);

        // Select second metric
        sendKeysToElement(Locators.FORMULA_BUILDER_SEARCH_METRIC_INPUT, secondMetric, "'Search Metric' field");
        sleep(Config.ACTION_PAUSE_MS);
        WebElement secondMetricResult = driver.findElement(By.xpath("//li[contains(., '" + secondMetric + "')]"));
        jsClick(secondMetricResult);
        log("Selected second metric: " + secondMetric);

        // Verify and confirm formula
        assertElementVisible(Locators.FORMULA_VALIDATE_BUTTON, "Green checkmark (validate formula) button");
        clickElement(Locators.FORMULA_VALIDATE_BUTTON, "Green checkmark button");
        log("Formula confirmed.");
    }

    private void createFormulaMetric(String metricName, String firstMetric, String operator, String secondMetric) {
        clickElement(Locators.ADD_METRIC_BUTTON, "'Add Metric' button");
        waitForPageLoad();
        sendKeysToElement(Locators.METRIC_NAME_INPUT, metricName, "'Name' input field");

        clickElement(Locators.VALUE_SOURCE_DROPDOWN, "'Value Source' dropdown");
        sleep(500);
        WebElement formulaOption = driver.findElement(By.xpath("//li[normalize-space()='" + Config.VALUE_SOURCE + "']"));
        jsClick(formulaOption);
        log("Selected '" + Config.VALUE_SOURCE + "' from dropdown.");
        sleep(Config.ACTION_PAUSE_MS);

        buildFormula(firstMetric, operator, secondMetric);

        clickElement(Locators.SAVE_BUTTON, "'Save' button");
        waitForPageLoad();
        log("Metric '" + metricName + "' created successfully.");
    }

    private void addMetricToDashboard(String metricName) {
        navigateTo("My Dashboard");
        clickElement(Locators.EDIT_KPI_ICON, "'Edit KPI' icon");
        sleep(Config.ACTION_PAUSE_MS);

        log("Locating metric '" + metricName + "' in the KPI selection modal...");
        String metricInModalXpath = String.format("//div[contains(@class, 'available-metrics')]//div[text()='%s']", metricName);
        WebElement metricElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(metricInModalXpath)));
        metricElement.click();
        log("Added metric '" + metricName + "' to dashboard KPIs.");

        clickElement(Locators.KPI_MODAL_SAVE_BUTTON, "Modal 'Save' button");
        waitForPageLoad();
        log("Dashboard KPI configuration saved.");
    }

    private WebElement findMetricCardOnDashboard(String metricName) {
        log("Locating metric card for '" + metricName + "' on the dashboard...");
        String cardXpath = String.format("//div[contains(@class, 'kpi-card')][.//span[text()='%s']]", metricName);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cardXpath)));
    }

    private void openMetricCardMenuAndClickEdit(String metricName) {
        WebElement metricCard = findMetricCardOnDashboard(metricName);
        new Actions(driver).moveToElement(metricCard).perform();
        log("Hovered over metric card.");
        sleep(500);

        WebElement threeDotMenu = metricCard.findElement(By.xpath(".//*[contains(@class, 'ico-threeDots')]"));
        wait.until(ExpectedConditions.elementToBeClickable(threeDotMenu)).click();
        log("Clicked three-dot menu.");
        sleep(Config.ACTION_PAUSE_MS);

        WebElement editOption = driver.findElement(By.xpath("//ul[contains(@class, 'k-menu-group')]//li[.//span[text()='Edit']]"));
        wait.until(ExpectedConditions.elementToBeClickable(editOption)).click();
        log("Clicked 'Edit' option.");
        waitForPageLoad();
    }

    private void configureCustomTarget(String metricName, String customTargetValues) {
        openMetricCardMenuAndClickEdit(metricName);
        clickElement(Locators.TARGET_TAB, "'Target' tab");
        sleep(Config.ACTION_PAUSE_MS);

        clickElement(Locators.CUSTOM_RADIO_BUTTON, "'Custom' radio button");
        log("Selected 'Custom' target type.");
        sleep(Config.ACTION_PAUSE_MS);

        String[] values = customTargetValues.replaceAll("[\\[\\]]", "").split(",")[0].split(" ");
        List<WebElement> targetInputs = driver.findElements(By.xpath(Locators.CUSTOM_TARGET_LEVEL_INPUTS[0]));
        if (targetInputs.size() < 4) {
            handleTestFailure("Expected 4 custom target input fields, but found " + targetInputs.size(), null);
        }

        for (int i = 0; i < 4; i++) {
            WebElement input = targetInputs.get(i);
            String value = values[i + 7]; // Parsing the placeholder string
            input.clear();
            input.sendKeys(value);
            log("Entered Level " + (i + 1) + " target value: " + value);
        }

        clickElement(Locators.SAVE_BUTTON, "'Save' button on Edit page");
        waitForPageLoad();
        log("Custom target configuration saved.");
    }

    private void configureTimeBasedTarget(String metricName, String startValue, String targetValue) {
        openMetricCardMenuAndClickEdit(metricName);
        clickElement(Locators.TARGET_TAB, "'Target' tab");
        sleep(Config.ACTION_PAUSE_MS);

        clickElement(Locators.TIME_BASED_RADIO_BUTTON, "'Time-Based' radio button");
        log("Selected 'Time-Based' target type.");
        sleep(Config.ACTION_PAUSE_MS);

        sendKeysToElement(Locators.START_INPUT, startValue, "'Start' input field");
        sendKeysToElement(Locators.TARGET_INPUT, targetValue, "'Target' input field");

        clickElement(Locators.SAVE_BUTTON, "'Save' button on Edit page");
        waitForPageLoad();
        log("Time-based target configuration saved.");
    }

    // =================================================================================
    // SETUP, CLEANUP, and MAIN
    // =================================================================================

    public void setUp() {
        log("Initializing WebDriver...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SEC));
        jsExecutor = (JavascriptExecutor) driver;
        log("WebDriver initialized successfully.");
    }

    public void cleanup() {
        log("Closing WebDriver...");
        if (driver != null) {
            try {
                driver.quit();
                log("WebDriver closed successfully.");
            } catch (Exception e) {
                log("Error closing WebDriver: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        AlignTestAutomation testSuite = new AlignTestAutomation();
        try {
            testSuite.setUp();
            testSuite.runTC001();
            testSuite.runTC002();
            testSuite.runTC003();
        } catch (AssertionError e) {
            System.err.println("A test assertion failed, stopping execution.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during test execution.");
            e.printStackTrace();
        } finally {
            testSuite.cleanup();
        }
    }
}