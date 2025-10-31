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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class SeleniumAutomationGenerator {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    // =================================================================================
    // LOCATORS CLASS
    // =================================================================================
    private static class Locators {
        // --- Login Page ---
        private static final String[] EMAIL_INPUT = new String[]{"//input[@data-bind='value: username, events: { keyup: emailKeyUp }']", "//input[contains(@data-bind,'value: username')]", "//input[@id='usernameField']", "//span[contains(@class,'k-widget')]//input[@id='usernameField']", "//span[contains(@class,'k-dropdown')]//input[@id='usernameField']", "//span[contains(@class,'k-combobox')]//input[@id='usernameField']", "//input[@name='username']", "//input[@type='email']"};
        private static final String[] CONTINUE_BUTTON = new String[]{"//button[contains(text(),'Continue')]", "//button[contains(@class,'icon') and contains(., 'Continue')]"};
        private static final String[] PASSWORD_INPUT = new String[]{"//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']", "//input[contains(@data-bind,'value: password')]", "//input[@id='passwordField']", "//span[contains(@class,'k-widget')]//input[@id='passwordField']", "//span[contains(@class,'k-dropdown')]//input[@id='passwordField']", "//span[contains(@class,'k-combobox')]//input[@id='passwordField']", "//input[@name='password']", "//input[@type='password']"};
        private static final String[] LOGIN_BUTTON = new String[]{"//button[contains(text(),'Login')]", "//button[contains(@class,'icon') and contains(., 'Login')]"};

        // --- Company Selection ---
        private static final String[] MANAGECOMPANIESDROPDOWNLIST_INPUT = new String[]{"//input[@data-bind='value: currentCompany, source: dsCompanies, events: { change: onSelectCompany, open: openManageCompanies }']", "//input[contains(@data-bind,'value: currentCompany')]", "//input[@id='manageCompaniesDropDownList']", "//span[contains(@class,'k-widget')]//input[@id='manageCompaniesDropDownList']", "//span[contains(@class,'k-dropdown')]//input[@id='manageCompaniesDropDownList']", "//span[contains(@class,'k-combobox')]//input[@id='manageCompaniesDropDownList']", "//input[contains(@class,'manage-drop')]", "//label[@for='manageCompaniesDropDownList']/following-sibling::input"};

        // --- Navigation ---
        private static final String[] NAVIGATION_HEADER_DROPDOWN_INPUT = new String[]{"//input[@data-bind='value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }']", "//input[contains(@data-bind,'value: headerNavigation')]", "//input[@id='navigation-header-dropdown']", "//span[contains(@class,'k-widget')]//input[@id='navigation-header-dropdown']", "//span[contains(@class,'k-dropdown')]//input[@id='navigation-header-dropdown']", "//span[contains(@class,'k-combobox')]//input[@id='navigation-header-dropdown']", "//input[contains(@class,'header-dropdown')]", "//input[contains(@class,'header-dropdown') and contains(@class,'setMaxWidth')]" };
        private static final String[] METRICS_LINK = new String[]{"//a[contains(text(), 'Metrics')]", "//span[contains(text(), 'Metrics')]/ancestor::a", "//div[contains(@class, 'navigation')]//a[contains(., 'Metrics')]"};
        private static final String[] MY_DASHBOARD_LINK = new String[]{"//a[contains(text(), 'My Dashboard')]", "//span[contains(text(), 'My Dashboard')]/ancestor::a"};

        // --- Metrics Page ---
        private static final String[] ADD_METRIC_BUTTON = new String[]{"//button[contains(text(),'Add Metric')]", "//a[contains(text(),'Add Metric')]", "//button[contains(@class,'icon') and contains(., 'Add Metric')]"};
        private static final String[] NAME_INPUT = new String[]{"//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']", "//input[contains(@data-bind,'value: companyMetric')]", "//input[@type='text']", "//input[@placeholder='Name of the Metric']", "//input[contains(@placeholder,'Name of th')]", "//input[contains(@class,'autocomplete-off')]" };
        private static final String[] VALUE_SOURCE_DROPDOWN = new String[]{"//label[contains(text(),'Value Source')]/following-sibling::span//span[contains(@class, 'k-select')]", "//label[contains(text(),'Value Source')]/..//span[contains(@class, 'k-select')]"};
        private static final String[] NAME_OR_OWNER_INPUT = new String[]{"//input[@data-bind='events: { keyup: calculated.onFilterChange }']", "//input[@type='text']", "//input[@placeholder='Name or Owner']", "//input[contains(@placeholder,'Name or Ow')]" };
        private static final String[] SAVE_BUTTON = new String[]{"//button[contains(text(),'Save')]", "//button[@id='saveButton']", "//button[contains(@class,'icon') and contains(., 'Save')]"};

        // --- Dashboard ---
        private static final String[] EDIT_KPI_ICON = new String[]{"//span[@title='Edit Kpi']", "//span[contains(@class,'icon') and @title='Edit Kpi']"};
        private static final String[] KPI_MODAL_SEARCH_INPUT = new String[]{"//div[contains(@class, 'kpi-modal')]//input[@placeholder='Search']", "//div[@id='editKpiModal']//input[contains(@placeholder, 'Search')]"};
        private static final String[] KPI_MODAL_SAVE_BUTTON = new String[]{"//div[contains(@class, 'kpi-modal')]//button[contains(text(), 'Save')]", "//div[@id='editKpiModal']//button[contains(text(), 'Save')]"};

        // --- Metric Card ---
        private static final String METRIC_CARD_BY_NAME_XPATH = "//div[contains(@class, 'kpi-card')]//span[contains(text(), '%s')]";
        private static final String THREE_DOT_MENU_BUTTON_XPATH = ".//span[contains(@class, 'ico-threeDots1')]/ancestor::button";
        private static final String EDIT_OPTION_IN_DROPDOWN_XPATH = "//div[contains(@class, 'kpicard-dropdown-menu-content') and contains(@style, 'display: block')]//li[@title='Edit']";

        // --- Edit Metric Page (Target Tab) ---
        private static final String[] TARGET_TAB = new String[]{"//a[text()='Target']", "//li/a[contains(text(), 'Target')]"};
        private static final String[] CUSTOM_RADIO_BUTTON = new String[]{"//label[contains(text(), 'Custom')]/preceding-sibling::input[@type='radio']", "//input[@type='radio' and @value='Custom']"};
        private static final String[] TIME_BASED_RADIO_BUTTON = new String[]{"//label[contains(text(), 'Time-Based')]/preceding-sibling::input[@type='radio']", "//input[@type='radio' and @value='Time-Based']"};
        private static final String[] CUSTOM_TARGET_LEVEL_1_INPUT = new String[]{"//input[@placeholder='400']", "(//label[text()='Level 1']/following-sibling::div//input)[1]"};
        private static final String[] CUSTOM_TARGET_LEVEL_2_INPUT = new String[]{"//input[@placeholder='300']", "(//label[text()='Level 2']/following-sibling::div//input)[1]"};
        private static final String[] CUSTOM_TARGET_LEVEL_3_INPUT = new String[]{"//input[@placeholder='200']", "(//label[text()='Level 3']/following-sibling::div//input)[1]"};
        private static final String[] CUSTOM_TARGET_LEVEL_4_INPUT = new String[]{"//input[@placeholder='100']", "(//label[text()='Level 4']/following-sibling::div//input)[1]"};
        private static final String[] START_INPUT = new String[]{"//label[contains(text(), 'Start')]/following-sibling::input", "//input[@data-bind='value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }']"};
        private static final String[] TARGET_INPUT = new String[]{"//label[contains(text(), 'Target')]/following-sibling::input", "//input[@data-bind='value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }']"};
    }

    // =================================================================================
    // CONFIG CLASS
    // =================================================================================
    private static class Config {
        private static final String BASE_URL = "https://app.align.ai/login"; // Placeholder URL

        // --- Timing Constants ---
        private static final int EXPLICIT_WAIT_SEC = 30;
        private static final int TYPE_DELAY_MS = 100;
        private static final int ACTION_PAUSE_MS = 1500;
        private static final int PAGE_SETTLE_MS = 2000;

        // --- Test Data ---
        public static final String EMAIL = "[User email address]";
        public static final String PASSWORD = "[User password]";
        public static final String COMPANY_NAME = "[Company to be selected]";

        // --- TC001 Data ---
        public static final String METRIC_NAME_TC001 = "[Name of the metric to be created]";
        public static final String FIRST_METRIC_TC001 = "[Name of first metric to use in formula]";
        public static final String OPERATOR_TC001 = "+";
        public static final String SECOND_METRIC_TC001 = "[Name of second metric to use in formula]";
        public static final String TARGET_TYPE_CUSTOM = "Custom";
        public static final int[] CUSTOM_TARGET_VALUES = {400, 300, 200, 100};

        // --- TC002 Data ---
        public static final String METRIC_NAME_TC002 = "[Name of the metric to be created]";
        public static final String FIRST_METRIC_TC002 = "[Name of first metric to use in formula]";
        public static final String OPERATOR_TC002 = "*";
        public static final String SECOND_METRIC_TC002 = "[Name of second metric to use in formula]";
        public static final String TARGET_TYPE_TIME_BASED = "Time-Based";
        public static final String TIME_BASED_START_VALUE = "100";
        public static final String TIME_BASED_TARGET_VALUE = "500";

        // --- TC003 Data ---
        public static final String EXISTING_FORMULA_METRIC_NAME = "[Name of first metric to use in formula]";
    }

    // =================================================================================
    // HELPER METHODS
    // =================================================================================

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                for (WebElement element : elements) {
                    if (element.isDisplayed() && element.isEnabled()) {
                        return element;
                    }
                }
            } catch (NoSuchElementException e) {
                // Ignore and try next xpath
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
        try {
            return wait.until(driver -> findElementWithFallbacks(xpaths));
        } catch (Exception e) {
            throw new RuntimeException("Timeout waiting for " + elementName + " to be visible.", e);
        }
    }

    private WebElement waitClickable(String[] xpaths, String elementName) {
        log("Waiting for " + elementName + " to be clickable...");
        try {
            WebElement element = findElementWithFallbacks(xpaths);
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            throw new RuntimeException("Timeout waiting for " + elementName + " to be clickable.", e);
        }
    }

    private void clickElement(String[] xpaths, String elementName) {
        try {
            WebElement element = waitClickable(xpaths, elementName);
            element.click();
            log("Clicked on " + elementName);
        } catch (Exception e) {
            log("Standard click failed for " + elementName + ". Trying JavaScript click.");
            try {
                WebElement element = waitVisible(xpaths, elementName);
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
            log("Assertion PASSED: " + elementName + " is visible.");
        } catch (Exception e) {
            handleTestFailure("Assertion FAILED: " + elementName + " is not visible.", e);
        }
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        sleep(Config.PAGE_SETTLE_MS);
        log("Page loaded completely.");
    }

    private void assertUrlContains(String expectedUrlPart) {
        try {
            wait.until(ExpectedConditions.urlContains(expectedUrlPart));
            log("Assertion PASSED: URL contains '" + expectedUrlPart + "'.");
        } catch (Exception e) {
            handleTestFailure("Assertion FAILED: URL does not contain '" + expectedUrlPart + "'. Current URL: " + driver.getCurrentUrl(), e);
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
            File scrFile = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String screenshotPath = "screenshots/" + testName + "_" + timestamp + ".png";
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(scrFile.toPath(), Paths.get(screenshotPath));
            log("Screenshot saved to: " + screenshotPath);
        } catch (IOException e) {
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
        log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log("TEST FAILED: " + message);
        if (e != null) {
            e.printStackTrace();
        }
        if (driver != null) {
            takeScreenshot("FAILURE_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        }
        log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        throw new RuntimeException(message, e);
    }

    // =================================================================================
    // TEST METHODS
    // =================================================================================

    public void runTC001() {
        String testId = "TC001";
        logStart(testId, "E2E - Create Formula Metric, Add to Dashboard, and Configure Custom Target");

        try {
            // Step 1: Login and select company
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);

            // Step 2: Navigate to Metrics page
            clickElement(Locators.METRICS_LINK, "Metrics link in navigation");
            waitForPageLoad();
            assertUrlContains("/metrics");

            // Step 3: Click 'Add Metric' and enter name
            clickElement(Locators.ADD_METRIC_BUTTON, "'Add Metric' button");
            sendKeysToElement(Locators.NAME_INPUT, Config.METRIC_NAME_TC001, "Metric Name input");

            // Step 4: Select 'Formula' as Value Source
            clickElement(Locators.VALUE_SOURCE_DROPDOWN, "'Value Source' dropdown");
            sleep(Config.ACTION_PAUSE_MS / 2);
            WebElement formulaOption = driver.findElement(By.xpath("//li[text()='Formula']"));
            jsClick(formulaOption);
            log("Selected 'Formula' as value source.");

            // Steps 5-7: Build the formula
            buildFormula(Config.FIRST_METRIC_TC001, Config.OPERATOR_TC001, Config.SECOND_METRIC_TC001);

            // Step 8: Save the new metric
            clickElement(Locators.SAVE_BUTTON, "'Save' button");
            sleep(Config.PAGE_SETTLE_MS); // Wait for confirmation
            log("Formula metric created successfully.");

            // Step 9: Navigate to 'My Dashboard'
            clickElement(Locators.MY_DASHBOARD_LINK, "'My Dashboard' link");
            waitForPageLoad();
            assertUrlContains("/dashboard");

            // Steps 10-12: Add the new metric to the dashboard
            addMetricToDashboard(Config.METRIC_NAME_TC001);

            // Steps 13-18: Configure Custom Target
            configureMetricTarget(Config.METRIC_NAME_TC001, Config.TARGET_TYPE_CUSTOM, null, null, Config.CUSTOM_TARGET_VALUES);

            // Step 19: Verify redirection to dashboard
            waitForPageLoad();
            assertUrlContains("/dashboard");
            log("Verified redirection to dashboard after saving target.");
            assertElementVisible(new String[]{String.format(Locators.METRIC_CARD_BY_NAME_XPATH, Config.METRIC_NAME_TC001)}, "Metric card on dashboard");
            log("Metric card with custom target is visible on the dashboard.");

            logPass(testId);
        } catch (Exception e) {
            handleTestFailure("Test " + testId + " failed.", e);
        }
    }

    public void runTC002() {
        String testId = "TC002";
        logStart(testId, "E2E - Create Formula Metric, Add to Dashboard, and Configure Time-Based Target");

        try {
            // Step 1: Login and select company
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);

            // Step 2-4: Create a new formula metric
            clickElement(Locators.METRICS_LINK, "Metrics link in navigation");
            waitForPageLoad();
            clickElement(Locators.ADD_METRIC_BUTTON, "'Add Metric' button");
            sendKeysToElement(Locators.NAME_INPUT, Config.METRIC_NAME_TC002, "Metric Name input");
            clickElement(Locators.VALUE_SOURCE_DROPDOWN, "'Value Source' dropdown");
            sleep(Config.ACTION_PAUSE_MS / 2);
            WebElement formulaOption = driver.findElement(By.xpath("//li[text()='Formula']"));
            jsClick(formulaOption);
            buildFormula(Config.FIRST_METRIC_TC002, Config.OPERATOR_TC002, Config.SECOND_METRIC_TC002);

            // Step 5: Save the new metric
            clickElement(Locators.SAVE_BUTTON, "'Save' button");
            sleep(Config.PAGE_SETTLE_MS);
            log("Formula metric created successfully.");

            // Step 6-7: Add the new metric to the dashboard
            clickElement(Locators.MY_DASHBOARD_LINK, "'My Dashboard' link");
            waitForPageLoad();
            addMetricToDashboard(Config.METRIC_NAME_TC002);

            // Steps 8-12: Configure Time-Based Target
            configureMetricTarget(Config.METRIC_NAME_TC002, Config.TARGET_TYPE_TIME_BASED, Config.TIME_BASED_START_VALUE, Config.TIME_BASED_TARGET_VALUE, null);

            // Step 13: Verify redirection to dashboard
            waitForPageLoad();
            assertUrlContains("/dashboard");
            log("Verified redirection to dashboard after saving target.");
            assertElementVisible(new String[]{String.format(Locators.METRIC_CARD_BY_NAME_XPATH, Config.METRIC_NAME_TC002)}, "Metric card on dashboard");
            log("Metric card with time-based target is visible on the dashboard.");

            logPass(testId);
        } catch (Exception e) {
            handleTestFailure("Test " + testId + " failed.", e);
        }
    }

    public void runTC003() {
        String testId = "TC003";
        logStart(testId, "Dashboard KPI Management - Add an Existing Formula Metric to Dashboard");

        try {
            // Step 1: Login and select company
            loginAndSelectCompany(Config.EMAIL, Config.PASSWORD, Config.COMPANY_NAME);
            waitForPageLoad();
            assertUrlContains("/dashboard");

            // Steps 2-5: Add an existing metric to the dashboard
            addMetricToDashboard(Config.EXISTING_FORMULA_METRIC_NAME);

            // Step 6: Verify metric is visible on dashboard
            waitForPageLoad();
            assertElementVisible(new String[]{String.format(Locators.METRIC_CARD_BY_NAME_XPATH, Config.EXISTING_FORMULA_METRIC_NAME)}, "Newly added metric card");
            log("Verified that the existing metric was successfully added to the dashboard.");

            logPass(testId);
        } catch (Exception e) {
            handleTestFailure("Test " + testId + " failed.", e);
        }
    }

    // --- Reusable Action Methods ---

    private void loginAndSelectCompany(String email, String password, String companyName) {
        driver.get(Config.BASE_URL);
        waitForPageLoad();
        sendKeysToElement(Locators.EMAIL_INPUT, email, "Email input");
        clickElement(Locators.CONTINUE_BUTTON, "'Continue' button");
        sendKeysToElement(Locators.PASSWORD_INPUT, password, "Password input");
        clickElement(Locators.LOGIN_BUTTON, "'Login' button");
        waitForPageLoad();
        log("Login successful.");

        // Select company
        WebElement company = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format("//div[contains(text(),'%s')]", companyName))));
        jsClick(company);
        log("Selected company: " + companyName);
        waitForPageLoad();
    }

    private void buildFormula(String firstMetric, String operator, String secondMetric) {
        // Step 5: Select first metric
        sendKeysToElement(Locators.NAME_OR_OWNER_INPUT, firstMetric, "Formula search input for first metric");
        sleep(Config.ACTION_PAUSE_MS);
        WebElement firstMetricResult = driver.findElement(By.xpath(String.format("//li[contains(text(),'%s')]", firstMetric)));
        jsClick(firstMetricResult);
        log("Selected first metric: " + firstMetric);

        // Step 6: Select operator
        WebElement operatorButton = driver.findElement(By.xpath(String.format("//button[text()='%s']", operator)));
        jsClick(operatorButton);
        log("Selected operator: " + operator);

        // Step 7: Select second metric
        sendKeysToElement(Locators.NAME_OR_OWNER_INPUT, secondMetric, "Formula search input for second metric");
        sleep(Config.ACTION_PAUSE_MS);
        WebElement secondMetricResult = driver.findElement(By.xpath(String.format("//li[contains(text(),'%s')]", secondMetric)));
        jsClick(secondMetricResult);
        log("Selected second metric: " + secondMetric);
    }

    private void addMetricToDashboard(String metricName) {
        // Step 10: Open 'Edit KPI' modal
        clickElement(Locators.EDIT_KPI_ICON, "'Edit KPI' icon");
        sleep(Config.ACTION_PAUSE_MS);

        // Step 11: Find and add metric
        log("Searching for metric '" + metricName + "' in KPI modal.");
        WebElement metricInList = driver.findElement(By.xpath(String.format("//div[contains(@class, 'available-metrics')]//div[contains(text(), '%s')]", metricName)));
        jsClick(metricInList);
        log("Clicked metric to add it to the right panel.");
        sleep(Config.ACTION_PAUSE_MS / 2);

        // Step 12: Save KPI configuration
        clickElement(Locators.KPI_MODAL_SAVE_BUTTON, "KPI modal 'Save' button");
        waitForPageLoad();
        log("Saved KPI configuration. Modal closed.");
    }

    private void configureMetricTarget(String metricName, String targetType, String startValue, String targetValue, int[] customValues) {
        // Step 13: Hover over metric card
        sleep(Config.PAGE_SETTLE_MS);
        WebElement metricCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(Locators.METRIC_CARD_BY_NAME_XPATH, metricName) + "/ancestor::div[contains(@class, 'kpi-card')]")));
        new Actions(driver).moveToElement(metricCard).perform();
        log("Hovered over the metric card for '" + metricName + "'.");

        // Step 14: Click three-dot menu and 'Edit'
        WebElement threeDotMenu = metricCard.findElement(By.xpath(Locators.THREE_DOT_MENU_BUTTON_XPATH));
        wait.until(ExpectedConditions.visibilityOf(threeDotMenu));
        jsClick(threeDotMenu);
        log("Clicked the three-dot menu.");
        sleep(Config.ACTION_PAUSE_MS);
        WebElement editOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.EDIT_OPTION_IN_DROPDOWN_XPATH)));
        jsClick(editOption);
        log("Clicked 'Edit' option.");
        waitForPageLoad();

        // Step 15: Navigate to 'Target' tab
        clickElement(Locators.TARGET_TAB, "'Target' tab");
        sleep(Config.ACTION_PAUSE_MS);

        if (targetType.equals(Config.TARGET_TYPE_CUSTOM)) {
            // Step 16: Select 'Custom' target
            clickElement(Locators.CUSTOM_RADIO_BUTTON, "'Custom' target radio button");
            sleep(Config.ACTION_PAUSE_MS);

            // Step 17: Enter custom values
            sendKeysToElement(Locators.CUSTOM_TARGET_LEVEL_1_INPUT, String.valueOf(customValues[0]), "Level 1 target input");
            sendKeysToElement(Locators.CUSTOM_TARGET_LEVEL_2_INPUT, String.valueOf(customValues[1]), "Level 2 target input");
            sendKeysToElement(Locators.CUSTOM_TARGET_LEVEL_3_INPUT, String.valueOf(customValues[2]), "Level 3 target input");
            sendKeysToElement(Locators.CUSTOM_TARGET_LEVEL_4_INPUT, String.valueOf(customValues[3]), "Level 4 target input");
            log("Entered all custom target values.");
        } else if (targetType.equals(Config.TARGET_TYPE_TIME_BASED)) {
            // Select 'Time-Based' target
            clickElement(Locators.TIME_BASED_RADIO_BUTTON, "'Time-Based' target radio button");
            sleep(Config.ACTION_PAUSE_MS);

            // Enter 'Start' and 'Target' values
            sendKeysToElement(Locators.START_INPUT, startValue, "'Start' value input");
            sendKeysToElement(Locators.TARGET_INPUT, targetValue, "'Target' value input");
            log("Entered time-based start and target values.");
        }

        // Step 18: Save target configuration
        clickElement(Locators.SAVE_BUTTON, "'Save' button on edit page");
        log("Saved target configuration.");
    }

    // =================================================================================
    // SETUP, CLEANUP, MAIN
    // =================================================================================

    public void setUp() {
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Uncomment and set path if needed
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SEC));
        jsExecutor = (JavascriptExecutor) driver;
        log("WebDriver setup complete.");
    }

    public void cleanup() {
        if (driver != null) {
            try {
                driver.quit();
                log("WebDriver cleanup complete.");
            } catch (Exception e) {
                log("Error during WebDriver cleanup: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SeleniumAutomationGenerator testRunner = new SeleniumAutomationGenerator();
        try {
            testRunner.setUp();
            testRunner.runTC001();
            testRunner.runTC002();
            testRunner.runTC003();
        } catch (Exception e) {
            System.err.println("A critical error occurred during the test execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            testRunner.cleanup();
        }
    }
}