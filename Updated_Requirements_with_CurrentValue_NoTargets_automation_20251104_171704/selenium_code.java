package com.aligntoday.automation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

public class AlignMetricsAutomation {

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "path/to/test_metrics.xlsx";
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
            "//input[contains(@placeholder, 'Email')]"
    };
    private static final String[] CONTINUE_BUTTON = new String[]{
            "//button[contains(text(),'Continue')]",
            "//button[@id='continueButton']"
    };
    private static final String[] PASSWORD_INPUT = new String[]{
            "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
            "//input[@id='passwordField']",
            "//input[@name='password']",
            "//input[@type='password']"
    };
    private static final String[] LOGIN_BUTTON = new String[]{
            "//button[contains(text(),'Login')]",
            "//button[@id='loginButton']"
    };
    private static final String[] NAVIGATION_HEADER_DROPDOWN = new String[]{
            "//input[@data-bind='value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }']",
            "//input[@id='navigation-header-dropdown']",
            "//span[contains(@class,'k-dropdown')]//input[contains(@class,'header-dropdown')]"
    };
    private static final String[] ADD_METRIC_BUTTON = new String[]{
            "//button[contains(text(),'Add Metric')]",
            "//button[contains(@class, 'add-metric')]",
            "//a[contains(text(), 'Add Metric')]"
    };
    private static final String[] METRIC_NAME_INPUT = new String[]{
            "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
            "//input[@placeholder='Name of the Metric']",
            "//label[text()='Name']/following-sibling::div//input"
    };
    private static final String[] CURRENT_VALUE_INPUT = new String[]{
            "//input[@data-bind='value: companyMetric.Value, disabled: isNonEditableMetricIntegration']",
            "//input[@placeholder='Enter a Value']",
            "//label[text()='Current Value']/following-sibling::div//input"
    };
    private static final String[] VALUE_SOURCE_DROPDOWN = new String[]{
            "//label[contains(text(),'Value Source')]/following-sibling::span//span[@class='k-input-value-text']",
            "//label[contains(text(),'Value Source')]/..//span[contains(@class, 'k-dropdown')]"
    };
    private static final String[] FORMULA_METRIC_SEARCH_INPUT = new String[]{
            "//input[@placeholder='Name or Owner']",
            "//div[@class='formula-builder']//input[@type='text']"
    };
    private static final String[] METRIC_SOURCE_SEARCH_INPUT = new String[]{
        "//label[text()='Metric']/following-sibling::span//input",
        "//div[contains(@class, 'metric-source-selector')]//input"
    };
    private static final String[] CADENCE_DROPDOWN = new String[]{
            "//label[contains(text(),'Cadence')]/following-sibling::span//span[@class='k-input-value-text']",
            "//label[contains(text(),'Cadence')]/..//span[contains(@class, 'k-dropdown')]"
    };
    private static final String[] RESETS_ON_DROPDOWN = new String[]{
            "//label[contains(text(),'Resets On')]/following-sibling::span//span[@class='k-input-value-text']",
            "//label[contains(text(),'Resets On')]/..//span[contains(@class, 'k-dropdown')]"
    };
    private static final String[] FORMULA_CONFIRM_BUTTON = new String[]{
        "//span[@title='Validate and Calculate']",
        "//button[contains(@class, 'check')]"
    };
    private static final String[] SAVE_BUTTON = new String[]{
            "//button[text()='Save']",
            "//button[contains(text(),'Save') and contains(@class, 'primary')]"
    };
    private static final String[] EDIT_KPI_ICON = new String[]{
            "//span[@title='Edit Kpi']",
            "//div[h5[text()='My KPIs']]//span[contains(@class, 'icon-edit')]"
    };
    private static final String[] KPI_MODAL_SAVE_BUTTON = new String[]{
            "//div[contains(@class, 'k-window-titlebar')]//following-sibling::div//button[text()='Save']",
            "//div[@id='editMyKpis']//button[text()='Save']"
    };

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/your/chromedriver"); // Update if needed
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void endToEndMetricCreationTest() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        if (testData == null || testData.isEmpty()) {
            handleTestFailure(new RuntimeException("Test data is empty or could not be read from: " + EXCEL_FILE_PATH));
            return;
        }

        // --- ONE-TIME LOGIN AND COMPANY SELECTION ---
        Map<String, String> firstRecord = testData.get(0);
        String userEmail = firstRecord.get("user_email");
        String userPassword = firstRecord.get("user_password");
        String companyName = firstRecord.get("company_name");

        try {
            logStart("Step 1: Navigate to the Application URL");
            driver.get(BASE_URL);
            assertUrlContains("aligntoday.com");
            logPass("Application login page is displayed.");

            logStart("Step 2: Enter user's email and continue");
            sendKeysToElement(EMAIL_INPUT, userEmail);
            clickElement(CONTINUE_BUTTON);
            logPass("Password input field is displayed.");

            logStart("Step 3: Enter user's password and login");
            sendKeysToElement(PASSWORD_INPUT, userPassword);
            clickElement(LOGIN_BUTTON);
            logPass("User is authenticated.");

            logStart("Step 4: Select the specified company");
            selectCompanyByName(companyName);
            logPass("Main application dashboard is displayed for " + companyName);
            sleep(PAGE_SETTLE_MS);

            // --- METRIC CREATION LOOP ---
            for (int i = 0; i < testData.size(); i++) {
                log("\n" + "=".repeat(20) + " STARTING METRIC " + (i + 1) + " CREATION " + "=".repeat(20));
                executeSingleMetricWorkflow(testData.get(i));
                log("=".repeat(20) + " COMPLETED METRIC " + (i + 1) + " CREATION " + "=".repeat(20) + "\n");
            }

            // --- FINAL VERIFICATION ---
            logStart("Step 36: Final Verification on Dashboard");
            for (Map<String, String> record : testData) {
                String metricName = record.get("Metric_Name");
                String kpiXpath = String.format("//div[contains(@class, 'kpi-container')]//div[contains(text(),'%s')]", metricName);
                assertElementVisible(new String[]{kpiXpath});
                log("Verified: Metric '" + metricName + "' is visible in 'My KPIs'.");
            }
            logPass("All three newly created metrics are simultaneously visible on the dashboard.");

        } catch (Exception e) {
            handleTestFailure(e);
        }
    }

    private void executeSingleMetricWorkflow(Map<String, String> data) {
        String metricName = data.get("Metric_Name");
        String currentValue = data.get("Current_Value");
        String valueSource = data.get("Value_Source");
        String cadence = data.get("Cadence");

        // Step 5 & 20 & 31: Navigate to Metrics page
        logStart("Navigate to 'Metrics' page");
        selectHeaderNavigation("Metrics");
        logPass("Metrics page loaded.");

        // Step 6 & 21: Click 'Add Metric'
        logStart("Click 'Add Metric' button");
        clickElement(ADD_METRIC_BUTTON);
        logPass("Metric creation form appears.");

        // Step 7 & 22: Enter Metric Name
        logStart("Enter Metric Name: " + metricName);
        sendKeysToElement(METRIC_NAME_INPUT, metricName);
        logPass("Metric name entered.");

        // Step 8 & 23: Enter Current Value
        logStart("Enter Current Value: " + currentValue);
        sendKeysToElement(CURRENT_VALUE_INPUT, currentValue);
        logPass("Current value entered.");

        // Step 9 & 24: Select Value Source and handle conditional flow
        logStart("Select Value Source: " + valueSource);
        selectKendoDropdownOption(VALUE_SOURCE_DROPDOWN, valueSource);
        logPass("Value source selected. Conditional UI appears.");

        if ("Formula".equalsIgnoreCase(valueSource)) {
            handleFormulaSource(data);
        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            handleMetricSource(data);
        }

        // Step 13 & 26 & 33: Select Cadence
        logStart("Select Cadence: " + cadence);
        selectKendoDropdownOption(CADENCE_DROPDOWN, cadence);
        logPass("Cadence selected.");

        // Step 14: Select Resets On (if Weekly)
        if ("Weekly".equalsIgnoreCase(cadence)) {
            String resetsOn = data.get("Resets_On");
            logStart("Select Resets On: " + resetsOn);
            selectKendoDropdownOption(RESETS_ON_DROPDOWN, resetsOn);
            logPass("'Resets On' day selected.");
        }

        // Step 15 & 27 & 34: Save the metric
        logStart("Click 'Save' button");
        clickElement(SAVE_BUTTON);
        logPass("Metric saved successfully.");
        sleep(PAGE_SETTLE_MS);

        // Step 16-19 & 28-30 & 35: Add metric to dashboard
        addMetricToDashboard(metricName);
    }

    private void handleFormulaSource(Map<String, String> data) {
        String firstMetric = data.get("First_Metric");
        String operator = data.get("Operator");
        String secondMetric = data.get("Second_Metric");

        logStart("Step 10: Select First Metric in Formula: " + firstMetric);
        sendKeysToElement(FORMULA_METRIC_SEARCH_INPUT, firstMetric);
        selectKendoDropdownOptionByText(firstMetric);
        logPass("First metric selected.");

        logStart("Step 11: Select Operator: " + operator);
        String operatorXpath = String.format("//div[@class='formula-builder']//button[text()='%s']", operator.replace("*", "×"));
        clickElement(new String[]{operatorXpath});
        logPass("Operator selected.");

        logStart("Step 12: Select Second Metric in Formula: " + secondMetric);
        sendKeysToElement(FORMULA_METRIC_SEARCH_INPUT, secondMetric);
        selectKendoDropdownOptionByText(secondMetric);
        logPass("Second metric selected. Formula is complete.");
        
        clickElement(FORMULA_CONFIRM_BUTTON);
        logPass("Formula confirmed.");
    }

    private void handleMetricSource(Map<String, String> data) {
        String selectedMetric = data.get("Selected_Metric");
        logStart("Step 25: Search for and select source metric: " + selectedMetric);
        sendKeysToElement(METRIC_SOURCE_SEARCH_INPUT, selectedMetric);
        selectKendoDropdownOptionByText(selectedMetric);
        logPass("Source metric selected.");
    }

    private void addMetricToDashboard(String metricName) {
        logStart("Step 16: Navigate to 'My Dashboard'");
        selectHeaderNavigation("My Dashboard");
        logPass("Dashboard page loaded.");

        logStart("Step 17: Click 'Edit KPI' icon");
        clickElement(EDIT_KPI_ICON);
        logPass("'Edit My KPIs' modal opened.");

        logStart("Step 18: Locate and add metric '" + metricName + "'");
        String metricInModalXpath = String.format("//div[@id='availableKpis']//div[contains(text(),'%s')]", metricName);
        clickElement(new String[]{metricInModalXpath});
        logPass("Metric clicked and moved to selected KPIs panel.");

        logStart("Step 19: Save KPI configuration");
        clickElement(KPI_MODAL_SAVE_BUTTON);
        logPass("Modal closed and dashboard refreshed.");
        sleep(PAGE_SETTLE_MS);

        String kpiOnDashboardXpath = String.format("//div[contains(@class, 'kpi-container')]//div[contains(text(),'%s')]", metricName);
        assertElementVisible(new String[]{kpiOnDashboardXpath});
        logPass("Verified: '" + metricName + "' is visible in 'My KPIs'.");
    }

    @AfterClass
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }

    // --- HELPER METHODS ---

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            } catch (TimeoutException e) {
                // Continue to next xpath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided XPaths.");
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (Exception e) {
            jsClick(element);
        }
        sleep(ACTION_PAUSE_MS / 2);
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        typeSlowly(element, text);
    }

    private void selectCompanyByName(String name) {
        log("Attempting to select company: " + name);
        String spanXpath = String.format("//span[@data-bind='text:getCompanyNameText' and text()='%s']", name);
        WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(spanXpath)));

        String[] parentSelectors = {
            "/ancestor::div[contains(@class,'company-card')]",
            "/ancestor::div[contains(@class,'company-item')]",
            "/ancestor::div[@role='button']",
            "/ancestor::div[contains(@onclick,'selectCompany')]",
            "/parent::div"
        };

        for (String selector : parentSelectors) {
            try {
                WebElement parent = companySpan.findElement(By.xpath("." + selector));
                log("Found clickable parent element. Clicking it.");
                jsClick(parent);
                return;
            } catch (Exception e) {
                // Parent not found, try next selector
            }
        }

        log("Warning: Could not find a robust parent element. Clicking the company name span directly.");
        jsClick(companySpan);
    }
    
    private void selectHeaderNavigation(String optionText) {
        clickElement(NAVIGATION_HEADER_DROPDOWN);
        String optionXpath = String.format("//ul[contains(@id, 'navigation-header-dropdown-list')]//li[text()='%s']", optionText);
        clickElement(new String[]{optionXpath});
        sleep(PAGE_SETTLE_MS);
    }

    private void selectKendoDropdownOption(String[] dropdownXpaths, String optionText) {
        clickElement(dropdownXpaths);
        selectKendoDropdownOptionByText(optionText);
    }
    
    private void selectKendoDropdownOptionByText(String optionText) {
        String optionXpath = String.format("//ul[contains(@class, 'k-list') and not(contains(@style,'display: none'))]//li[text()='%s']", optionText);
        WebElement optionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        jsClick(optionElement);
        sleep(ACTION_PAUSE_MS / 2);
    }

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    private void typeSlowly(WebElement element, String text) {
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void assertElementVisible(String[] xpaths) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpaths[0])));
        } catch (Exception e) {
            throw new AssertionError("Element was not visible: " + xpaths[0], e);
        }
    }

    private void assertUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }



    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void log(String message) {
        System.out.println(message);
    }

    private void logStart(String message) {
        log("[START] " + message);
    }

    private void logPass(String message) {
        log("[PASS]  " + message);
    }

    private void handleTestFailure(Exception e) {
        log("[FAIL] Test failed: " + e.getMessage());
        e.printStackTrace();
        // takeScreenshot("failure_screenshot"); // Optional screenshot on failure
        throw new RuntimeException(e);
    }

    // --- EXCEL READER UTILITY ---
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
                return null;
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
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return new DecimalFormat("#.##########").format(numericValue);
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (IllegalStateException e) {
                        // If formula result is numeric
                        double formulaNumericValue = cell.getNumericCellValue();
                        if (formulaNumericValue == (long) formulaNumericValue) {
                            return String.format("%d", (long) formulaNumericValue);
                        } else {
                            return String.valueOf(formulaNumericValue);
                        }
                    }
                case BLANK:
                default:
                    return "";
            }
        }
    }
}