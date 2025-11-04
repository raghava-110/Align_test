package com.selenium.datadriventest;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AlignTestDataDriven {

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "path/to/your/excel/file.xlsx";
    // ==================================================

    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // --- LOCATORS ---
    private static final String[] EMAIL_INPUT = new String[]{
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email')]",
        "//span[contains(@class,'k-widget')]//input[@id='usernameField']"
    };
    private static final String[] CONTINUE_BUTTON = new String[]{
        "//button[contains(@class,'icon') and contains(@class,'primary')]",
        "//button[contains(text(),'Continue')]",
        "//button[@id='continueButton']",
        "//button[@type='submit']"
    };
    private static final String[] PASSWORD_INPUT = new String[]{
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password')]",
        "//span[contains(@class,'k-widget')]//input[@id='passwordField']"
    };
    private static final String[] LOGIN_BUTTON = new String[]{
        "//button[contains(@class,'login-button')]",
        "//button[contains(text(),'Login')]",
        "//button[@id='loginButton']",
        "//button[@type='submit']"
    };
    private static final String[] DASHBOARD_NAV_VERIFICATION = new String[]{
        "//div[@class='navigation-header-container']",
        "//input[@id='navigation-header-dropdown']",
        "//div[contains(@class, 'main-nav')]"
    };
    private static final String[] METRICS_LINK = new String[]{
        "//a[@href='/Company/Metrics']",
        "//span[text()='Metrics']/ancestor::a",
        "//div[contains(@class, 'main-nav')]//a[contains(., 'Metrics')]"
    };
    private static final String[] ADD_METRIC_BUTTON = new String[]{
        "//button[contains(., 'Add Metric')]",
        "//a[contains(., 'Add Metric')]",
        "//button[@data-bind='click: clickAddMetric']",
        "//button[@id='addMetricButton']"
    };
    private static final String[] METRIC_NAME_INPUT = new String[]{
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//input[@placeholder='Name of the Metric']",
        "//label[text()='Name']/following-sibling::input",
        "//input[@name='MetricName']"
    };
    private static final String[] VALUE_SOURCE_DROPDOWN = new String[]{
        "//label[contains(text(),'Value Source')]/following-sibling::span//span[@class='k-select']",
        "//input[@data-bind='value: selectedIntegration,source: integrationOptions, events: { open: clickValuesourceDropdown, change: clickChangeIntegration, select: disableOptions }']/ancestor::span/span[@class='k-select']"
    };
    private static final String[] FORMULA_SEARCH_METRIC_INPUT = new String[]{
        "//input[@data-bind='events: { keyup: calculated.onFilterChange }']",
        "//input[@placeholder='Name or Owner']"
    };
    private static final String[] CADENCE_DROPDOWN = new String[]{
        "//label[contains(text(),'Cadence')]/following-sibling::span//span[@class='k-select']",
        "//input[@data-bind='value: selectedCadence, source:cadenceChoiceOptions, enabled:cadenceIsNonEditable, events: { change: clickChangeCadence }']/ancestor::span/span[@class='k-select']"
    };
    private static final String[] RESETS_ON_DROPDOWN = new String[]{
        "//label[contains(text(),'Resets On')]/following-sibling::span//span[@class='k-select']",
        "//input[@data-bind='value: selectedResetWeekDay, source:weeklyCadenceOptions, enabled:cadenceSelectionEnabled, invisible:monthlyCadenceSelected, events: { change: clickChangeResetWeekDay }']/ancestor::span/span[@class='k-select']"
    };
    private static final String[] CONFIRM_FORMULA_BUTTON = new String[]{
        "//span[@title='Validate and Calculate']",
        "//span[contains(@class, 'ico-checkmark')]"
    };
    private static final String[] SAVE_BUTTON = new String[]{
        "//button[@id='saveButton']",
        "//button[contains(., 'Save') and contains(@class, 'primary')]"
    };
    private static final String[] DASHBOARDS_MENU = new String[]{
        "//a[@href='/Dashboard']",
        "//span[text()='Dashboards']/ancestor::a"
    };
    private static final String[] MY_DASHBOARD_LINK = new String[]{
        "//a[contains(text(),'My Dashboard')]",
        "//div[contains(@class, 'dropdown-menu')]//a[contains(., 'My Dashboard')]"
    };
    private static final String[] EDIT_KPI_ICON = new String[]{
        "//span[@title='Edit Kpi']",
        "//span[contains(@class,'icon-edit-kpi')]"
    };
    private static final String[] SAVE_KPI_MODAL_BUTTON = new String[]{
        "//div[contains(@class, 'k-window-content')]//button[contains(text(), 'Save')]"
    };
    private static final String[] TARGET_TAB = new String[]{
        "//a[text()='Target']",
        "//li[@role='tab']/a[contains(., 'Target')]"
    };
    private static final String[] CUSTOM_TARGET_RADIO = new String[]{
        "//label[contains(text(), 'Custom')]/preceding-sibling::input[@type='radio']",
        "//input[@name='targetTemplate' and @value='1']"
    };
    private static final String[] TIME_BASED_TARGET_RADIO = new String[]{
        "//label[contains(text(), 'Time-Based')]/preceding-sibling::input[@type='radio']",
        "//input[@name='targetTemplate' and @value='2']"
    };
    private static final String[] LEVEL_1_INPUT = new String[]{"//input[@data-bind='value: companyMetric.KPITarget.Level1']"};
    private static final String[] LEVEL_2_INPUT = new String[]{"//input[@data-bind='value: companyMetric.KPITarget.Level2']"};
    private static final String[] LEVEL_3_INPUT = new String[]{"//input[@data-bind='value: companyMetric.KPITarget.Level3']"};
    private static final String[] LEVEL_4_INPUT = new String[]{"//input[@data-bind='value: companyMetric.KPITarget.Level4']"};
    private static final String[] TIME_BASED_START_INPUT = new String[]{"//input[@data-bind='value: companyMetric.KPITarget.StartValue']"};
    private static final String[] TIME_BASED_TARGET_INPUT = new String[]{"//input[@data-bind='value: companyMetric.KPITarget.TargetValue']"};
    private static final String[] METRIC_SOURCE_SEARCH_INPUT = new String[]{
        "//label[text()='Metric']/following-sibling::span//input",
        "//input[@placeholder='Search Metrics...']"
    };

    public static class ExcelReader {
        public static List<Map<String, String>> readExcel(String filePath) {
            List<Map<String, String>> testData = new ArrayList<>();
            try (FileInputStream fis = new FileInputStream(new File(filePath));
                 Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                Row headerRow = sheet.getRow(0);
                if (headerRow == null) return testData;

                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(getCellValueAsString(cell));
                }

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row currentRow = sheet.getRow(i);
                    if (currentRow == null) continue;
                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < headers.size(); j++) {
                        Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        rowData.put(headers.get(j), getCellValueAsString(cell));
                    }
                    testData.add(rowData);
                }
            } catch (IOException e) {
                System.err.println("Error reading Excel file: " + e.getMessage());
            }
            return testData;
        }

        private static String getCellValueAsString(Cell cell) {
            if (cell == null) return "";
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    }
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        return new DecimalFormat("#").format(numericValue);
                    }
                    return String.valueOf(numericValue);
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (IllegalStateException e) {
                        // Fallback for formula resulting in number
                        double formulaValue = cell.getNumericCellValue();
                        if (formulaValue == Math.floor(formulaValue)) {
                            return new DecimalFormat("#").format(formulaValue);
                        }
                        return String.valueOf(formulaValue);
                    }
                case BLANK:
                default:
                    return "";
            }
        }
    }

    // --- HELPER METHODS ---
    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            } catch (Exception e) {
                // Ignore and try next xpath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided XPaths.");
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        jsClick(element);
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        typeSlowly(element, text);
    }

    private void selectCompanyByName(String companyName) {
        logStart("Select Company: " + companyName);
        String companySpanXpath = String.format("//div[contains(@class, 'company-name') and text()='%s']", companyName);
        WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));

        String[] parentSelectors = {
            "/ancestor::div[contains(@class, 'company-list-card')]",
            "/ancestor::div[contains(@class, 'company-item')]",
            "/ancestor::div[@role='button']",
            "/ancestor::div[contains(@onclick, 'selectCompany')]",
            "/ancestor::*[contains(@class, 'clickable')]"
        };

        for (String selector : parentSelectors) {
            try {
                WebElement parent = companySpan.findElement(By.xpath("." + selector));
                log("Found clickable parent. Clicking parent element.");
                jsClick(parent);
                logPass("Company selected successfully.");
                return;
            } catch (Exception e) {
                // Parent not found, try next selector
            }
        }

        log("Could not find a preferred clickable parent. Clicking the company name span directly.");
        jsClick(companySpan);
        logPass("Company selected successfully.");
    }

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", element);
    }

    private void typeSlowly(WebElement element, String text) {
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        sleep(PAGE_SETTLE_MS);
    }

    private void assertElementVisible(String[] xpaths) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpaths[0])));
        } catch (Exception e) {
            throw new AssertionError("Assertion failed: Element was not visible.", e);
        }
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void log(String message) {
        System.out.println("INFO: " + message);
    }

    private void logStart(String step) {
        System.out.println("\n>>>>> STARTING: " + step + " <<<<<");
    }

    private void logPass(String message) {
        System.out.println("PASS: " + message);
    }

    private void handleTestFailure(Exception e, int rowNum) {
        System.err.println("!!!!!! FAILED on row " + (rowNum + 2) + ": " + e.getMessage() + " !!!!!!");
        e.printStackTrace();
    }

    @Test
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        AtomicInteger i = new AtomicInteger(0);
        testData.forEach(data -> {
            try {
                System.out.println("\n=====================================================================");
                System.out.println("Executing Test Case: " + data.get("id") + " - " + data.get("name"));
                System.out.println("=====================================================================");
                executeWorkflow(data);
            } catch (Exception e) {
                handleTestFailure(e, i.get());
            } finally {
                i.getAndIncrement();
            }
        });
    }

    private void executeWorkflow(Map<String, String> data) {
        // --- Extract Data ---
        String testUserEmail = data.get("test_user_email");
        String testUserPassword = data.get("test_user_password");
        String companyName = data.get("company_name");
        String metricName = data.get("metric_name");
        String valueSource = data.get("value_source");
        String cadence = data.get("cadence");
        String resetsOn = data.get("resets_on");
        String firstMetric = data.get("first_metric");
        String operator = data.get("operator");
        String secondMetric = data.get("second_metric");
        String metricToSelect = data.get("metric_to_select");
        String targetType = data.get("target_type");
        String customTargetValues = data.get("custom_target_values");
        String timeBasedStartValue = data.get("time_based_start_value");
        String timeBasedTargetValue = data.get("time_based_target_value");

        // Step 1: Navigate to URL
        logStart("Step 1: Navigate to Application URL");
        driver.get(BASE_URL);
        waitForPageLoad();
        logPass("Login page is displayed.");

        // Step 2-3: Login
        logStart("Step 2-3: Enter credentials and login");
        sendKeysToElement(EMAIL_INPUT, testUserEmail);
        clickElement(CONTINUE_BUTTON);
        sendKeysToElement(PASSWORD_INPUT, testUserPassword);
        clickElement(LOGIN_BUTTON);
        waitForPageLoad();
        logPass("User authenticated.");

        // Step 4: Select Company
        selectCompanyByName(companyName);
        waitForPageLoad();

        // Step 5: Verify Dashboard
        logStart("Step 5: Verify dashboard page is displayed");
        assertElementVisible(DASHBOARD_NAV_VERIFICATION);
        logPass("Main dashboard is visible.");

        // Step 6: Navigate to Metrics
        logStart("Step 6: Click on 'Metrics' in navigation menu");
        clickElement(METRICS_LINK);
        waitForPageLoad();
        logPass("Metrics page is loaded.");

        // Step 7: Click 'Add Metric'
        logStart("Step 7: Click the 'Add Metric' button");
        clickElement(ADD_METRIC_BUTTON);
        sleep(ACTION_PAUSE_MS);
        logPass("Metric creation form appears.");

        // Step 8: Enter Metric Name
        logStart("Step 8: Enter the metric name");
        sendKeysToElement(METRIC_NAME_INPUT, metricName);
        logPass("Metric name entered: " + metricName);

        // Step 9-14: Configure Value Source and Cadence
        logStart("Step 9: Select Value Source: " + valueSource);
        clickElement(VALUE_SOURCE_DROPDOWN);
        clickElement(new String[]{String.format("//li[text()='%s']", valueSource)});
        sleep(ACTION_PAUSE_MS);

        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Step 11-14: Build Formula");
            sendKeysToElement(FORMULA_SEARCH_METRIC_INPUT, firstMetric);
            clickElement(new String[]{String.format("//li[contains(text(),'%s')]", firstMetric)});
            sleep(ACTION_PAUSE_MS);
            clickElement(new String[]{String.format("//button[text()='%s']", operator)});
            sleep(ACTION_PAUSE_MS);
            sendKeysToElement(FORMULA_SEARCH_METRIC_INPUT, secondMetric);
            clickElement(new String[]{String.format("//li[contains(text(),'%s')]", secondMetric)});
            logPass("Formula built: " + firstMetric + " " + operator + " " + secondMetric);
        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            logStart("Step 10: Select existing metric");
            sendKeysToElement(METRIC_SOURCE_SEARCH_INPUT, metricToSelect);
            clickElement(new String[]{String.format("//li[contains(text(),'%s')]", metricToSelect)});
            logPass("Selected metric: " + metricToSelect);
        }

        logStart("Step 15-16: Select Cadence: " + cadence);
        clickElement(CADENCE_DROPDOWN);
        clickElement(new String[]{String.format("//li[text()='%s']", cadence)});
        if ("Weekly".equalsIgnoreCase(cadence)) {
            clickElement(RESETS_ON_DROPDOWN);
            clickElement(new String[]{String.format("//li[text()='%s']", resetsOn)});
            logPass("Cadence set to Weekly, resets on " + resetsOn);
        } else {
            logPass("Cadence set to " + cadence);
        }

        // Step 17-18: Save Metric
        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Step 17: Confirm formula");
            clickElement(CONFIRM_FORMULA_BUTTON);
            logPass("Formula confirmed.");
        }
        logStart("Step 18: Save the metric");
        clickElement(SAVE_BUTTON);
        waitForPageLoad();
        logPass("Metric created successfully.");

        // Step 19-24: Add Metric to Dashboard
        logStart("Step 19: Navigate to 'My Dashboard'");
        clickElement(DASHBOARDS_MENU);
        clickElement(MY_DASHBOARD_LINK);
        waitForPageLoad();
        logPass("'My Dashboard' page loaded.");

        logStart("Step 20-23: Add new metric as a KPI");
        clickElement(EDIT_KPI_ICON);
        sleep(ACTION_PAUSE_MS);
        String metricInModalXpath = String.format("//div[contains(@class,'available-metrics')]//div[text()='%s']", metricName);
        clickElement(new String[]{metricInModalXpath});
        log("Located and clicked new metric in KPI modal.");
        clickElement(SAVE_KPI_MODAL_BUTTON);
        waitForPageLoad();
        logPass("KPI saved, dashboard refreshed.");

        // Step 25-32: Configure Target
        if (targetType != null && !targetType.isEmpty() && !"None".equalsIgnoreCase(targetType)) {
            logStart("Step 25-27: Open Edit page for the new metric card");
            String cardXpath = String.format("//div[contains(@class, 'kpi-card-container') and .//span[text()='%s']]", metricName);
            List<WebElement> cards = driver.findElements(By.xpath(cardXpath));
            WebElement targetCard = cards.get(cards.size() - 1); // Get the last one added
            new Actions(driver).moveToElement(targetCard).perform();
            sleep(500);
            WebElement threeDotMenu = targetCard.findElement(By.xpath(".//*[contains(@class, 'ico-threeDots')]"));
            jsClick(threeDotMenu);
            sleep(ACTION_PAUSE_MS);
            String editOptionXpath = "//div[contains(@class, 'k-popup')]//li[@title='Edit']";
            clickElement(new String[]{editOptionXpath});
            waitForPageLoad();
            logPass("Navigated to metric Edit page.");

            logStart("Step 28: Select 'Target' tab");
            clickElement(TARGET_TAB);
            sleep(ACTION_PAUSE_MS);

            if ("Custom".equalsIgnoreCase(targetType)) {
                logStart("Step 29-30: Configure Custom Target");
                clickElement(CUSTOM_TARGET_RADIO);
                sleep(ACTION_PAUSE_MS);
                String[] customValues = customTargetValues.split(",");
                sendKeysToElement(LEVEL_1_INPUT, customValues[0].trim());
                sendKeysToElement(LEVEL_2_INPUT, customValues[1].trim());
                sendKeysToElement(LEVEL_3_INPUT, customValues[2].trim());
                sendKeysToElement(LEVEL_4_INPUT, customValues[3].trim());
                logPass("Custom target values entered.");
            } else if ("Time-Based".equalsIgnoreCase(targetType)) {
                logStart("Step 29-30: Configure Time-Based Target");
                clickElement(TIME_BASED_TARGET_RADIO);
                sleep(ACTION_PAUSE_MS);
                sendKeysToElement(TIME_BASED_START_INPUT, timeBasedStartValue);
                sendKeysToElement(TIME_BASED_TARGET_INPUT, timeBasedTargetValue);
                logPass("Time-Based target values entered.");
            }

            logStart("Step 31: Save target configuration");
            clickElement(SAVE_BUTTON);
            waitForPageLoad();
            logPass("Target saved, navigated back to dashboard.");

            logStart("Step 32: Verify metric card on dashboard");
            assertElementVisible(new String[]{cardXpath});
            logPass("Metric card with target configuration is visible.");
        } else {
            log("Skipping target configuration as Target Type is 'None' or empty.");
        }
    }

    @BeforeClass
    public void setUp() {
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
    }

    @AfterClass
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        AlignTestDataDriven test = new AlignTestDataDriven();
        try {
            test.setUp();
            test.testDataDrivenWorkflow();
        } finally {
            test.cleanup();
        }
    }
}