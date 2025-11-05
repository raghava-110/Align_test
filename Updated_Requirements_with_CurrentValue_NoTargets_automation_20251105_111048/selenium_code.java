package com.selenium.dataprovider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignTestDataDriven {

    // ========== LOCATORS - GENERATED WITH HYBRID STRATEGY ==========
    private static final String[] EMAIL_INPUT_LOCATORS = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[contains(@data-bind,'value: username')]",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email')]",
        "//label[contains(text(), 'Email')]/following-sibling::input",
        "//input[@aria-label='Email Address']"
    };

    private static final String[] CONTINUE_BUTTON_LOCATORS = {
        "//button[contains(., 'Continue')]",
        "//button[@id='continueButton']",
        "//button[contains(@class,'icon') and contains(@class,'primary')]",
        "//button[@type='submit']",
        "//span[text()='Continue']/ancestor::button"
    };

    private static final String[] PASSWORD_INPUT_LOCATORS = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[contains(@data-bind,'value: password')]",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password')]",
        "//label[contains(text(), 'Password')]/following-sibling::input",
        "//input[@aria-label='Password']"
    };

    private static final String[] LOGIN_BUTTON_LOCATORS = {
        "//button[normalize-space()='Login']",
        "//button[contains(., 'Login')]",
        "//button[@id='loginButton']",
        "//button[contains(@class,'primary') and @type='submit']",
        "//input[@type='submit' and @value='Login']",
        "//span[text()='Login']/ancestor::button"
    };

    private static final String[] METRICS_LINK_LOCATORS = {
        "//div[@id='navigation-header']//a[normalize-space()='Metrics']",
        "//a[@href='/Company/Metrics']",
        "//nav//a[contains(., 'Metrics')]",
        "//div[contains(@class, 'main-nav')]//span[text()='Metrics']",
        "//span[text()='Metrics']/ancestor::a"
    };

    private static final String[] ADD_METRIC_BUTTON_LOCATORS = {
        "//button[normalize-space()='Add Metric']",
        "//button[contains(., 'Add Metric')]",
        "//a[contains(., 'Add Metric')]",
        "//button[@data-bind='click: addCompanyMetric']",
        "//button[contains(@class, 'add-metric')]",
        "//span[text()='Add Metric']/ancestor::button"
    };

    private static final String[] METRIC_NAME_INPUT_LOCATORS = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//input[@placeholder='Name of the Metric']",
        "//label[normalize-space()='Name']/following-sibling::div//input",
        "//input[@name='MetricName']",
        "//div[contains(@class, 'metric-name')]//input"
    };

    private static final String[] CURRENT_VALUE_INPUT_LOCATORS = {
        "//input[@data-bind='value: companyMetric.Value, disabled: isNonEditableMetricIntegration']",
        "//input[@placeholder='Enter a Value']",
        "//label[normalize-space()='Current Value']/following-sibling::div//input",
        "//input[contains(@data-bind, 'companyMetric.Value')]",
        "//input[@name='Value']"
    };

    private static final String[] VALUE_SOURCE_DROPDOWN_LOCATORS = {
        "//label[normalize-space()='Value Source']/following-sibling::span//span[@class='k-select']",
        "//label[contains(text(),'Value Source')]/..//span[contains(@class, 'k-dropdown')]",
        "//input[@aria-owns='selectedIntegration_listbox']/../span[@class='k-select']"
    };

    private static final String[] FORMULA_FIRST_METRIC_SEARCH_LOCATORS = {
        "//div[@data-bind='with: calculated']//input[@placeholder='Name or Owner']",
        "//div[contains(@class, 'formula-builder')]//input[contains(@placeholder, 'Search')]",
        "//input[@data-bind='events: { keyup: calculated.onFilterChange }']"
    };

    private static final String[] CADENCE_DROPDOWN_LOCATORS = {
        "//label[normalize-space()='Cadence']/following-sibling::span//span[@class='k-select']",
        "//label[contains(text(),'Cadence')]/..//span[contains(@class, 'k-dropdown')]",
        "//input[@aria-owns='selectedCadence_listbox']/../span[@class='k-select']"
    };

    private static final String[] RESETS_ON_DROPDOWN_LOCATORS = {
        "//label[normalize-space()='Resets On']/following-sibling::span//span[@class='k-select']",
        "//label[contains(text(),'Resets On')]/..//span[contains(@class, 'k-dropdown')]",
        "//input[@aria-owns='selectedResetWeekDay_listbox']/../span[@class='k-select']"
    };

    private static final String[] FORMULA_CONFIRM_BUTTON_LOCATORS = {
        "//span[@title='Validate and Calculate']",
        "//div[@data-bind='with: calculated']//span[contains(@class, 'ico-check')]"
    };

    private static final String[] SAVE_BUTTON_LOCATORS = {
        "//button[normalize-space()='Save']",
        "//button[contains(., 'Save') and contains(@class, 'primary')]",
        "//button[@data-bind='click: saveCompanyMetric']",
        "//div[@class='modal-footer']//button[text()='Save']",
        "//button[@type='submit' and contains(text(), 'Save')]"
    };

    private static final String[] MY_DASHBOARD_LINK_LOCATORS = {
        "//div[@id='navigation-header']//a[normalize-space()='My Dashboard']",
        "//a[@href='/Dashboard']",
        "//nav//a[contains(., 'My Dashboard')]",
        "//div[contains(@class, 'main-nav')]//span[text()='My Dashboard']",
        "//span[text()='My Dashboard']/ancestor::a"
    };

    private static final String[] EDIT_KPI_ICON_LOCATORS = {
        "//span[@title='Edit Kpi']",
        "//span[contains(@class, 'edit-kpi')]",
        "//div[contains(@class, 'kpi-header')]//span[contains(@class, 'icon')]",
        "//h2[text()='My KPIs']/..//span[@title='Edit Kpi']"
    };

    private static final String[] EDIT_KPI_MODAL_SAVE_BUTTON_LOCATORS = {
        "//div[@id='editKpis']//button[normalize-space()='Save']",
        "//div[contains(@class, 'k-window-titlebar')]//..//button[contains(text(), 'Save')]",
        "//div[@aria-labelledby='editKpis_wnd_title']//button[normalize-space()='Save']"
    };

    private static final String[] METRIC_SOURCE_SEARCH_LOCATORS = {
        "//label[normalize-space()='Metric']/following-sibling::span//input",
        "//input[@aria-label='Metric']",
        "//div[contains(@class, 'metric-source')]//input"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/path/to/your/excel/file.xlsx";
    // ==================================================
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1000;
    private static final int PAGE_SETTLE_MS = 2000;

    // Class Variables
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static boolean isFirstRecord = true;

    /**
     * Static inner class for reading data from an Excel file.
     */
    public static class ExcelReader {
        public static List<Map<String, String>> readExcel(String filePath) {
            List<Map<String, String>> testData = new ArrayList<>();
            try (FileInputStream fis = new FileInputStream(new File(filePath));
                 Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                Row headerRow = sheet.getRow(0);
                if (headerRow == null) {
                    throw new RuntimeException("Excel file is empty or has no header row.");
                }

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row currentRow = sheet.getRow(i);
                    if (currentRow == null) continue;
                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                        Cell headerCell = headerRow.getCell(j);
                        Cell currentCell = currentRow.getCell(j);
                        if (headerCell != null) {
                            rowData.put(headerCell.getStringCellValue(), getCellValueAsString(currentCell));
                        }
                    }
                    testData.add(rowData);
                }
            } catch (IOException e) {
                System.err.println("Error reading Excel file: " + filePath);
                e.printStackTrace();
            }
            return testData;
        }

        private static String getCellValueAsString(Cell cell) {
            if (cell == null) {
                return "";
            }
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == Math.floor(numericValue)) {
                            return new DecimalFormat("#").format(numericValue);
                        } else {
                            return String.valueOf(numericValue);
                        }
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (IllegalStateException e) {
                        // If formula evaluates to a number
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == Math.floor(numericValue)) {
                            return new DecimalFormat("#").format(numericValue);
                        } else {
                            return String.valueOf(numericValue);
                        }
                    }
                case BLANK:
                default:
                    return "";
            }
        }
    }

    // ========== HELPER METHODS ==========

    private void log(String message) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + message);
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
        throw new RuntimeException("Element not found with any of the provided locators.");
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) {
            log("Skipping sendKeys as text is null or empty.");
            return;
        }
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        // typeSlowly(element, text);
        element.sendKeys(text);
    }
    
    private void typeSlowly(WebElement element, String text) {
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void selectCompanyByName(String companyName) {
        log("Attempting to select company: " + companyName);
        String spanXpath = String.format("//span[@data-bind='text:getCompanyNameText' and normalize-space()='%s']", companyName);
        WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(spanXpath)));

        String[] parentSelectors = {
            "/ancestor::div[contains(@class, 'company-card')]",
            "/ancestor::div[contains(@class, 'company-item')]",
            "/ancestor::div[@role='button']",
            "/ancestor::a",
            "/.."
        };

        for (String selector : parentSelectors) {
            try {
                WebElement clickableParent = companySpan.findElement(By.xpath("." + selector));
                log("Found clickable parent with selector: " + selector);
                wait.until(ExpectedConditions.elementToBeClickable(clickableParent)).click();
                return;
            } catch (Exception e) {
                // Parent not found, try next selector
            }
        }

        log("WARNING: Could not find a preferred clickable parent. Clicking the span directly.");
        companySpan.click();
    }

    private void selectDropdownOptionByText(String text) {
        if (text == null || text.isEmpty()) {
            log("Skipping dropdown selection as text is null or empty.");
            return;
        }
        String optionXpath = String.format("//ul[not(contains(@style,'display: none'))]/li[normalize-space()='%s']", text);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    private void selectFormulaMetric(String metricName) {
        sendKeysToElement(FORMULA_FIRST_METRIC_SEARCH_LOCATORS, metricName);
        sleep(ACTION_PAUSE_MS); // Wait for search results
        String optionXpath = String.format("//ul[not(contains(@style,'display: none'))]//span[normalize-space()='%s']", metricName);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    private void selectSourceMetric(String metricName) {
        sendKeysToElement(METRIC_SOURCE_SEARCH_LOCATORS, metricName);
        sleep(ACTION_PAUSE_MS); // Wait for search results
        String optionXpath = String.format("//ul[not(contains(@style,'display: none'))]/li[normalize-space()='%s']", metricName);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    private void clickOperator(String operator) {
        String operatorXpath = String.format("//div[@data-bind='with: calculated']//button[normalize-space()='%s']", operator);
        WebElement operatorButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(operatorXpath)));
        operatorButton.click();
    }

    private void addKpiToDashboard(String metricName) {
        String kpiXpath = String.format("//div[@id='availableKpis']//div[normalize-space()='%s']", metricName);
        WebElement kpiElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(kpiXpath)));
        wait.until(ExpectedConditions.elementToBeClickable(kpiElement)).click();
    }

    private void verifyKpiOnDashboard(String metricName) {
        String kpiOnDashboardXpath = String.format("//div[@id='dashboardKpis']//div[contains(text(), '%s')]", metricName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(kpiOnDashboardXpath)));
        log("SUCCESS: Verified metric '" + metricName + "' is visible on the dashboard.");
    }

    // ========== TEST WORKFLOW ==========

    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        if (testData.isEmpty()) {
            log("No test data found in the Excel file. Exiting.");
            return;
        }
        log("Found " + testData.size() + " records to process.");

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("\n--- Processing Record " + (i + 1) + " for Metric: " + data.get("Metric_Name") + " ---");
            try {
                executeWorkflow(data);
                isFirstRecord = false;
            } catch (Exception e) {
                log("ERROR processing record " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
                // Decide if you want to stop or continue with the next record
                break; 
            }
        }
        
        log("\n--- FINAL VERIFICATION ---");
        for (Map<String, String> data : testData) {
            verifyKpiOnDashboard(data.get("Metric_Name"));
        }
        log("All created metrics verified on the dashboard.");
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract data from the map
        String email = data.get("test_user_email");
        String password = data.get("test_user_password");
        String companyName = data.get("company_name");
        String metricName = data.get("Metric_Name");
        String currentValue = data.get("Current_Value");
        String valueSource = data.get("Value_Source");
        String firstMetric = data.get("First_Metric");
        String operator = data.get("Operator");
        String secondMetric = data.get("Second_Metric");
        String selectedMetric = data.get("Selected_Metric");
        String cadence = data.get("Cadence");
        String resetsOn = data.get("Resets_On");

        if (isFirstRecord) {
            // Step 1: Navigate to URL
            log("Step 1: Navigating to the Application URL.");
            driver.get(BASE_URL);

            // Step 2: Enter Email
            log("Step 2: Entering email address.");
            sendKeysToElement(EMAIL_INPUT_LOCATORS, email);
            clickElement(CONTINUE_BUTTON_LOCATORS);

            // Step 3: Enter Password
            log("Step 3: Entering password.");
            sendKeysToElement(PASSWORD_INPUT_LOCATORS, password);
            clickElement(LOGIN_BUTTON_LOCATORS);

            // Step 4: Select Company
            log("Step 4: Selecting company: " + companyName);
            selectCompanyByName(companyName);

            // Step 5: Verify Dashboard
            log("Step 5: Verifying dashboard is displayed.");
            wait.until(ExpectedConditions.urlContains("/Dashboard"));
            log("Login and company selection successful.");
        }

        // --- METRIC CREATION ---
        // Step 6 & 22 & 33: Navigate to Metrics Page
        log("Step 6: Navigating to the 'Metrics' page.");
        clickElement(METRICS_LINK_LOCATORS);
        wait.until(ExpectedConditions.urlContains("/Metrics"));

        // Step 7 & 23: Click 'Add Metric'
        log("Step 7: Clicking the 'Add Metric' button.");
        clickElement(ADD_METRIC_BUTTON_LOCATORS);

        // Step 8, 24, 34: Enter Metric Name
        log("Step 8: Entering Metric Name: '" + metricName + "'.");
        sendKeysToElement(METRIC_NAME_INPUT_LOCATORS, metricName);

        // Step 9, 25, 34: Enter Current Value
        log("Step 9: Entering Current Value: '" + currentValue + "'.");
        sendKeysToElement(CURRENT_VALUE_INPUT_LOCATORS, currentValue);

        // Step 10, 26, 35: Select Value Source
        log("Step 10: Selecting Value Source: '" + valueSource + "'.");
        clickElement(VALUE_SOURCE_DROPDOWN_LOCATORS);
        selectDropdownOptionByText(valueSource);
        sleep(ACTION_PAUSE_MS);

        if ("Formula".equalsIgnoreCase(valueSource)) {
            // Step 11, 35: Select First Metric
            log("Step 11: Searching for and selecting First Metric: '" + firstMetric + "'.");
            selectFormulaMetric(firstMetric);

            // Step 12, 35: Click Operator
            log("Step 12: Clicking the '" + operator + "' operator button.");
            clickOperator(operator);

            // Step 13, 35: Select Second Metric
            log("Step 13: Searching for and selecting Second Metric: '" + secondMetric + "'.");
            selectFormulaMetric(secondMetric);
            
            // Step 16: Confirm Formula
            log("Step 16: Clicking the green checkmark to confirm formula.");
            clickElement(FORMULA_CONFIRM_BUTTON_LOCATORS);

        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            // Step 27: Select Source Metric
            log("Step 27: Searching for and selecting Source Metric: '" + selectedMetric + "'.");
            selectSourceMetric(selectedMetric);
        }

        // Step 14, 28, 36: Select Cadence
        log("Step 14: Selecting Cadence: '" + cadence + "'.");
        clickElement(CADENCE_DROPDOWN_LOCATORS);
        selectDropdownOptionByText(cadence);

        // Step 15: Select Resets On (if Weekly)
        if ("Weekly".equalsIgnoreCase(cadence)) {
            log("Step 15: Selecting Resets On: '" + resetsOn + "'.");
            clickElement(RESETS_ON_DROPDOWN_LOCATORS);
            selectDropdownOptionByText(resetsOn);
        }

        // Step 17, 29, 37: Click Save
        log("Step 17: Clicking the 'Save' button.");
        clickElement(SAVE_BUTTON_LOCATORS);
        sleep(PAGE_SETTLE_MS); // Wait for redirect and list to update

        // --- ADD TO DASHBOARD ---
        // Step 18, 30, 38: Navigate to My Dashboard
        log("Step 18: Navigating to 'My Dashboard'.");
        clickElement(MY_DASHBOARD_LINK_LOCATORS);
        wait.until(ExpectedConditions.urlContains("/Dashboard"));

        // Step 19, 30, 38: Click 'Edit KPI'
        log("Step 19: Clicking the 'Edit KPI' icon.");
        clickElement(EDIT_KPI_ICON_LOCATORS);

        // Step 20, 31, 38: Locate and add metric
        log("Step 20: Locating '" + metricName + "' and adding it to KPIs.");
        addKpiToDashboard(metricName);

        // Step 21, 32, 38: Click Save in modal
        log("Step 21: Clicking 'Save' in the Edit KPI modal.");
        clickElement(EDIT_KPI_MODAL_SAVE_BUTTON_LOCATORS);
        sleep(PAGE_SETTLE_MS); // Wait for modal to close and dashboard to refresh

        log("--- Finished processing metric: " + metricName + " ---");
    }

    // ========== SETUP AND TEARDOWN ==========

    public void setUp() {
        log("Setting up WebDriver.");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
    }

    public void cleanup() {
        log("Cleaning up and closing WebDriver.");
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
            System.err.println("A critical error occurred during the test execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.cleanup();
        }
    }
}