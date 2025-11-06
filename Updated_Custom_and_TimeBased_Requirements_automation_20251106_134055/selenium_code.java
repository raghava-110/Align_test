package com.selenium.datadriven;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlignTestDataDriven {

    // ========== LOCATORS - COMPREHENSIVE FALLBACKS ==========
    private static final String[] EMAIL_INPUT_XPATHS = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[contains(@data-bind,'value: username')]",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[@placeholder='Email']",
        "//input[contains(@id, 'email')]",
        "//label[normalize-space()='Email Address']/following-sibling::div//input"
    };

    private static final String[] CONTINUE_BUTTON_XPATHS = {
        "//button[contains(text(),'Continue')]",
        "//button[@id='continueButton']",
        "//button/span[normalize-space()='Continue']"
    };

    private static final String[] PASSWORD_INPUT_XPATHS = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[contains(@data-bind,'value: password')]",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@id, 'password')]",
        "//label[normalize-space()='Password']/following-sibling::div//input"
    };

    private static final String[] LOGIN_BUTTON_XPATHS = {
        "//button[contains(text(),'Login')]",
        "//button[@id='loginButton']",
        "//button[@type='submit']",
        "//button/span[normalize-space()='Login']"
    };

    private static final String[] METRICS_LINK_XPATHS = {
        "//div[@id='header-navigation']//a[normalize-space()='Metrics']",
        "//a[@href='/Company/Metrics']",
        "//nav//a[contains(text(), 'Metrics')]",
        "//span[normalize-space()='Metrics']/ancestor::a"
    };

    private static final String[] ADD_METRIC_BUTTON_XPATHS = {
        "//button[normalize-space()='Add Metric']",
        "//a[normalize-space()='Add Metric']",
        "//button[contains(., 'Add Metric')]",
        "//button[@id='addMetricButton']"
    };

    private static final String[] METRIC_NAME_INPUT_XPATHS = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//input[@placeholder='Name of the Metric']",
        "//label[normalize-space()='Name']/following-sibling::div//input",
        "//input[contains(@class,'metric-name')]"
    };

    private static final String[] VALUE_SOURCE_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Value Source']/following-sibling::span//span[@class='k-select']",
        "//label[contains(text(),'Value Source')]/..//span[contains(@class,'k-select')]"
    };

    private static final String[] CADENCE_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Cadence']/following-sibling::span//span[@class='k-select']",
        "//label[contains(text(),'Cadence')]/..//span[contains(@class,'k-select')]"
    };

    private static final String[] RESETS_ON_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Resets On']/following-sibling::span//span[@class='k-select']",
        "//label[contains(text(),'Resets On')]/..//span[contains(@class,'k-select')]"
    };

    private static final String[] FORMULA_METRIC_SEARCH_INPUT_XPATHS = {
        "//input[@placeholder='Name or Owner']",
        "//div[contains(@class, 'formula-builder')]//input[@type='text']"
    };

    private static final String[] SAVE_BUTTON_XPATHS = {
        "//button[@id='saveButton']",
        "//button[normalize-space()='Save']",
        "//button[contains(., 'Save') and contains(@class, 'primary')]",
        "//div[contains(@class, 'modal-footer')]//button[normalize-space()='Save']"
    };


    private static final String[] MY_DASHBOARD_LINK_XPATHS = {
        "//div[@id='header-navigation']//a[normalize-space()='My Dashboard']",
        "//a[@href='/Dashboard']",
        "//nav//a[contains(text(), 'My Dashboard')]",
        "//span[normalize-space()='My Dashboard']/ancestor::a"
    };

    private static final String[] EDIT_KPI_ICON_XPATHS = {
        "//span[@title='Edit Kpi']",
        "//div[contains(@class, 'my-kpis')]//span[contains(@class, 'ico-edit')]",
        "//h2[normalize-space()='My KPIs']/..//span[@title='Edit Kpi']"
    };

    private static final String[] KPI_MODAL_SEARCH_INPUT_XPATHS = {
        "//div[@id='editKpisModal']//input[@placeholder='Search']",
        "//div[contains(@class, 'modal-body')]//input[@type='search']"
    };

    private static final String[] TARGET_TAB_XPATHS = {
        "//a[normalize-space()='Target']",
        "//ul[contains(@class, 'nav-tabs')]//a[contains(text(), 'Target')]"
    };

    private static final String[] CUSTOM_TARGET_RADIO_XPATHS = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']",
        "//input[@name='targetOptions' and @value='1']"
    };

    private static final String[] TIME_BASED_TARGET_RADIO_XPATHS = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']",
        "//input[@name='targetOptions' and @value='2']"
    };

    private static final String[] CUSTOM_TARGET_LEVEL_1_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.Level1']"};
    private static final String[] CUSTOM_TARGET_LEVEL_2_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.Level2']"};
    private static final String[] CUSTOM_TARGET_LEVEL_3_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.Level3']"};
    private static final String[] CUSTOM_TARGET_LEVEL_4_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.Level4']"};

    private static final String[] TIME_BASED_START_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.StartValue']"};
    private static final String[] TIME_BASED_TARGET_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.TargetValue']"};

    private static final String[] METRIC_SELECTION_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Metric']/following-sibling::span//span[@class='k-select']"
    };

    // ========== CONFIGURATION ==========
    private static final String EXCEL_FILE_PATH = "C:/path/to/your/excel/file.xlsx"; // <-- CHANGE THIS
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final int EXPLICIT_WAIT_SEC = 20;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1000;
    private static final int PAGE_SETTLE_MS = 2500;

    // ========== CLASS VARIABLES ==========
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static boolean isFirstRecord = true;

    // ========== EXCEL READER UTILITY ==========
    private static class ExcelReader {
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
                    boolean hasData = false;
                    for (int j = 0; j < headers.size(); j++) {
                        Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        String cellValue = (cell == null) ? "" : getCellValueAsString(cell);
                        rowData.put(headers.get(j), cellValue);
                        if (!cellValue.isEmpty()) {
                            hasData = true;
                        }
                    }
                    if (hasData) {
                        testData.add(rowData);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading Excel file: " + e.getMessage());
                e.printStackTrace();
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
                        // Fallback for formula resulting in a number
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

    // ========== HELPER METHODS ==========
    private void log(String message) {
        System.out.println(message);
    }

    private void logStart(String step) {
        log("--- START: " + step + " ---");
    }

    private void logPass(String message) {
        log("PASS: " + message);
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            } catch (Exception e) {
                // Ignore and try the next xpath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided XPaths.");
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    
    private void jsClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].click();", element);
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    private void typeSlowly(WebElement element, String text) {
        if (text == null || text.isEmpty()) return;
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void selectCompanyByName(String companyName) {
        logStart("Select company: " + companyName);
        String companySpanXpath = String.format("//div[contains(@class, 'company-name') and normalize-space()='%s']", companyName);
        WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));

        String[] parentSelectors = {
            "/ancestor::div[contains(@class, 'company-list-item')]",
            "/ancestor::div[contains(@class, 'company-card')]",
            "/ancestor::div[@role='button']",
            "/.."
        };

        WebElement clickableParent = null;
        for (String selector : parentSelectors) {
            try {
                clickableParent = companySpan.findElement(By.xpath("." + selector));
                if (clickableParent != null) break;
            } catch (Exception e) {
                // Continue to next selector
            }
        }

        if (clickableParent != null) {
            log("Found clickable parent card. Clicking it.");
            wait.until(ExpectedConditions.elementToBeClickable(clickableParent)).click();
        } else {
            log("WARN: Could not find a clickable parent card. Clicking the company name span directly.");
            companySpan.click();
        }
        logPass("Company '" + companyName + "' selected.");
    }

    private void selectFromKendoDropdown(String[] dropdownTriggerXpaths, String optionText) {
        if (optionText == null || optionText.isEmpty()) return;
        clickElement(dropdownTriggerXpaths);
        sleep(ACTION_PAUSE_MS / 2);
        String optionXpath = String.format("//ul[contains(@class, 'k-list') and not(contains(@style,'display: none'))]/li[normalize-space()='%s']", optionText);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        jsClick(option);
        sleep(ACTION_PAUSE_MS / 2);
    }

    // ========== TEST EXECUTION WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        if (testData.isEmpty()) {
            log("No test data found in the Excel file or file could not be read.");
            return;
        }

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("\n=============================================================");
            log("Executing Test Case: " + data.get("id") + " - " + data.get("name"));
            log("=============================================================");
            try {
                executeWorkflow(data);
            } catch (Exception e) {
                log("!!!!!!!!!! TEST FAILED: " + data.get("id") + " !!!!!!!!!!");
                e.printStackTrace();
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // 1. Extract data from the map
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

        // 2. Login and Company Selection (only for the first run)
        if (isFirstRecord) {
            logStart("Navigate to Application URL");
            driver.get(BASE_URL);
            logPass("Login page is displayed.");

            logStart("Enter email and continue");
            sendKeysToElement(EMAIL_INPUT_XPATHS, testUserEmail);
            clickElement(CONTINUE_BUTTON_XPATHS);
            logPass("Email entered.");

            logStart("Enter password and login");
            sendKeysToElement(PASSWORD_INPUT_XPATHS, testUserPassword);
            clickElement(LOGIN_BUTTON_XPATHS);
            logPass("Password entered and login clicked.");

            sleep(PAGE_SETTLE_MS);
            selectCompanyByName(companyName);
            sleep(PAGE_SETTLE_MS);
            logPass("Dashboard is displayed.");
        } else {
            logStart("Navigating to dashboard for subsequent run");
            clickElement(MY_DASHBOARD_LINK_XPATHS);
            sleep(PAGE_SETTLE_MS);
            logPass("On dashboard page.");
        }

        // 3. Navigate to Metrics and start creation
        logStart("Navigate to Metrics page");
        clickElement(METRICS_LINK_XPATHS);
        sleep(PAGE_SETTLE_MS);
        logPass("Metrics page loaded.");

        logStart("Click 'Add Metric'");
        clickElement(ADD_METRIC_BUTTON_XPATHS);
        sleep(ACTION_PAUSE_MS);
        logPass("Metric creation form appeared.");

        // 4. Fill out metric details
        logStart("Enter metric name: " + metricName);
        sendKeysToElement(METRIC_NAME_INPUT_XPATHS, metricName);
        logPass("Metric name entered.");

        logStart("Select Value Source: " + valueSource);
        selectFromKendoDropdown(VALUE_SOURCE_DROPDOWN_XPATHS, valueSource);
        logPass("Value source selected.");
        sleep(ACTION_PAUSE_MS);

        // 5. Handle different Value Source paths
        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Building formula: (" + firstMetric + ") " + operator + " (" + secondMetric + ")");
            WebElement searchInput = findElementWithFallbacks(FORMULA_METRIC_SEARCH_INPUT_XPATHS);
            
            typeSlowly(searchInput, firstMetric);
            String firstMetricOptionXpath = String.format("//ul[contains(@class, 'k-list')]/li[normalize-space()='%s']", firstMetric);
            jsClick(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(firstMetricOptionXpath))));
            logPass("First metric selected.");
            sleep(ACTION_PAUSE_MS / 2);

            String operatorXpath = String.format("//div[@class='formula-operators']//button[normalize-space()='%s']", operator);
            jsClick(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(operatorXpath))));
            logPass("Operator selected.");
            sleep(ACTION_PAUSE_MS / 2);

            typeSlowly(searchInput, secondMetric);
            String secondMetricOptionXpath = String.format("//ul[contains(@class, 'k-list')]/li[normalize-space()='%s']", secondMetric);
            jsClick(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(secondMetricOptionXpath))));
            logPass("Second metric selected.");
            sleep(ACTION_PAUSE_MS / 2);

            jsClick(driver.findElement(By.xpath("//span[@title='Validate and Calculate']")));
            logPass("Formula validated.");

        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            logStart("Selecting existing metric: " + metricToSelect);
            selectFromKendoDropdown(METRIC_SELECTION_DROPDOWN_XPATHS, metricToSelect);
            logPass("Existing metric selected.");
        }

        // 6. Set Cadence
        logStart("Select Cadence: " + cadence);
        selectFromKendoDropdown(CADENCE_DROPDOWN_XPATHS, cadence);
        logPass("Cadence selected.");
        sleep(ACTION_PAUSE_MS);

        if ("Weekly".equalsIgnoreCase(cadence)) {
            logStart("Select Resets On: " + resetsOn);
            selectFromKendoDropdown(RESETS_ON_DROPDOWN_XPATHS, resetsOn);
            logPass("Resets On day selected.");
        }

        // 7. Save Metric
        logStart("Save the new metric");
        clickElement(SAVE_BUTTON_XPATHS);
        sleep(PAGE_SETTLE_MS);
        logPass("Metric saved successfully.");

        // 8. Add metric to Dashboard
        logStart("Navigate to My Dashboard to add KPI");
        clickElement(MY_DASHBOARD_LINK_XPATHS);
        sleep(PAGE_SETTLE_MS);
        logPass("On My Dashboard page.");

        logStart("Open Edit KPI modal");
        clickElement(EDIT_KPI_ICON_XPATHS);
        sleep(ACTION_PAUSE_MS);
        logPass("KPI modal opened.");

        logStart("Search for and add new metric to dashboard");
        sendKeysToElement(KPI_MODAL_SEARCH_INPUT_XPATHS, metricName);
        sleep(ACTION_PAUSE_MS);
        String metricInModalXpath = String.format("//div[@id='availableKpis']//div[normalize-space()='%s']", metricName);
        jsClick(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(metricInModalXpath))));
        logPass("Metric '" + metricName + "' moved to selected KPIs.");
        
        clickElement(SAVE_BUTTON_XPATHS);
        sleep(PAGE_SETTLE_MS);
        logPass("KPI configuration saved, dashboard refreshed.");

        // 9. Configure Target if specified
        if (!"None".equalsIgnoreCase(targetType)) {
            logStart("Configuring target type: " + targetType);
            
            // Find the correct metric card
            String metricCardXpath = String.format("//div[contains(@class, 'kpi-card-container')]//span[contains(@class, 'metric-name') and normalize-space()='%s']/ancestor::div[contains(@class, 'kpi-card-container')]", metricName);
            List<WebElement> cards = driver.findElements(By.xpath(metricCardXpath));
            WebElement metricCard = cards.get(cards.size() - 1); // Get the last one, assuming it's the newest
            
            new Actions(driver).moveToElement(metricCard).perform();
            sleep(ACTION_PAUSE_MS / 2);
            logPass("Hovered over the new metric card.");

            WebElement threeDotMenu = metricCard.findElement(By.xpath(".//span[contains(@class, 'ico-threeDots1')]"));
            jsClick(threeDotMenu);
            sleep(ACTION_PAUSE_MS);
            logPass("Clicked three-dot menu.");

            WebElement editOption = driver.findElement(By.xpath("//div[contains(@class, 'kpicard-dropdown-menu-content') and contains(@style, 'display: block')]//li[@title='Edit']"));
            jsClick(editOption);
            sleep(PAGE_SETTLE_MS);
            logPass("Clicked 'Edit'. Navigated to Edit Metric page.");

            clickElement(TARGET_TAB_XPATHS);
            sleep(ACTION_PAUSE_MS);
            logPass("On 'Target' tab.");

            if ("Custom".equalsIgnoreCase(targetType)) {
                logStart("Setting Custom Target values");
                clickElement(CUSTOM_TARGET_RADIO_XPATHS);
                sleep(ACTION_PAUSE_MS);
                sendKeysToElement(CUSTOM_TARGET_LEVEL_1_INPUT_XPATHS, data.get("custom_target_values").split(",")[0].trim());
                sendKeysToElement(CUSTOM_TARGET_LEVEL_2_INPUT_XPATHS, data.get("custom_target_values").split(",")[1].trim());
                sendKeysToElement(CUSTOM_TARGET_LEVEL_3_INPUT_XPATHS, data.get("custom_target_values").split(",")[2].trim());
                sendKeysToElement(CUSTOM_TARGET_LEVEL_4_INPUT_XPATHS, data.get("custom_target_values").split(",")[3].trim());
                logPass("Custom target values entered.");
            } else if ("Time-Based".equalsIgnoreCase(targetType)) {
                logStart("Setting Time-Based Target values");
                clickElement(TIME_BASED_TARGET_RADIO_XPATHS);
                sleep(ACTION_PAUSE_MS);
                sendKeysToElement(TIME_BASED_START_INPUT_XPATHS, data.get("time_based_start_value"));
                sendKeysToElement(TIME_BASED_TARGET_INPUT_XPATHS, data.get("time_based_target_value"));
                logPass("Time-Based start and target values entered.");
            }

            clickElement(SAVE_BUTTON_XPATHS);
            sleep(PAGE_SETTLE_MS);
            logPass("Target configuration saved. Navigated back to dashboard.");
        }

        // 10. Final Verification
        logStart("Final verification on dashboard");
        String finalCardXpath = String.format("//div[contains(@class, 'kpi-card-container')]//span[contains(@class, 'metric-name') and normalize-space()='%s']", metricName);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(finalCardXpath), 0));
        logPass("Verified that the metric card for '" + metricName + "' is present on the dashboard.");
        log("--- TEST CASE " + data.get("id") + " COMPLETED SUCCESSFULLY ---");
    }

    // ========== SETUP, CLEANUP, AND MAIN METHOD ==========
    public void setUp() {
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe"); // Uncomment if needed
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
    }

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
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during the test execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.cleanup();
        }
    }
}