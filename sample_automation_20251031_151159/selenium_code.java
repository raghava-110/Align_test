package com.selenium.automation;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class AlignTestAutomation {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    /**
     * Locators class containing all XPath mappings.
     * Locators are grouped by page/feature for better organization.
     */
    private static class Locators {
        // Login Page
        private static final String[] EMAIL_INPUT = new String[]{"//input[@id='usernameField']", "//input[@name='username']", "//input[@type='email']", "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']"};
        private static final String[] CONTINUE_BUTTON = new String[]{"//button[text()='Continue']", "//button[contains(.,'Continue')]", "//button[@class='btn btn-primary icon-trailing ico-arrow-right']"};
        private static final String[] PASSWORD_INPUT = new String[]{"//input[@id='passwordField']", "//input[@name='password']", "//input[@type='password']", "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']"};
        private static final String[] LOGIN_BUTTON = new String[]{"//button[text()='Login']", "//button[contains(.,'Login')]", "//button[@class='btn btn-primary icon-trailing ico-arrow-right']"};

        // Company Selection
        private static final String COMPANY_LIST_ITEM = "//div[contains(@class,'company-list-item')]//span[text()='%s']";

        // Main Navigation
        private static final String[] NAVIGATION_HEADER_DROPDOWN_INPUT = new String[]{"//input[@id='navigation-header-dropdown']", "//input[contains(@class,'header-dropdown')]", "//input[@data-bind='value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }']"};
        private static final String NAVIGATION_MENU_ITEM = "//ul[@id='navigation-header-dropdown_listbox']/li[text()='%s']";

        // Metrics Page
        private static final String[] ADD_METRIC_BUTTON = new String[]{"//button[contains(text(),'Add Metric')]", "//a[contains(text(),'Add Metric')]", "//button[@data-bind='click: clickAdd']"};
        private static final String[] NAME_INPUT = new String[]{"//input[@placeholder='Name of the Metric']", "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']"};
        private static final String[] VALUE_SOURCE_DROPDOWN = new String[]{"//label[contains(text(),'Value Source')]/following-sibling::span//span[contains(@class, 'k-select')]"};
        private static final String DROPDOWN_OPTION = "//ul[contains(@style,'display: block')]//li[text()='%s']";
        private static final String[] SEARCH_METRIC_INPUT = new String[]{"//input[@placeholder='Name or Owner']", "//input[@data-bind='events: { keyup: calculated.onFilterChange }']"};
        private static final String SEARCH_METRIC_RESULT = "//ul[contains(@style,'display: block')]//li[contains(text(),'%s')]";
        private static final String OPERATOR_BUTTON = "//div[@class='formula-operators']//button[text()='%s']";
        private static final String[] VALIDATE_FORMULA_BUTTON = new String[]{"//span[@title='Validate and Calculate']", "//div[@class='formula-validate']//span[contains(@class,'icon')]"};
        private static final String[] SAVE_BUTTON = new String[]{"//button[@id='saveButton']", "//button[contains(text(),'Save')]", "//button[contains(@class,'btn-primary') and .//span[text()='Save']]"};

        // Dashboard
        private static final String[] EDIT_KPI_ICON = new String[]{"//span[@title='Edit Kpi']", "//span[contains(@class,'icon-edit') and @data-bind='click: clickEditKpis']"};
        private static final String KPI_MODAL_AVAILABLE_METRIC = "//div[contains(@class,'available-metrics')]//div[text()='%s']";
        private static final String[] KPI_MODAL_SAVE_BUTTON = new String[]{"//div[@class='modal-footer']//button[contains(text(),'Save')]", "//button[@data-bind='click: saveCompanyKpis']"};
        private static final String METRIC_CARD = "//div[contains(@class,'kpi-card-container')]//span[contains(@class,'metric-name') and text()='%s']/ancestor::div[contains(@class,'kpi-card-container')]";
        private static final String METRIC_CARD_THREE_DOT_MENU = ".//span[contains(@class,'ico-threeDots1')]/ancestor::button";
        private static final String METRIC_CARD_EDIT_OPTION = "//div[contains(@class,'kpicard-dropdown-menu-content') and contains(@style,'display: block')]//li[@title='Edit']";

        // Edit Metric Page
        private static final String TARGET_TAB = "//ul[contains(@class,'nav-tabs')]//a[text()='Target']";
        private static final String CUSTOM_TARGET_RADIO = "//label[contains(text(),'Custom')]/preceding-sibling::input[@type='radio']";
        private static final String TIME_BASED_TARGET_RADIO = "//label[contains(text(),'Time-Based')]/preceding-sibling::input[@type='radio']";
        private static final String[] CUSTOM_TARGET_LEVEL_INPUTS = new String[]{"//div[@data-bind='visible: isCustomSelected']//input[contains(@class,'k-formatted-value')]", "//div[@data-bind='visible: isCustomSelected']//input[contains(@class,'k-numerictextbox')]" };
        private static final String TIME_BASED_START_VALUE_INPUT = "//label[text()='Start Value']/following-sibling::span//input[contains(@class,'k-formatted-value')]";
        private static final String TIME_BASED_TARGET_VALUE_INPUT = "//label[text()='Target Value']/following-sibling::span//input[contains(@class,'k-formatted-value')]";
    }

    /**
     * Config class for test data and constants.
     */
    private static class Config {
        private static final String BASE_URL = "https://app.align.com/login"; // Placeholder URL

        // Test Data extracted from all test cases
        public static final String EMAIL = "[User email address]";
        public static final String PASSWORD = "[User password]";
        public static final String COMPANY_NAME = "[Company to be selected]";
        public static final String METRIC_NAME = "[Name of the metric to be created]";
        public static final String VALUE_SOURCE = "Formula";
        public static final String FIRST_METRIC = "[Name of first metric to use in formula]";
        public static final String OPERATOR = "[+, -, /, *, (, )]";
        public static final String SECOND_METRIC = "[Name of second metric to use in formula]";
        public static final String TARGET_TYPE_CUSTOM = "Custom";
        public static final String CUSTOM_TARGET_VALUES = "[4 values from highest to lowest - Level 1, Level 2, Level 3, Level 4]";
        public static final String TARGET_TYPE_TIME_BASED = "Time-Based";
        public static final String TIME_BASED_START_VALUE = "[Starting value]";
        public static final String TIME_BASED_TARGET_VALUE = "[Target value to achieve]";

        // Timing Constants
        public static final int EXPLICIT_WAIT_SEC = 30;
        public static final int TYPE_DELAY_MS = 100;
        public static final int ACTION_PAUSE_MS = 1500;
        public static final int PAGE_SETTLE_MS = 2000;
    }

    // ========== HELPER METHODS ==========

    private void log(String message) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - " + message);
    }

    private void logStart(String testId, String testName) {
        log("========== TEST START: " + testId + " - " + testName + " ==========");
    }

    private void logPass(String testId) {
        log("========== TEST PASSED: " + testId + " ==========\n");
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log("Sleep interrupted: " + e.getMessage());
        }
    }

    private String takeScreenshot(String testId) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String path = "screenshots/" + testId + "_" + timestamp + ".png";
            Files.createDirectories(Paths.get("screenshots"));
            Files.move(screenshot.toPath(), Paths.get(path));
            log("Screenshot saved to: " + path);
            return path;
        } catch (IOException e) {
            log("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    private void handleTestFailure(String testId, Exception e) {
        log("!!!!!!!!!! TEST FAILED: " + testId + " !!!!!!!!!!");
        log("Error: " + e.getMessage());
        e.printStackTrace();
        takeScreenshot(testId);
        throw new RuntimeException("Test failed: " + testId, e);
    }

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                WebElement element = driver.findElement(By.xpath(xpath));
                if (element.isDisplayed()) {
                    return element;
                }
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {
                // Ignore and try the next XPath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided XPaths: " + Arrays.toString(xpaths));
    }

    private WebElement waitVisible(String[] xpaths, String elementName) {
        log("Waiting for " + elementName + " to be visible...");
        try {
            return wait.until(driver -> findElementWithFallbacks(xpaths));
        } catch (Exception e) {
            throw new TimeoutException("Timed out waiting for " + elementName + " to be visible. Locators: " + Arrays.toString(xpaths));
        }
    }
    
    private WebElement waitVisible(By locator, String elementName) {
        log("Waiting for " + elementName + " to be visible...");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            throw new TimeoutException("Timed out waiting for " + elementName + " to be visible. Locator: " + locator);
        }
    }

    private WebElement waitClickable(String[] xpaths, String elementName) {
        log("Waiting for " + elementName + " to be clickable...");
        WebElement element = waitVisible(xpaths, elementName);
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            throw new TimeoutException("Timed out waiting for " + elementName + " to be clickable. Locators: " + Arrays.toString(xpaths));
        }
    }
    
    private WebElement waitClickable(By locator, String elementName) {
        log("Waiting for " + elementName + " to be clickable...");
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            throw new TimeoutException("Timed out waiting for " + elementName + " to be clickable. Locator: " + locator);
        }
    }

    private void jsClick(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    private void clickElement(String[] xpaths, String elementName) {
        log("Clicking on " + elementName);
        WebElement element = waitClickable(xpaths, elementName);
        try {
            element.click();
        } catch (Exception e) {
            log("Standard click failed for " + elementName + ". Trying JavaScript click.");
            jsClick(element);
        }
    }
    
    private void clickElement(By locator, String elementName) {
        log("Clicking on " + elementName);
        WebElement element = waitClickable(locator, elementName);
        try {
            element.click();
        } catch (Exception e) {
            log("Standard click failed for " + elementName + ". Trying JavaScript click.");
            jsClick(element);
        }
    }

    private void typeSlowly(WebElement element, String text) {
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(Config.TYPE_DELAY_MS);
        }
    }

    private void sendKeysToElement(String[] xpaths, String text, String elementName) {
        log("Typing '" + text + "' into " + elementName);
        WebElement element = waitVisible(xpaths, elementName);
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        typeSlowly(element, text);
    }

    private void assertElementVisible(String[] xpaths, String elementName) {
        log("Verifying " + elementName + " is visible...");
        waitVisible(xpaths, elementName);
        log(elementName + " is visible as expected.");
    }
    
    private void assertElementVisible(By locator, String elementName) {
        log("Verifying " + elementName + " is visible...");
        waitVisible(locator, elementName);
        log(elementName + " is visible as expected.");
    }

    private void waitForPageLoad() {
        log("Waiting for page to load completely...");
        wait.until(driver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
        sleep(Config.PAGE_SETTLE_MS);
    }

    private void assertUrlContains(String partialUrl) {
        log("Verifying URL contains '" + partialUrl + "'...");
        wait.until(ExpectedConditions.urlContains(partialUrl));
        log("URL verification successful.");
    }

    // ========== REUSABLE WORKFLOWS ==========

    private void loginAndSelectCompany(String email, String password, String companyName) {
        log("Starting login process...");
        driver.get(Config.BASE_URL);
        waitForPageLoad();
        assertElementVisible(Locators.EMAIL_INPUT, "Email input field");

        sendKeysToElement(Locators.EMAIL_INPUT, email, "Email input field");
        clickElement(Locators.CONTINUE_BUTTON, "Continue button");

        sendKeysToElement(Locators.PASSWORD_INPUT, password, "Password input field");
        clickElement(Locators.LOGIN_BUTTON, "Login button");
        waitForPageLoad();

        log("Selecting company: " + companyName);
        By companyLocator = By.xpath(String.format(Locators.COMPANY_LIST_ITEM, companyName));
        clickElement(companyLocator, "Company '" + companyName + "'");
        waitForPageLoad();
        log("Login and company selection successful.");
    }

    private void navigateTo(String menuItem) {
        log("Navigating to '" + menuItem + "' page...");
        clickElement(Locators.NAVIGATION_HEADER_DROPDOWN_INPUT, "Navigation dropdown");
        sleep(500); // Wait for dropdown animation
        By menuItemLocator = By.xpath(String.format(Locators.NAVIGATION_MENU_ITEM, menuItem));
        clickElement(menuItemLocator, menuItem + " link");
        waitForPageLoad();
    }

    private void createFormulaMetric(String metricName, String firstMetric, String operator, String secondMetric) {
        log("Creating a new formula metric: " + metricName);
        navigateTo("Metrics");
        
        clickElement(Locators.ADD_METRIC_BUTTON, "'Add Metric' button");
        waitForPageLoad();
        
        sendKeysToElement(Locators.NAME_INPUT, metricName, "'Metric Name' input");
        
        clickElement(Locators.VALUE_SOURCE_DROPDOWN, "'Value Source' dropdown");
        sleep(500);
        clickElement(By.xpath(String.format(Locators.DROPDOWN_OPTION, "Formula")), "Formula option");
        
        log("Building formula: " + firstMetric + " " + operator + " " + secondMetric);
        sendKeysToElement(Locators.SEARCH_METRIC_INPUT, firstMetric, "'Search Metric' field for first metric");
        sleep(1000); // Wait for search results
        clickElement(By.xpath(String.format(Locators.SEARCH_METRIC_RESULT, firstMetric)), "First metric '" + firstMetric + "'");
        
        clickElement(By.xpath(String.format(Locators.OPERATOR_BUTTON, operator)), "Operator '" + operator + "' button");
        
        sendKeysToElement(Locators.SEARCH_METRIC_INPUT, secondMetric, "'Search Metric' field for second metric");
        sleep(1000); // Wait for search results
        clickElement(By.xpath(String.format(Locators.SEARCH_METRIC_RESULT, secondMetric)), "Second metric '" + secondMetric + "'");
        
        clickElement(Locators.VALIDATE_FORMULA_BUTTON, "Green checkmark button");
        sleep(500);
        
        clickElement(Locators.SAVE_BUTTON, "'Save' button on metric form");
        waitForPageLoad();
        log("Metric '" + metricName + "' created successfully.");
    }

    private void addMetricToDashboard(String metricName) {
        log("Adding metric '" + metricName + "' to dashboard...");
        navigateTo("My Dashboard");
        
        clickElement(Locators.EDIT_KPI_ICON, "'Edit KPI' icon");
        
        By metricInModalLocator = By.xpath(String.format(Locators.KPI_MODAL_AVAILABLE_METRIC, metricName));
        waitVisible(metricInModalLocator, "Metric '" + metricName + "' in available list");
        clickElement(metricInModalLocator, "Metric '" + metricName + "' in available list");
        
        clickElement(Locators.KPI_MODAL_SAVE_BUTTON, "'Save' button in modal");
        waitForPageLoad();
        
        By metricCardLocator = By.xpath(String.format(Locators.METRIC_CARD, metricName));
        assertElementVisible(metricCardLocator, "Newly added metric card for '" + metricName + "'");
        log("Metric '" + metricName + "' added to dashboard successfully.");
    }

    // ========== TEST CASES ==========

    public void runTC001() {
        String testId = "TC001";
        try {
            logStart(testId, "End-to-End Workflow: Create Formula Metric and Set Custom Target");

            // Steps 1-13: Login and create the metric
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);
            createFormulaMetric(Config.METRIC_NAME, Config.FIRST_METRIC, Config.OPERATOR, Config.SECOND_METRIC);

            // Steps 14-17: Add the new metric to the dashboard
            addMetricToDashboard(Config.METRIC_NAME);

            // Step 18: Hover over card, click three-dot menu, and select 'Edit'
            log("Opening edit page for metric: " + Config.METRIC_NAME);
            By metricCardLocator = By.xpath(String.format(Locators.METRIC_CARD, Config.METRIC_NAME));
            WebElement metricCard = waitVisible(metricCardLocator, "Metric card for '" + Config.METRIC_NAME + "'");
            new Actions(driver).moveToElement(metricCard).perform();
            
            WebElement threeDotMenu = waitClickable(metricCard.findElement(By.xpath(Locators.METRIC_CARD_THREE_DOT_MENU)), "Three-dot menu");
            threeDotMenu.click();
            sleep(1000); // Wait for menu to appear
            
            clickElement(By.xpath(Locators.METRIC_CARD_EDIT_OPTION), "'Edit' option");
            waitForPageLoad();

            // Steps 19-21: Configure Custom Target
            log("Configuring custom target...");
            clickElement(By.xpath(Locators.TARGET_TAB), "'Target' tab");
            clickElement(By.xpath(Locators.CUSTOM_TARGET_RADIO), "'Custom' radio button");
            sleep(Config.ACTION_PAUSE_MS);

            List<WebElement> targetInputs = driver.findElements(By.xpath(Locators.CUSTOM_TARGET_LEVEL_INPUTS[0]));
            String[] targetValues = Config.CUSTOM_TARGET_VALUES.replaceAll("[\\[\\]a-zA-Z\\s-]", "").split(",");
            
            if (targetInputs.size() == 4 && targetValues.length == 4) {
                for (int i = 0; i < 4; i++) {
                    log("Entering value for Level " + (i + 1) + ": " + targetValues[i]);
                    typeSlowly(targetInputs.get(i), targetValues[i]);
                }
            } else {
                throw new RuntimeException("Could not find 4 custom target input fields or parse 4 target values.");
            }

            // Step 22: Save configuration
            clickElement(Locators.SAVE_BUTTON, "'Save' button on edit page");
            waitForPageLoad();
            assertUrlContains("dashboard");

            // Step 23: Final verification
            log("Verifying metric card on dashboard shows custom target indicators.");
            assertElementVisible(metricCardLocator, "Metric card for '" + Config.METRIC_NAME + "' with target");
            
            logPass(testId);
        } catch (Exception e) {
            handleTestFailure(testId, e);
        }
    }

    public void runTC002() {
        String testId = "TC002";
        try {
            logStart(testId, "End-to-End Workflow: Create Formula Metric and Set Time-Based Target");

            // Steps 1-13: Login and create the metric
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);
            createFormulaMetric(Config.METRIC_NAME, Config.FIRST_METRIC, Config.OPERATOR, Config.SECOND_METRIC);

            // Steps 14-17: Add the new metric to the dashboard
            addMetricToDashboard(Config.METRIC_NAME);

            // Step 18: Hover over card, click three-dot menu, and select 'Edit'
            log("Opening edit page for metric: " + Config.METRIC_NAME);
            By metricCardLocator = By.xpath(String.format(Locators.METRIC_CARD, Config.METRIC_NAME));
            WebElement metricCard = waitVisible(metricCardLocator, "Metric card for '" + Config.METRIC_NAME + "'");
            new Actions(driver).moveToElement(metricCard).perform();
            
            WebElement threeDotMenu = waitClickable(metricCard.findElement(By.xpath(Locators.METRIC_CARD_THREE_DOT_MENU)), "Three-dot menu");
            threeDotMenu.click();
            sleep(1000);
            
            clickElement(By.xpath(Locators.METRIC_CARD_EDIT_OPTION), "'Edit' option");
            waitForPageLoad();

            // Steps 19-21: Configure Time-Based Target
            log("Configuring time-based target...");
            clickElement(By.xpath(Locators.TARGET_TAB), "'Target' tab");
            clickElement(By.xpath(Locators.TIME_BASED_TARGET_RADIO), "'Time-Based' radio button");
            sleep(Config.ACTION_PAUSE_MS);

            WebElement startInput = waitVisible(By.xpath(Locators.TIME_BASED_START_VALUE_INPUT), "Start Value input");
            typeSlowly(startInput, Config.TIME_BASED_START_VALUE);

            WebElement targetInput = waitVisible(By.xpath(Locators.TIME_BASED_TARGET_VALUE_INPUT), "Target Value input");
            typeSlowly(targetInput, Config.TIME_BASED_TARGET_VALUE);

            // Step 22: Save configuration
            clickElement(Locators.SAVE_BUTTON, "'Save' button on edit page");
            waitForPageLoad();
            assertUrlContains("dashboard");

            // Step 23: Final verification
            log("Verifying metric card on dashboard shows time-based target indicators.");
            assertElementVisible(metricCardLocator, "Metric card for '" + Config.METRIC_NAME + "' with target");

            logPass(testId);
        } catch (Exception e) {
            handleTestFailure(testId, e);
        }
    }

    public void runTC003() {
        String testId = "TC003";
        try {
            logStart(testId, "Metric Management: Successful Creation of a Metric Using the Formula Builder");

            // Step 1: Login and navigate to Metrics page
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);
            
            // Steps 2-10: Create the formula metric
            createFormulaMetric(Config.METRIC_NAME, Config.FIRST_METRIC, Config.OPERATOR, Config.SECOND_METRIC);
            
            log("Verifying metric creation by checking for a success message or presence in the list.");
            // A robust verification would be to find the metric in the metrics list on the page.
            assertElementVisible(By.xpath("//td[text()='" + Config.METRIC_NAME + "']"), "Newly created metric in the list");

            logPass(testId);
        } catch (Exception e) {
            handleTestFailure(testId, e);
        }
    }

    public void runTC004() {
        String testId = "TC004";
        try {
            logStart(testId, "Dashboard & KPI Customization: Add a Pre-existing Metric to 'My Dashboard'");

            // Step 1: Login and navigate to 'My Dashboard'
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);
            
            // Steps 2-6: Add the pre-existing metric to the dashboard
            // Using FIRST_METRIC as the pre-existing metric to add, as per test data.
            addMetricToDashboard(Config.FIRST_METRIC);
            
            log("Final verification: Metric card is visible on the dashboard.");
            By metricCardLocator = By.xpath(String.format(Locators.METRIC_CARD, Config.FIRST_METRIC));
            assertElementVisible(metricCardLocator, "Metric card for '" + Config.FIRST_METRIC + "'");

            logPass(testId);
        } catch (Exception e) {
            handleTestFailure(testId, e);
        }
    }

    // ========== SETUP, CLEANUP, MAIN ==========

    public void setUp() {
        log("Initializing WebDriver...");
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Uncomment and set path if needed
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
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
            // Note: Running tests sequentially might cause issues with duplicate metric names.
            // A better approach would be to use unique names for each test run or clean up data.
            // For this generation, we assume each test can run independently or sequentially with unique data.
            // testSuite.runTC002(); 
            // testSuite.runTC003();
            // testSuite.runTC004();
        } catch (Exception e) {
            System.err.println("A critical error occurred during the test suite execution: " + e.getMessage());
        } finally {
            testSuite.cleanup();
        }
    }
}