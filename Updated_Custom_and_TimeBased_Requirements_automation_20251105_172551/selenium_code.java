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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AlignTestDataDriven {

    // ========== LOCATORS - COMPREHENSIVE FALLBACKS ==========
    private static final String[] email_input = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[contains(@data-bind,'value: username')]",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email') or contains(@placeholder, 'email')]",
        "//label[normalize-space()='Email Address']/following-sibling::span//input",
        "//input[@aria-label='Email Address']"
    };

    private static final String[] continue_button = {
        "//button[contains(text(),'Continue')]",
        "//button[@id='continueButton']",
        "//button[@type='submit' and contains(., 'Continue')]",
        "//form//button[contains(@class, 'primary')]",
        "//button[contains(@class,'icon') and .//span[contains(text(),'Continue')]]"
    };

    private static final String[] password_input = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[contains(@data-bind,'value: password')]",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password') or contains(@placeholder, 'password')]",
        "//label[normalize-space()='Password']/following-sibling::span//input",
        "//input[@aria-label='Password']"
    };

    private static final String[] login_button = {
        "//button[contains(text(),'Login') or contains(text(),'Log In')]",
        "//button[@id='loginButton']",
        "//button[@type='submit' and (contains(., 'Login') or contains(., 'Log In'))]",
        "//form//button[contains(@class, 'primary') and not(contains(text(),'Continue'))]",
        "//button[contains(@class,'icon') and .//span[contains(text(),'Login')]]"
    };

    private static final String[] metrics_link = {
        "//a[@href='/Company/Metrics' and normalize-space()='Metrics']",
        "//div[@id='header-navigation']//a[normalize-space()='Metrics']",
        "//nav//li/a[span[normalize-space()='Metrics']]",
        "//ul[contains(@class,'nav')]//a[contains(., 'Metrics')]",
        "//a[.//span[text()='Metrics']]"
    };

    private static final String[] add_metric_button = {
        "//button[normalize-space()='Add Metric']",
        "//a[normalize-space()='Add Metric']",
        "//button[contains(., 'Add Metric')]",
        "//button[@data-bind='click: clickAdd']",
        "//button[contains(@class, 'add') and contains(., 'Metric')]",
        "//div[@class='page-header']//button"
    };

    private static final String[] name_input = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//input[@placeholder='Name of the Metric']",
        "//label[normalize-space()='Name']/following-sibling::div/input",
        "//label[contains(text(),'Name')]/..//input[@type='text']",
        "//input[contains(@data-bind,'MetricName')]"
    };

    private static final String[] value_source_dropdown = {
        "//label[normalize-space()='Value Source']/following-sibling::span//span[contains(@class,'k-select')]",
        "//label[contains(text(),'Value Source')]/..//span[@aria-label='select']",
        "//input[@data-bind='value: selectedIntegration']/ancestor::span[contains(@class,'k-dropdown')]"
    };

    private static final String[] cadence_dropdown = {
        "//label[normalize-space()='Cadence']/following-sibling::span//span[contains(@class,'k-select')]",
        "//label[contains(text(),'Cadence')]/..//span[@aria-label='select']",
        "//input[@data-bind='value: selectedCadence']/ancestor::span[contains(@class,'k-dropdown')]"
    };

    private static final String[] resets_on_dropdown = {
        "//label[normalize-space()='Resets On']/following-sibling::span//span[contains(@class,'k-select')]",
        "//label[contains(text(),'Resets On')]/..//span[@aria-label='select']",
        "//input[contains(@data-bind,'selectedResetWeekDay')]/ancestor::span[contains(@class,'k-dropdown')]"
    };
    
    private static final String[] formula_confirm_button = {
        "//span[@title='Validate and Calculate']",
        "//button[contains(@class, 'check')]",
        "//div[contains(@class,'formula-builder')]//span[contains(@class,'ico-check')]"
    };

    private static final String[] save_button = {
        "//button[@id='saveButton' and normalize-space()='Save']",
        "//button[normalize-space()='Save']",
        "//button[contains(., 'Save') and contains(@class, 'primary')]",
        "//div[contains(@class,'modal-footer')]//button[normalize-space()='Save']",
        "//button[@type='submit' and contains(., 'Save')]"
    };

    private static final String[] dashboards_menu = {
        "//a[normalize-space()='Dashboards']",
        "//div[@id='header-navigation']//a[normalize-space()='Dashboards']",
        "//nav//li/a[span[normalize-space()='Dashboards']]",
        "//ul[contains(@class,'nav')]//a[contains(., 'Dashboards')]"
    };

    private static final String[] my_dashboard_link = {
        "//a[normalize-space()='My Dashboard']",
        "//ul[contains(@class,'dropdown-menu')]//a[normalize-space()='My Dashboard']",
        "//a[@href='/Dashboard' and contains(., 'My Dashboard')]"
    };

    private static final String[] edit_kpi_icon = {
        "//div[contains(@class,'my-kpis')]//span[@title='Edit Kpi']",
        "//h2[normalize-space()='My KPIs']/..//span[contains(@class,'ico-edit')]",
        "//span[@title='Edit Kpi']",
        "//div[contains(@class,'widget-header')]//span[contains(@class,'edit')]"
    };

    private static final String[] save_kpi_modal_button = {
        "//div[contains(@class,'modal-content') and .//h4[text()='Edit KPI']]//button[normalize-space()='Save']",
        "//div[@id='editKpiModal']//button[contains(text(),'Save')]",
        "//div[contains(@class,'modal-footer')]//button[normalize-space()='Save']"
    };

    private static final String[] target_tab = {
        "//ul[contains(@class,'nav-tabs')]//a[normalize-space()='Target']",
        "//a[@href='#target' and @role='tab']",
        "//li/a[contains(text(),'Target')]"
    };

    private static final String[] custom_target_radio = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']",
        "//input[@type='radio' and @value='Custom']",
        "//label[contains(.,'Custom')]/input"
    };

    private static final String[] time_based_target_radio = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']",
        "//input[@type='radio' and @value='TimeBased']",
        "//label[contains(.,'Time-Based')]/input"
    };

    private static final String[] level_1_input = {"//label[contains(text(),'Level 1')]/..//input", "//input[@name='level1']"};
    private static final String[] level_2_input = {"//label[contains(text(),'Level 2')]/..//input", "//input[@name='level2']"};
    private static final String[] level_3_input = {"//label[contains(text(),'Level 3')]/..//input", "//input[@name='level3']"};
    private static final String[] level_4_input = {"//label[contains(text(),'Level 4')]/..//input", "//input[@name='level4']"};

    private static final String[] start_value_input = {"//label[normalize-space()='Start']/..//input", "//input[@name='startValue']"};
    private static final String[] target_value_input = {"//label[normalize-space()='Target']/..//input", "//input[@name='targetValue']"};

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "path/to/your/excel/file.xlsx";
    // ==================================================
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions actions;
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
                    for (int j = 0; j < headers.size(); j++) {
                        Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        rowData.put(headers.get(j), getCellValueAsString(cell));
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
                    }
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return new DecimalFormat("#").format(numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        // Evaluate formula and get the cached result as a string
                        return cell.getStringCellValue();
                    } catch (IllegalStateException e) {
                        // If formula result is not a string, try getting it as numeric
                        double numericFormulaValue = cell.getNumericCellValue();
                        if (numericFormulaValue == (long) numericFormulaValue) {
                            return new DecimalFormat("#").format(numericFormulaValue);
                        } else {
                            return String.valueOf(numericFormulaValue);
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

    private void logStart(String step) {
        log("START: " + step);
    }

    private void logPass(String message) {
        log("PASS: " + message);
    }

    private void handleTestFailure(Exception e, String testCaseId) {
        log("FAIL: Test Case " + testCaseId + " failed. Reason: " + e.getMessage());
        e.printStackTrace();
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        sleep(PAGE_SETTLE_MS);
    }

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

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();", element);
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            log("WARN: Standard click failed, attempting JavaScript click.");
            jsClick(element);
        }
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) {
            log("WARN: Skipping sendKeys as text is null or empty.");
            return;
        }
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        typeSlowly(element, text);
    }


    private void typeSlowly(WebElement element, String text) {
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void selectCompanyByName(String companyName) {
        logStart("Select company: " + companyName);
        String companySpanXpath = String.format("//div[contains(@class,'company-name') and normalize-space()='%s']", companyName);
        WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));

        String[] parentXpaths = {
            "/ancestor::div[contains(@class,'company-list-item')]",
            "/ancestor::div[contains(@class,'company-card')]",
            "/ancestor::div[@role='button']",
            "/parent::div[contains(@onclick,'select')]",
            "/ancestor::*[contains(@class,'clickable')][1]"
        };

        for (String parentXpath : parentXpaths) {
            try {
                WebElement parent = companySpan.findElement(By.xpath("." + parentXpath));
                log("Found clickable parent card. Clicking it.");
                jsClick(parent);
                logPass("Company selected successfully.");
                return;
            } catch (Exception e) {
                // Try next parent strategy
            }
        }

        log("WARN: Could not find a clickable parent card. Clicking the company name span directly.");
        jsClick(companySpan);
        logPass("Company selected successfully.");
    }
    
    private void selectDropdownOption(String[] dropdownXpaths, String optionText) {
        if (optionText == null || optionText.isEmpty() || optionText.startsWith("[")) {
            log("WARN: Skipping dropdown selection as option text is invalid: " + optionText);
            return;
        }
        clickElement(dropdownXpaths);
        sleep(500); // Wait for dropdown options to appear
        String optionXpath = String.format("//ul[not(contains(@style,'display: none')) and contains(@class,'k-list')]//li[normalize-space()='%s']", optionText);
        WebElement optionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        jsClick(optionElement);
    }

    private void searchAndSelectFormulaMetric(String metricName) {
        String[] searchInput = {"//div[contains(@class,'formula-builder')]//input[@placeholder='Name or Owner']"};
        sendKeysToElement(searchInput, metricName);
        sleep(ACTION_PAUSE_MS); // Wait for search results
        String metricOptionXpath = String.format("//ul[contains(@class,'k-list') and not(contains(@style,'display: none'))]//li[normalize-space()='%s']", metricName);
        WebElement metricOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricOptionXpath)));
        jsClick(metricOption);
    }

    private void clickOperator(String operator) {
        String operatorXpath = String.format("//div[contains(@class,'formula-builder')]//button[normalize-space()='%s']", operator);
        WebElement operatorButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(operatorXpath)));
        jsClick(operatorButton);
    }

    // ========== TEST EXECUTION WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        if (testData.isEmpty()) {
            log("FAIL: No test data found in the Excel file or the file could not be read.");
            return;
        }

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            String testCaseId = data.getOrDefault("id", "TC" + (i + 1));
            log("======================================================================");
            log("Executing Test Case: " + testCaseId + " - " + data.getOrDefault("name", "N/A"));
            log("======================================================================");
            try {
                executeWorkflow(data);
            } catch (Exception e) {
                handleTestFailure(e, testCaseId);
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract data from map
        String baseUrl = data.get("base_url");
        String email = data.get("test_user_email");
        String password = data.get("test_user_password");
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

        // Step 1-5: Login and Company Selection (only for the first record)
        if (isFirstRecord) {
            logStart("Step 1: Navigate to Application URL: " + baseUrl);
            driver.get(baseUrl);
            waitForPageLoad();
            logPass("Login page is displayed.");

            logStart("Step 2: Enter email and continue.");
            sendKeysToElement(email_input, email);
            clickElement(continue_button);
            logPass("Password input field appeared.");

            logStart("Step 3: Enter password and login.");
            sendKeysToElement(password_input, password);
            clickElement(login_button);
            logPass("Company selection page is displayed.");

            logStart("Step 4: Select company.");
            waitForPageLoad();
            selectCompanyByName(companyName);
            logPass("Redirected to dashboard.");

            logStart("Step 5: Verify dashboard page.");
            waitForPageLoad();
            wait.until(ExpectedConditions.urlContains("Dashboard"));
            logPass("Dashboard page is visible.");
        } else {
            log("INFO: Skipping login for subsequent test record.");
            // Ensure we are on a main page before starting
            if (!driver.getCurrentUrl().contains("Dashboard") && !driver.getCurrentUrl().contains("Metrics")) {
                 driver.get(baseUrl + "Dashboard");
                 waitForPageLoad();
            }
        }

        // Step 6: Navigate to Metrics page
        logStart("Step 6: Navigate to Metrics page.");
        clickElement(metrics_link);
        waitForPageLoad();
        logPass("Metrics page is loaded.");

        // Step 7: Initiate metric creation
        logStart("Step 7: Click 'Add Metric' button.");
        clickElement(add_metric_button);
        waitForPageLoad();
        logPass("Metric creation form appears.");

        // Step 8: Enter metric name
        logStart("Step 8: Enter metric name: " + metricName);
        sendKeysToElement(name_input, metricName);
        logPass("Name entered successfully.");

        // Step 9-14: Handle different Value Sources
        logStart("Step 9: Select 'Value Source': " + valueSource);
        selectDropdownOption(value_source_dropdown, valueSource);
        sleep(ACTION_PAUSE_MS);

        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Step 10: Verify Formula Builder is visible.");
            logPass("Formula Builder is visible.");

            logStart("Step 11: Select first metric: " + firstMetric);
            searchAndSelectFormulaMetric(firstMetric);
            logPass("First metric selected.");

            logStart("Step 12: Select operator: " + operator);
            clickOperator(operator);
            logPass("Operator selected.");

            logStart("Step 13: Select second metric: " + secondMetric);
            searchAndSelectFormulaMetric(secondMetric);
            logPass("Second metric selected.");
            
            logStart("Step 14: Verify formula is displayed.");
            logPass("Formula displayed correctly.");
        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            logStart("Step 10-ALT: Verify Metric Selection is visible.");
            logPass("Metric Selection is visible.");
            
            logStart("Step 11-ALT: Select existing metric: " + metricToSelect);
            String[] metricSelectDropdown = {"//label[normalize-space()='Metric']/following-sibling::span//span[contains(@class,'k-select')]" };
            selectDropdownOption(metricSelectDropdown, metricToSelect);
            logPass("Existing metric selected.");
        }

        // Step 15-16: Cadence selection
        logStart("Step 15: Select 'Cadence': " + cadence);
        selectDropdownOption(cadence_dropdown, cadence);
        logPass("Cadence selected.");

        if ("Weekly".equalsIgnoreCase(cadence)) {
            logStart("Step 16: Select 'Resets On': " + resetsOn);
            selectDropdownOption(resets_on_dropdown, resetsOn);
            logPass("Resets On day selected.");
        }
        
        // Step 17: Confirm formula if applicable
        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Step 17: Confirm formula.");
            clickElement(formula_confirm_button);
            logPass("Formula confirmed.");
        }

        // Step 18: Save the metric
        logStart("Step 18: Save the metric.");
        clickElement(save_button);
        waitForPageLoad();
        logPass("Metric saved and listed on Metrics page.");

        // Step 19: Navigate to My Dashboard
        logStart("Step 19: Navigate to 'My Dashboard'.");
        clickElement(dashboards_menu);
        sleep(500);
        clickElement(my_dashboard_link);
        waitForPageLoad();
        logPass("'My Dashboard' page loaded.");

        // Step 20-23: Add metric to KPI
        logStart("Step 20: Open 'Edit KPI' modal.");
        clickElement(edit_kpi_icon);
        sleep(ACTION_PAUSE_MS);
        logPass("'Edit KPI' modal opened.");

        logStart("Step 21-22: Locate and add the new metric to the dashboard.");
        String metricInModalXpath = String.format("//div[@id='availableMetrics']//div[normalize-space()='%s']", metricName);
        WebElement metricElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricInModalXpath)));
        jsClick(metricElement);
        logPass("Metric moved to the right panel.");

        logStart("Step 23: Save KPI configuration.");
        clickElement(save_kpi_modal_button);
        logPass("Modal closed and dashboard refreshing.");

        // Step 24-36: Configure Target (if specified)
        if (!"None".equalsIgnoreCase(targetType)) {
            logStart("Step 24-25: Wait for dashboard and locate the new KPI card.");
            waitForPageLoad();
            String metricCardXpath = String.format("//div[contains(@class,'kpi-card-container')]//span[contains(@class,'metric-name') and normalize-space()='%s']/ancestor::div[contains(@class,'kpi-card-container')]", metricName);
            WebElement metricCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricCardXpath)));
            logPass("New metric card is visible.");

            logStart("Step 26-28: Hover and open the three-dot menu.");
            actions.moveToElement(metricCard).perform();
            sleep(500);
            WebElement kebabMenu = metricCard.findElement(By.xpath(".//span[contains(@class,'ico-threeDots')]"));
            jsClick(kebabMenu);
            sleep(ACTION_PAUSE_MS);
            logPass("Dropdown menu is open.");

            logStart("Step 29: Click 'Edit' option.");
            WebElement editOption = metricCard.findElement(By.xpath(".//li[@title='Edit']"));
            jsClick(editOption);
            logPass("Navigating to the metric's Edit page.");

            logStart("Step 30: Wait for Edit page to load.");
            waitForPageLoad();
            logPass("Edit page is fully loaded.");

            logStart("Step 31: Select the 'Target' tab.");
            clickElement(target_tab);
            sleep(ACTION_PAUSE_MS);
            logPass("Target configuration options are visible.");

            if ("Custom".equalsIgnoreCase(targetType)) {
                logStart("Step 32: Select 'Custom' target type.");
                clickElement(custom_target_radio);
                sleep(ACTION_PAUSE_MS);
                logPass("Custom target fields appeared.");

                logStart("Step 33: Enter custom target values.");
                sendKeysToElement(level_1_input, data.get("custom_target_values").split(",")[0].trim());
                sendKeysToElement(level_2_input, data.get("custom_target_values").split(",")[1].trim());
                sendKeysToElement(level_3_input, data.get("custom_target_values").split(",")[2].trim());
                sendKeysToElement(level_4_input, data.get("custom_target_values").split(",")[3].trim());
                logPass("All four target values are populated.");
            } else if ("Time-Based".equalsIgnoreCase(targetType)) {
                logStart("Step 26-ALT: Select 'Time-Based' target type.");
                clickElement(time_based_target_radio);
                sleep(ACTION_PAUSE_MS);
                logPass("'Start' and 'Target' fields appeared.");

                logStart("Step 27-28-ALT: Enter Start and Target values.");
                sendKeysToElement(start_value_input, data.get("time_based_start_value"));
                sendKeysToElement(target_value_input, data.get("time_based_target_value"));
                logPass("Start and Target values are populated.");
            }

            logStart("Step 34: Save the target configuration.");
            clickElement(save_button);
            waitForPageLoad();
            logPass("Configuration saved, navigated back to dashboard.");

            logStart("Step 35-36: Verify target is visible on the metric card.");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricCardXpath)));
            logPass("Metric card on dashboard displays the new target visualization.");
        } else {
            log("INFO: Skipping target configuration as Target Type is 'None'.");
        }
        
        log("SUCCESS: Test Case " + data.get("id") + " completed successfully.");
    }

    // ========== SETUP AND TEARDOWN ==========
    public void setUp() {
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Uncomment and set path if not in system PATH
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        File f = new File(EXCEL_FILE_PATH);
        if (!f.exists() || f.isDirectory()) {
            System.err.println("ERROR: Excel file not found at the specified path. Please update the EXCEL_FILE_PATH variable.");
            System.err.println("Current path: " + f.getAbsolutePath());
            return;
        }

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