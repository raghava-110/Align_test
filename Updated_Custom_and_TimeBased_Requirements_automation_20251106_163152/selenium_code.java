package com.selenium.dataprovider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
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

    // ========== LOCATORS - GENERATED WITH HYBRID STRATEGY ==========
    private static final String[] EMAIL_INPUT_XPATHS = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[contains(@data-bind,'value: username')]",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email')]",
        "//label[contains(text(), 'Email')]/following-sibling::span//input"
    };

    private static final String[] CONTINUE_BUTTON_XPATHS = {
        "//button[contains(text(), 'Continue')]",
        "//button[@id='continueButton']",
        "//button[@type='submit']",
        "//button[contains(@class, 'continue')]"
    };

    private static final String[] PASSWORD_INPUT_XPATHS = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[contains(@data-bind,'value: password')]",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password')]"
    };

    private static final String[] LOGIN_BUTTON_XPATHS = {
        "//button[normalize-space()='Login']",
        "//button[@id='loginButton']",
        "//button[@type='submit']",
        "//button[contains(text(), 'Login')]"
    };

    private static final String[] METRICS_LINK_XPATHS = {
        "//div[@id='navigation-header']//a[normalize-space()='Metrics']",
        "//a[@href='/Metric']",
        "//nav//a[contains(text(), 'Metrics')]",
        "//div[contains(@class, 'main-nav')]//a[normalize-space()='Metrics']"
    };

    private static final String[] ADD_METRIC_BUTTON_XPATHS = {
        "//button[contains(text(), 'Add Metric')]",
        "//a[contains(text(), 'Add Metric')]",
        "//button[@id='addMetricButton']",
        "//button[contains(@class, 'add-metric')]"
    };

    private static final String[] METRIC_NAME_INPUT_XPATHS = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//input[@placeholder='Name of the Metric']",
        "//label[normalize-space()='Name']/following-sibling::div/input",
        "//input[contains(@class,'metric-name')]"
    };

    private static final String[] VALUE_SOURCE_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Value Source']/following-sibling::span[contains(@class, 'k-dropdown')]/span[@class='k-input-value-text']",
        "//label[normalize-space()='Value Source']/following-sibling::span//span[contains(@class, 'k-select')]"
    };

    private static final String[] FORMULA_SEARCH_METRIC_INPUT_XPATHS = {
        "//input[@data-bind='events: { keyup: calculated.onFilterChange }']",
        "//input[@placeholder='Name or Owner']",
        "//div[contains(@class, 'formula-builder')]//input[@type='text']"
    };

    private static final String[] CADENCE_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Cadence']/following-sibling::span[contains(@class, 'k-dropdown')]/span[@class='k-input-value-text']",
        "//label[normalize-space()='Cadence']/following-sibling::span//span[contains(@class, 'k-select')]"
    };

    private static final String[] RESETS_ON_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Resets On']/following-sibling::span[contains(@class, 'k-dropdown')]/span[@class='k-input-value-text']",
        "//label[normalize-space()='Resets On']/following-sibling::span//span[contains(@class, 'k-select')]"
    };
    
    private static final String[] FORMULA_CONFIRM_CHECKMARK_XPATHS = {
        "//span[@title='Validate and Calculate']",
        "//span[contains(@class, 'ico-checkmark')]"
    };

    private static final String[] SAVE_BUTTON_XPATHS = {
        "//button[@id='saveButton']",
        "//button[normalize-space()='Save']",
        "//button[contains(., 'Save') and contains(@class, 'primary')]"
    };

    private static final String[] MY_DASHBOARD_LINK_XPATHS = {
        "//div[@id='navigation-header']//a[normalize-space()='My Dashboard']",
        "//a[@href='/Dashboard']",
        "//nav//a[contains(text(), 'My Dashboard')]"
    };

    private static final String[] EDIT_KPI_ICON_XPATHS = {
        "//div[@id='myKpi']//span[@title='Edit Kpi']",
        "//h2[normalize-space()=\"My KPIs\"]/following-sibling::div//span[contains(@class, 'ico-edit')]"
    };

    private static final String[] KPI_MODAL_SEARCH_INPUT_XPATHS = {
        "//div[contains(@class, 'kpi-modal')]//input[@placeholder='Search']"
    };

    private static final String[] KPI_MODAL_SAVE_BUTTON_XPATHS = {
        "//div[contains(@class, 'k-window-content')]//button[normalize-space()='Save']"
    };
    
    private static final String[] TARGET_TAB_XPATHS = {
        "//a[normalize-space()='Target']",
        "//li/a[contains(text(), 'Target')]"
    };

    private static final String[] CUSTOM_TARGET_RADIO_XPATHS = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']",
        "//input[@name='targetTemplate' and @value='1']"
    };

    private static final String[] TIME_BASED_TARGET_RADIO_XPATHS = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']",
        "//input[@name='targetTemplate' and @value='2']"
    };

    // ========== CONFIGURATION ==========
    private static final String EXCEL_FILE_PATH = "C:/path/to/your/AlignTestData.xlsx"; // <-- CHANGE THIS PATH
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1500;
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

    // ========== SELENIUM HELPER METHODS ==========
    private void log(String message) {
        System.out.println(message);
    }

    private void logStart(String step) {
        log("--- " + step + " ---");
    }

    private void logPass(String message) {
        log("  [PASS] " + message);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            } catch (TimeoutException e) {
                // Ignore and try next xpath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided XPaths: " + String.join(", ", xpaths));
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    
    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    private void typeSlowly(WebElement element, String text) {
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = findElementWithFallbacks(xpaths);
        typeSlowly(element, text);
    }

    private void selectCompanyByName(String companyName) {
        logStart("Selecting company: " + companyName);
        String companySpanXpath = String.format("//div[contains(@class, 'company-list-item')]//span[normalize-space()='%s']", companyName);
        WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));

        String[] parentSelectors = {
            "/ancestor::div[contains(@class, 'company-list-item')]",
            "/ancestor::div[contains(@class, 'company-item')]",
            "/ancestor::div[@role='button']",
            "/parent::div[contains(@onclick, 'select')]",
            "/ancestor::*[contains(@class, 'clickable')]"
        };

        for (String selector : parentSelectors) {
            try {
                WebElement parent = companySpan.findElement(By.xpath("." + selector));
                log("  Found clickable parent. Clicking parent element.");
                parent.click();
                logPass("Company '" + companyName + "' selected via parent.");
                return;
            } catch (Exception e) {
                // Parent not found, try next selector
            }
        }

        log("  WARNING: Could not find a preferred clickable parent. Clicking the span directly.");
        companySpan.click();
        logPass("Company '" + companyName + "' selected via span.");
    }
    
    private void selectKendoDropdownOption(String[] dropdownXpaths, String optionText) {
        if (optionText == null || optionText.isEmpty()) return;
        clickElement(dropdownXpaths);
        sleep(500); // Wait for animation
        String optionXpath = String.format("//ul[contains(@class, 'k-list') and not(contains(@style, 'display: none'))]/li[normalize-space()='%s']", optionText);
        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));
        optionElement.click();
    }
    
    private void waitAndHover(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
        sleep(500); // Allow time for hover effect
    }

    // ========== TEST WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        if (testData.isEmpty()) {
            log("No test data found in the Excel file. Exiting.");
            return;
        }
        log("Found " + testData.size() + " test case(s) to execute.");

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("\n=====================================================================");
            log("Executing Test Case: " + (i + 1) + " - " + data.getOrDefault("name", "N/A"));
            log("=====================================================================");
            try {
                executeWorkflow(data);
                log("\nTEST CASE " + (i + 1) + " PASSED");
            } catch (Exception e) {
                log("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                log("TEST CASE " + (i + 1) + " FAILED: " + e.getMessage());
                e.printStackTrace();
                log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                // Optional: re-initialize driver for next test case
                cleanup();
                setUp();
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

        // --- Login and Navigation ---
        if (isFirstRecord) {
            logStart("Step 1: Navigate to Application URL");
            driver.get(baseUrl);
            logPass("Navigated to " + baseUrl);

            logStart("Step 2: Enter Email");
            sendKeysToElement(EMAIL_INPUT_XPATHS, email);
            clickElement(CONTINUE_BUTTON_XPATHS);
            logPass("Email entered and continued.");

            logStart("Step 3: Enter Password");
            sendKeysToElement(PASSWORD_INPUT_XPATHS, password);
            clickElement(LOGIN_BUTTON_XPATHS);
            logPass("Password entered and logged in.");
            
            sleep(PAGE_SETTLE_MS);

            logStart("Step 4: Select Company");
            selectCompanyByName(companyName);
            sleep(PAGE_SETTLE_MS);
            
            logStart("Step 5: Verify Dashboard");
            wait.until(ExpectedConditions.urlContains("Dashboard"));
            logPass("Successfully landed on the dashboard.");
        }

        logStart("Step 6: Navigate to Metrics Page");
        clickElement(METRICS_LINK_XPATHS);
        wait.until(ExpectedConditions.urlContains("Metric"));
        logPass("Navigated to Metrics page.");

        // --- Metric Creation ---
        logStart("Step 7: Click 'Add Metric'");
        clickElement(ADD_METRIC_BUTTON_XPATHS);
        logPass("Clicked 'Add Metric' button.");

        logStart("Step 8: Enter Metric Name");
        sendKeysToElement(METRIC_NAME_INPUT_XPATHS, metricName);
        logPass("Entered metric name: " + metricName);

        logStart("Step 9: Select Value Source");
        selectKendoDropdownOption(VALUE_SOURCE_DROPDOWN_XPATHS, valueSource);
        logPass("Selected value source: " + valueSource);
        sleep(ACTION_PAUSE_MS);

        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Step 11: Select First Metric for Formula");
            sendKeysToElement(FORMULA_SEARCH_METRIC_INPUT_XPATHS, firstMetric);
            String firstMetricXpath = String.format("//ul[contains(@class, 'k-list')]/li[normalize-space()='%s']", firstMetric);
            clickElement(new String[]{firstMetricXpath});
            logPass("Selected first metric: " + firstMetric);

            logStart("Step 12: Select Operator");
            String operatorXpath = String.format("//button[normalize-space()='%s']", operator);
            clickElement(new String[]{operatorXpath});
            logPass("Selected operator: " + operator);

            logStart("Step 13: Select Second Metric for Formula");
            sendKeysToElement(FORMULA_SEARCH_METRIC_INPUT_XPATHS, secondMetric);
            String secondMetricXpath = String.format("//ul[contains(@class, 'k-list')]/li[normalize-space()='%s']", secondMetric);
            clickElement(new String[]{secondMetricXpath});
            logPass("Selected second metric: " + secondMetric);
            
            logStart("Step 17: Confirm Formula");
            clickElement(FORMULA_CONFIRM_CHECKMARK_XPATHS);
            logPass("Formula confirmed.");

        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            logStart("Step 9 (ALT): Select Existing Metric");
            String metricDropdownXpath = "//label[normalize-space()='Metric']/following-sibling::span//span[contains(@class, 'k-select')]";
            selectKendoDropdownOption(new String[]{metricDropdownXpath}, metricToSelect);
            logPass("Selected existing metric: " + metricToSelect);
        }
        
        sleep(ACTION_PAUSE_MS);

        logStart("Step 15: Select Cadence");
        selectKendoDropdownOption(CADENCE_DROPDOWN_XPATHS, cadence);
        logPass("Selected cadence: " + cadence);

        if ("Weekly".equalsIgnoreCase(cadence)) {
            logStart("Step 16: Select 'Resets On' day");
            selectKendoDropdownOption(RESETS_ON_DROPDOWN_XPATHS, resetsOn);
            logPass("Selected resets on: " + resetsOn);
        }

        logStart("Step 18: Save Metric");
        clickElement(SAVE_BUTTON_XPATHS);
        wait.until(ExpectedConditions.urlContains("Metric"));
        logPass("Metric saved successfully.");
        sleep(PAGE_SETTLE_MS);

        // --- Add Metric to Dashboard ---
        logStart("Step 19: Navigate to My Dashboard");
        clickElement(MY_DASHBOARD_LINK_XPATHS);
        wait.until(ExpectedConditions.urlContains("Dashboard"));
        logPass("Navigated to My Dashboard.");

        logStart("Step 20: Open Edit KPI Modal");
        clickElement(EDIT_KPI_ICON_XPATHS);
        logPass("Clicked Edit KPI icon.");

        logStart("Step 21 & 22: Find and Add New Metric");
        String metricInModalXpath = String.format("//div[contains(@class, 'available-metrics')]//div[normalize-space()='%s']", metricName);
        clickElement(new String[]{metricInModalXpath});
        logPass("Added metric '" + metricName + "' to KPIs.");

        logStart("Step 23: Save KPI Configuration");
        clickElement(KPI_MODAL_SAVE_BUTTON_XPATHS);
        logPass("Saved KPI configuration.");
        sleep(PAGE_SETTLE_MS);

        // --- Configure Target ---
        if (!"None".equalsIgnoreCase(targetType)) {
            logStart("Step 24 & 25: Locate the new metric card");
            String cardXpath = String.format("//div[contains(@class, 'kpi-card-container')][.//span[normalize-space()='%s']]", metricName);
            List<WebElement> cards = driver.findElements(By.xpath(cardXpath));
            if (cards.isEmpty()) throw new NoSuchElementException("Could not find metric card for: " + metricName);
            WebElement metricCard = cards.get(cards.size() - 1); // Get the last one, likely the newest
            js.executeScript("arguments[0].scrollIntoView(true);", metricCard);
            logPass("Located metric card for '" + metricName + "'.");

            logStart("Step 26: Hover over card to reveal menu");
            waitAndHover(metricCard);
            logPass("Hovered over metric card.");

            logStart("Step 27: Click three-dot menu");
            WebElement threeDotMenu = metricCard.findElement(By.xpath(".//*[contains(@class, 'kpicard-dropdown-menu')]//button"));
            jsClick(threeDotMenu);
            logPass("Clicked three-dot menu.");
            sleep(ACTION_PAUSE_MS);

            logStart("Step 27: Click 'Edit' option");
            WebElement editOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[contains(@class, 'k-menu-group')]//li//span[normalize-space()='Edit']")));
            editOption.click();
            logPass("Clicked 'Edit'.");
            wait.until(ExpectedConditions.urlContains("Edit"));
            sleep(PAGE_SETTLE_MS);

            logStart("Step 28: Select 'Target' tab");
            clickElement(TARGET_TAB_XPATHS);
            logPass("Switched to Target tab.");

            if ("Custom".equalsIgnoreCase(targetType)) {
                logStart("Step 29: Select 'Custom' target type");
                clickElement(CUSTOM_TARGET_RADIO_XPATHS);
                logPass("Selected 'Custom' radio button.");
                
                String[] customValues = data.get("custom_target_values").split(",");
                for (int i = 0; i < customValues.length; i++) {
                    logStart("Step 30-" + (i+1) + ": Enter Level " + (i+1) + " target");
                    String levelInputXpath = String.format("(//div[contains(@class, 'target-levels')]//input[@type='text'])[%d]", i + 1);
                    sendKeysToElement(new String[]{levelInputXpath}, customValues[i].trim());
                    logPass("Entered " + customValues[i].trim() + " for Level " + (i+1));
                }
            } else if ("Time-Based".equalsIgnoreCase(targetType)) {
                logStart("Step 19 (ALT): Select 'Time-Based' target type");
                clickElement(TIME_BASED_TARGET_RADIO_XPATHS);
                logPass("Selected 'Time-Based' radio button.");

                logStart("Step 20 (ALT): Enter Start Value");
                String startInputXpath = "//label[normalize-space()='Start']/following-sibling::span//input";
                sendKeysToElement(new String[]{startInputXpath}, data.get("time_based_start_value"));
                logPass("Entered start value: " + data.get("time_based_start_value"));

                logStart("Step 21 (ALT): Enter Target Value");
                String targetInputXpath = "//label[normalize-space()='Target']/following-sibling::span//input";
                sendKeysToElement(new String[]{targetInputXpath}, data.get("time_based_target_value"));
                logPass("Entered target value: " + data.get("time_based_target_value"));
            }

            logStart("Step 34: Save Target Configuration");
            clickElement(SAVE_BUTTON_XPATHS);
            wait.until(ExpectedConditions.urlContains("Dashboard"));
            logPass("Target configuration saved.");
            sleep(PAGE_SETTLE_MS);

            logStart("Step 35: Final Verification");
            String finalCardXpath = String.format("//div[contains(@class, 'kpi-card-container')][.//span[normalize-space()='%s']]", metricName);
            WebElement finalCard = findElementWithFallbacks(new String[]{finalCardXpath});
            if (finalCard.isDisplayed()) {
                logPass("Metric card '" + metricName + "' is visible on the dashboard with target configured.");
            } else {
                throw new AssertionError("Final verification failed: Metric card not visible.");
            }
        } else {
            logPass("Target Type is 'None'. Skipping target configuration.");
        }
    }

    // ========== SETUP AND TEARDOWN ==========
    public void setUp() {
        log("Setting up WebDriver...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
        log("WebDriver setup complete.");
    }

    public void cleanup() {
        if (driver != null) {
            log("Closing WebDriver.");
            driver.quit();
            driver = null;
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