package com.aligntoday.test;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

public class AlignMetricsDataDrivenTest {

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "path/to/test_data.xlsx";
    // ==================================================

    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static boolean isFirstRecord = true;

    // --- LOCATORS ---

    private static final String[] email_input = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[contains(@data-bind,'value: username')]",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[@placeholder='Email']",
        "//label[contains(.,'Email')]/following-sibling::*/input"
    };

    private static final String[] continue_button = {
        "//button[contains(@class,'icon') and contains(@class,'primary')]",
        "//button[normalize-space()='Continue']",
        "//button[@id='continueButton']",
        "//button[@type='submit']"
    };

    private static final String[] password_input = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[contains(@data-bind,'value: password')]",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[@placeholder='Password']"
    };

    private static final String[] login_button = {
        "//button[contains(@class,'login-button')]",
        "//button[normalize-space()='Login']",
        "//button[@id='loginButton']",
        "//button[@type='submit']"
    };

    private static final String[] metrics_link = {
        "//input[@id='navigation-header-dropdown']",
        "//a[normalize-space()='Metrics']",
        "//span[normalize-space()='Metrics']/parent::a",
        "//div[contains(@class,'main-nav')]//a[contains(.,'Metrics')]",
        "//li/a[span[text()='Metrics']]"
    };

    private static final String[] add_metric_button = {
        "//button[normalize-space()='Add Metric']",
        "//button[contains(.,'Add Metric')]",
        "//a[contains(.,'Add Metric')]",
        "//button[contains(@class,'add-metric')]"
    };

    private static final String[] name_input = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//input[@placeholder='Name of the Metric']",
        "//label[normalize-space()='Name']/following-sibling::div/input",
        "//label[contains(.,'Name')]/..//input[@type='text']"
    };

    private static final String[] current_value_input = {
        "//label[normalize-space()='Current Value']/following-sibling::div//input",
        "//label[contains(.,'Current Value')]/..//input",
        "//input[@data-bind='value: companyMetric.Value']"
    };

    private static final String[] value_source_dropdown = {
        "//label[normalize-space()='Value Source']/following-sibling::div//span[contains(@class,'k-dropdown')]",
        "//label[contains(text(),'Value Source')]/..//span[@aria-label='select']"
    };

    private static final String[] formula_search_metric_input = {
        "//input[@placeholder='Name or Owner']",
        "//div[@class='formula-builder']//input[@type='text']"
    };

    private static final String[] formula_checkmark_button = {
        "//span[@title='Validate and Calculate']",
        "//span[contains(@class,'ico-check')]"
    };

    private static final String[] metric_source_dropdown = {
        "//label[normalize-space()='Metric']/following-sibling::div//span[contains(@class,'k-dropdown')]",
        "//label[contains(text(),'Metric')]/..//span[@aria-label='select']"
    };

    private static final String[] cadence_dropdown = {
        "//label[normalize-space()='Cadence']/following-sibling::div//span[contains(@class,'k-dropdown')]",
        "//label[contains(text(),'Cadence')]/..//span[@aria-label='select']"
    };

    private static final String[] resets_on_dropdown = {
        "//label[normalize-space()='Resets On']/following-sibling::div//span[contains(@class,'k-dropdown')]",
        "//label[contains(text(),'Resets On')]/..//span[@aria-label='select']"
    };

    private static final String[] save_button = {
        "//button[normalize-space()='Save']",
        "//button[contains(.,'Save') and contains(@class,'primary')]",
        "//div[@class='modal-footer']//button[normalize-space()='Save']"
    };

    private static final String[] my_dashboard_link = {
        "//a[normalize-space()='My Dashboard']",
        "//span[normalize-space()='My Dashboard']/parent::a",
        "//div[contains(@class,'main-nav')]//a[contains(.,'My Dashboard')]"
    };

    private static final String[] edit_kpi_icon = {
        "//span[@title='Edit Kpi']",
        "//div[h5[normalize-space()='My KPIs']]//span[contains(@class,'icon-edit')]",
        "//span[contains(@class,'icon-edit') and @data-bind='click: editMyKpis']"
    };

    private static final String[] edit_kpi_modal_search_input = {
        "//div[@id='editMyKpis']//input[@placeholder='Search']",
        "//div[contains(@class,'modal-content')]//input[@type='text' and contains(@data-bind,'search')]"
    };

    private static final String[] edit_kpi_modal_save_button = {
        "//div[@id='editMyKpis']//button[normalize-space()='Save']",
        "//div[contains(@class,'modal-footer')]//button[contains(.,'Save')]"
    };

    // --- EXCEL READER UTILITY ---

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
                            rowData.put(getCellValueAsString(headerCell), getCellValueAsString(currentCell));
                        }
                    }
                    testData.add(rowData);
                }
            } catch (IOException e) {
                System.err.println("Error reading Excel file: " + e.getMessage());
                throw new RuntimeException(e);
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
                        if (numericValue == (long) numericValue) {
                            return String.format("%d", (long) numericValue);
                        } else {
                            return new DecimalFormat("#.#####").format(numericValue);
                        }
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return String.valueOf(cell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        return cell.getStringCellValue().trim();
                    }
                case BLANK:
                default:
                    return "";
            }
        }
    }

    // --- HELPER METHODS ---

    private void log(String message) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + message);
    }

    private void logStart(String step) {
        log("START: " + step);
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
            } catch (TimeoutException e) {
                // Ignore and try next xpath
            }
        }
        throw new NoSuchElementException("Could not find element with any of the provided XPaths.");
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            jsClick(element);
        }
    }

    private void jsClick(WebElement element) {
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
        String companySpanXpath = String.format("//span[@data-bind='text:getCompanyNameText' and normalize-space()='%s']", companyName);
        WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));

        String[] parentSelectors = {
            "ancestor::div[contains(@class,'company-card')]",
            "ancestor::div[contains(@class,'company-item')]",
            "ancestor::div[@role='button']",
            "ancestor::div[contains(@class,'clickable')]",
            "parent::div"
        };

        for (String selector : parentSelectors) {
            try {
                WebElement parent = companySpan.findElement(By.xpath(selector));
                jsClick(parent);
                logPass("Clicked on company card for: " + companyName);
                return;
            } catch (NoSuchElementException e) {
                // Parent not found, try next selector
            }
        }

        log("WARNING: Could not find a clickable parent card for company. Clicking the span directly.");
        jsClick(companySpan);
        logPass("Clicked on company span for: " + companyName);
    }

    private void selectDropdownOptionByText(String[] dropdownLocator, String optionText) {
        if (optionText == null || optionText.isEmpty()) return;
        clickElement(dropdownLocator);
        sleep(500);
        String optionXpath = String.format("//ul[not(contains(@style,'display: none'))]/li[normalize-space()='%s']", optionText);
        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));
        jsClick(optionElement);
    }

    private void assertElementVisible(String[] xpaths, String elementName) {
        try {
            WebElement element = findElementWithFallbacks(xpaths);
            Assert.assertTrue(element.isDisplayed(), elementName + " should be visible.");
            logPass(elementName + " is visible.");
        } catch (NoSuchElementException e) {
            Assert.fail(elementName + " was not found on the page.");
        }
    }

    // --- TEST SETUP AND TEARDOWN ---

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
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // --- TEST WORKFLOW ---

    @Test
    public void testCreateMultipleMetrics() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        Assert.assertFalse(testData.isEmpty(), "Test data from Excel is empty.");

        for (int i = 0; i < testData.size(); i++) {
            log("\n--- Processing Record " + (i + 1) + " of " + testData.size() + " ---");
            try {
                executeWorkflow(testData.get(i));
                isFirstRecord = false; // After the first successful run, set to false
            } catch (Exception e) {
                log("ERROR processing record " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
                // Optionally, take a screenshot on failure
                Assert.fail("Test failed on record " + (i + 1), e);
            }
        }
        log("\n--- Final Verification ---");
        for (Map<String, String> data : testData) {
            String metricName = data.get("Metric Name");
            String currentValue = data.get("Current Value");
            String finalVerificationXpath = String.format("//div[contains(@class,'my-kpis')]//div[contains(.,'%s') and contains(.,'%s')]", metricName, currentValue);
            Assert.assertTrue(driver.findElement(By.xpath(finalVerificationXpath)).isDisplayed(), "Final verification failed: Metric '" + metricName + "' not found on dashboard.");
            logPass("Metric '" + metricName + "' is correctly displayed on the dashboard.");
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract data from the map
        String email = data.get("Email");
        String password = data.get("Password");
        String companyName = data.get("Company Name");
        String metricName = data.get("Metric Name");
        String currentValue = data.get("Current Value");
        String valueSource = data.get("Value Source");
        String firstMetric = data.get("First Metric");
        String operator = data.get("Operator");
        String secondMetric = data.get("Second Metric");
        String selectedMetric = data.get("Metric to Select");
        String cadence = data.get("Cadence");
        String resetsOn = data.get("Resets On");

        // Step 1-5: Login and Company Selection (only for the first record)
        if (isFirstRecord) {
            logStart("Navigate to Application URL: " + BASE_URL);
            driver.get(BASE_URL);
            logPass("Login page is displayed.");

            logStart("Enter email and continue");
            sendKeysToElement(email_input, email);
            clickElement(continue_button);
            logPass("Password field is displayed.");

            logStart("Enter password and login");
            sendKeysToElement(password_input, password);
            clickElement(login_button);
            logPass("Company selection page is displayed.");

            selectCompanyByName(companyName);
            logPass("User redirected to the company's dashboard.");
            
            sleep(PAGE_SETTLE_MS);
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "URL does not contain 'dashboard' after login.");
        }

        // Step 6: Navigate to Metrics Page
        logStart("Navigate to Metrics Page");
        selectDropdownOptionByText(metrics_link, "Metrics");
        sleep(PAGE_SETTLE_MS);
        logPass("Metrics page is loaded.");

        // Step 7: Initiate Metric Creation
        logStart("Initiate Metric Creation");
        clickElement(add_metric_button);
        logPass("Metric creation form opened.");

        // Step 8-9: Enter Metric Details
        logStart("Enter Metric Name: " + metricName);
        sendKeysToElement(name_input, metricName);
        logPass("Metric name entered.");

        logStart("Enter Current Value: " + currentValue);
        sendKeysToElement(current_value_input, currentValue);
        logPass("Current value entered.");

        // Step 10-14: Configure Value Source
        logStart("Select Value Source: " + valueSource);
        selectDropdownOptionByText(value_source_dropdown, valueSource);
        logPass("Value source selected.");

        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Building formula: " + firstMetric + " " + operator + " " + secondMetric);
            WebElement searchInput = findElementWithFallbacks(formula_search_metric_input);
            typeSlowly(searchInput, firstMetric);
            selectDropdownOptionByText(new String[]{}, firstMetric);
            logPass("First metric selected.");

            String operatorXpath = String.format("//button[normalize-space()='%s']", operator);
            clickElement(new String[]{operatorXpath});
            logPass("Operator '" + operator + "' selected.");

            searchInput = findElementWithFallbacks(formula_search_metric_input);
            typeSlowly(searchInput, secondMetric);
            selectDropdownOptionByText(new String[]{}, secondMetric);
            logPass("Second metric selected.");

            clickElement(formula_checkmark_button);
            logPass("Formula confirmed.");
        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            logStart("Selecting source metric: " + selectedMetric);
            selectDropdownOptionByText(metric_source_dropdown, selectedMetric);
            logPass("Source metric selected.");
        }

        // Step 16-17: Select Cadence
        logStart("Select Cadence: " + cadence);
        selectDropdownOptionByText(cadence_dropdown, cadence);
        logPass("Cadence selected.");

        if ("Weekly".equalsIgnoreCase(cadence)) {
            logStart("Select Resets On: " + resetsOn);
            selectDropdownOptionByText(resets_on_dropdown, resetsOn);
            logPass("Reset day selected.");
        }

        // Step 19: Save the Metric
        logStart("Save the metric");
        clickElement(save_button);
        sleep(PAGE_SETTLE_MS);
        logPass("Metric saved successfully.");

        // Step 20: Navigate to My Dashboard
        logStart("Navigate to My Dashboard");
        selectDropdownOptionByText(metrics_link, "My Dashboard");
        sleep(PAGE_SETTLE_MS);
        logPass("Dashboard page loaded.");

        // Step 21: Open Edit KPI Modal
        logStart("Open Edit KPI Modal");
        clickElement(edit_kpi_icon);
        logPass("Edit KPI modal opened.");

        // Step 22-24: Search and Add Metric to Dashboard
        logStart("Search for and add new metric to dashboard");
        sendKeysToElement(edit_kpi_modal_search_input, metricName);
        sleep(ACTION_PAUSE_MS);
        String metricInListXpath = String.format("//div[@id='availableKpis']//div[normalize-space()='%s']", metricName);
        clickElement(new String[]{metricInListXpath});
        logPass("Metric '" + metricName + "' clicked to add to KPIs.");

        // Step 25: Save KPI Configuration
        logStart("Save KPI configuration");
        clickElement(edit_kpi_modal_save_button);
        sleep(PAGE_SETTLE_MS);
        logPass("KPI configuration saved and modal closed.");

        // Step 26: Verify Metric on Dashboard
        logStart("Verify metric is visible on dashboard");
        String metricOnDashboardXpath = String.format("//div[contains(@class,'my-kpis')]//div[contains(.,'%s')]", metricName);
        assertElementVisible(new String[]{metricOnDashboardXpath}, "Metric '" + metricName + "' on dashboard");
    }
}
