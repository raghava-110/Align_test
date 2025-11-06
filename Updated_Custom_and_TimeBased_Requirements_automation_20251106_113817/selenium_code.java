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
import java.util.concurrent.TimeUnit;

public class AlignTestDataDriven {

    // ========== LOCATORS - GENERATED WITH HYBRID STRATEGY ==========
    private static final String[] email_input = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email')]",
        "//input[contains(@data-bind,'value: username')]",
        "//label[contains(normalize-space(),'Email')]/following-sibling::*/input",
        "//input[@aria-label='Email Address']"
    };

    private static final String[] continue_button = {
        "//button[contains(normalize-space(), 'Continue')]",
        "//button[@id='continueButton']",
        "//button[@type='submit']",
        "//button[contains(@class,'icon') and contains(@class,'primary')]",
        "//button[.//span[contains(text(),'Continue')]]"
    };

    private static final String[] password_input = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password')]",
        "//input[contains(@data-bind,'value: password')]",
        "//label[contains(normalize-space(),'Password')]/following-sibling::*/input",
        "//input[@aria-label='Password']"
    };

    private static final String[] login_button = {
        "//button[normalize-space()='Login']",
        "//button[@id='loginButton']",
        "//button[@type='submit']",
        "//button[contains(@class,'login')]",
        "//button[.//span[contains(text(),'Login')]]"
    };

    private static final String[] add_metric_button = {
        "//button[normalize-space()='Add Metric']",
        "//button[contains(., 'Add Metric')]",
        "//a[contains(., 'Add Metric')]",
        "//button[@id='add-metric']",
        "//button[contains(@class, 'add-metric')]",
        "//button[contains(@title, 'Add a new Metric')]",
        "//span[normalize-space()='Add Metric']/parent::button"
    };

    private static final String[] metric_name_input = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//input[@placeholder='Name of the Metric']",
        "//label[normalize-space()='Name']/following-sibling::input",
        "//input[contains(@data-bind,'value: companyMetric')]",
        "//div[label[normalize-space()='Name']]//input[@type='text']",
        "//input[contains(@class,'autocomplete-off') and @placeholder='Name of the Metric']"
    };

    private static final String[] value_source_dropdown = {
        "//label[normalize-space()='Value Source']/following-sibling::span//span[contains(@class, 'k-select')]",
        "//label[contains(text(),'Value Source')]/following-sibling::span//input",
        "//span[@aria-owns='selectedIntegration_listbox']",
        "//input[@placeholder='Select a Value Source']/ancestor::span[contains(@class,'k-dropdown')]"
    };

    private static final String[] formula_metric_search_input = {
        "//input[@placeholder='Name or Owner']",
        "//div[contains(@class, 'formula-builder')]//input[@type='text']",
        "//input[@data-bind='events: { keyup: calculated.onFilterChange }']",
        "//label[normalize-space()='Search Metric']/following-sibling::input"
    };

    private static final String[] cadence_dropdown = {
        "//label[normalize-space()='Cadence']/following-sibling::span//span[contains(@class, 'k-select')]",
        "//label[contains(text(),'Cadence')]/following-sibling::span//input",
        "//span[@aria-owns='selectedCadence_listbox']",
        "//input[@data-bind='value: selectedCadence, source:cadenceChoiceOptions, enabled:cadenceIsNonEditable, events: { change: clickChangeCadence }']/ancestor::span"
    };

    private static final String[] resets_on_dropdown = {
        "//label[normalize-space()='Resets On']/following-sibling::span//span[contains(@class, 'k-select')]",
        "//label[contains(text(),'Resets On')]/following-sibling::span//input",
        "//span[@aria-owns='selectedResetWeekDay_listbox']",
        "//input[contains(@data-bind, 'selectedResetWeekDay')]/ancestor::span"
    };

    private static final String[] confirm_formula_button = {
        "//span[@title='Validate and Calculate']",
        "//button[contains(@class, 'validate-formula')]",
        "//span[contains(@class, 'ico-checkmark')]/parent::button",
        "//button[@data-bind='click: calculated.validateFormula']"
    };

    private static final String[] save_button = {
        "//button[@id='saveButton']",
        "//button[normalize-space()='Save']",
        "//button[contains(., 'Save') and contains(@class, 'primary')]",
        "//button[@type='submit' and contains(., 'Save')]",
        "//div[@class='modal-footer']//button[contains(., 'Save')]",
        "//button[span[normalize-space()='Save']]"
    };

    private static final String[] edit_kpi_icon = {
        "//span[@title='Edit Kpi']",
        "//span[contains(@class, 'edit-kpi-icon')]",
        "//div[contains(@class, 'my-kpis-header')]//span[contains(@class, 'icon')]",
        "//a[@aria-label='Edit KPI']"
    };

    private static final String[] kpi_modal_search_input = {
        "//div[@id='editKpisModal']//input[@placeholder='Search']",
        "//div[contains(@class, 'modal-body')]//input[@type='search']",
        "//label[normalize-space()='Search Metric']/following-sibling::input"
    };

    private static final String[] target_tab = {
        "//a[normalize-space()='Target']",
        "//li/a[contains(text(), 'Target')]",
        "//div[@role='tablist']//a[contains(., 'Target')]"
    };

    private static final String[] custom_target_radio = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']",
        "//input[@name='targetTemplate' and @value='2']",
        "//label[normalize-space()='Custom']"
    };

    private static final String[] time_based_target_radio = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']",
        "//input[@name='targetTemplate' and @value='1']",
        "//label[normalize-space()='Time-Based']"
    };

    private static final String[] custom_target_level1_input = {"//input[@data-bind='value: companyMetric.KPITarget.Level1']", "//label[contains(., 'Level 1')]/following-sibling::input"};
    private static final String[] custom_target_level2_input = {"//input[@data-bind='value: companyMetric.KPITarget.Level2']", "//label[contains(., 'Level 2')]/following-sibling::input"};
    private static final String[] custom_target_level3_input = {"//input[@data-bind='value: companyMetric.KPITarget.Level3']", "//label[contains(., 'Level 3')]/following-sibling::input"};
    private static final String[] custom_target_level4_input = {"//input[@data-bind='value: companyMetric.KPITarget.Level4']", "//label[contains(., 'Level 4')]/following-sibling::input"};

    private static final String[] time_based_start_input = {"//input[@data-bind='value: companyMetric.KPITarget.StartValue']", "//label[normalize-space()='Start']/following-sibling::input"};
    private static final String[] time_based_target_input = {"//input[@data-bind='value: companyMetric.KPITarget.TargetValue']", "//label[normalize-space()='Target']/following-sibling::input"};
    
    private static final String[] metric_source_dropdown = {
        "//label[normalize-space()='Metric']/following-sibling::span//span[contains(@class, 'k-select')]",
        "//span[@aria-owns='selectedMetric_listbox']",
        "//input[@placeholder='Search Metric']/ancestor::span"
    };
    
    private static final String[] metric_source_search_input = {
        "//span[contains(@class, 'k-list-filter')]/input[@type='text']",
        "//div[contains(@class, 'k-animation-container')]//input[contains(@class, 'k-textbox')]"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/Users/Sudhir/Desktop/Align_test/Align_test/src/test/resources/testData/AlignTestData.xlsx";
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

    // ========== EXCEL READER UTILITY ==========
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
                System.err.println("Error reading Excel file: " + filePath);
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
                        // If formula result is numeric
                        double numericFormulaValue = cell.getNumericCellValue();
                        if (numericFormulaValue == Math.floor(numericFormulaValue)) {
                            return new DecimalFormat("#").format(numericFormulaValue);
                        }
                        return String.valueOf(numericFormulaValue);
                    }
                case BLANK:
                default:
                    return "";
            }
        }
    }

    // ========== HELPER METHODS ==========
    private void log(String message) { System.out.println(message); }
    private void logStart(String step) { log("--- " + step + " ---"); }
    private void logPass(String message) { log("  [PASS] " + message); }
    private void sleep(int milliseconds) { try { Thread.sleep(milliseconds); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return driver.findElement(By.xpath(xpath));
            } catch (NoSuchElementException e) {
                // Ignore and try the next one
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided XPaths. First XPath tried: " + xpaths[0]);
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
        logStart("Step 4: Select Company: " + companyName);
        String spanXpath = String.format("//span[@data-bind='text:getCompanyNameText' and normalize-space()='%s']", companyName);
        WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(spanXpath)));

        String[] parentSelectors = {
            "/ancestor::div[contains(@class, 'company-card')]",
            "/ancestor::div[contains(@class, 'company-item')]",
            "/ancestor::div[@role='button']",
            "/ancestor::div[contains(@onclick, 'selectCompany')]",
            "/ancestor::*[contains(@class, 'clickable')]"
        };

        for (String selector : parentSelectors) {
            try {
                WebElement parent = companySpan.findElement(By.xpath("." + selector));
                log("  Found clickable parent card. Clicking it.");
                jsClick(parent);
                logPass("Company selected successfully.");
                return;
            } catch (Exception e) {
                // Parent not found, try next selector
            }
        }

        log("  WARNING: Could not find a preferred clickable parent element. Clicking the company name span directly.");
        jsClick(companySpan);
        logPass("Company selected successfully.");
    }
    
    private void selectDropdownOptionByText(String text) {
        if (text == null || text.isEmpty()) return;
        String optionXpath = String.format("//ul[contains(@class, 'k-list') and not(contains(@style,'display: none'))]/li[normalize-space()='%s']", text);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        jsClick(option);
    }

    private void navigateTo(String menu, String subMenu) {
        logStart("Navigate to " + menu + (subMenu != null ? " -> " + subMenu : ""));
        String menuXpath = String.format("//div[@id='header-navigation']//a[normalize-space()='%s']", menu);
        WebElement menuElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(menuXpath)));
        
        Actions actions = new Actions(driver);
        actions.moveToElement(menuElement).perform();
        sleep(ACTION_PAUSE_MS / 2);

        if (subMenu != null) {
            menuElement.click(); // Sometimes a click is needed to keep menu open
            sleep(ACTION_PAUSE_MS / 2);
            String subMenuXpath = String.format("//div[contains(@class, 'sub-navigation')]//a[normalize-space()='%s']", subMenu);
            WebElement subMenuElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(subMenuXpath)));
            jsClick(subMenuElement);
        } else {
            jsClick(menuElement);
        }
        logPass("Navigated to " + (subMenu != null ? subMenu : menu));
        sleep(PAGE_SETTLE_MS);
    }

    // ========== TEST EXECUTION WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        log("Found " + testData.size() + " test case(s) in the Excel file.");
        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("\n=====================================================================");
            log("Executing Test Case: " + (i + 1) + " - " + data.get("name"));
            log("=====================================================================");
            try {
                executeWorkflow(data);
            } catch (Exception e) {
                log("  [FAIL] Test case failed with exception: " + e.getMessage());
                e.printStackTrace();
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // --- Login and Setup ---
        if (isFirstRecord) {
            logStart("Step 1: Navigate to Application URL");
            driver.get(BASE_URL);
            logPass("Navigated to " + BASE_URL);

            logStart("Step 2: Enter Email");
            sendKeysToElement(email_input, data.get("test_user_email"));
            clickElement(continue_button);
            logPass("Email entered and continued.");

            logStart("Step 3: Enter Password and Login");
            sendKeysToElement(password_input, data.get("test_user_password"));
            clickElement(login_button);
            logPass("Password entered and login clicked.");
            
            selectCompanyByName(data.get("company_name"));
            sleep(PAGE_SETTLE_MS);
        } else {
            log("Skipping login for subsequent test run. Navigating to dashboard.");
            navigateTo("Dashboards", "My Dashboard");
        }

        // --- Metric Creation ---
        navigateTo("Metrics", null);

        logStart("Step 6: Click 'Add Metric'");
        clickElement(add_metric_button);
        logPass("Add Metric form opened.");
        sleep(ACTION_PAUSE_MS);

        logStart("Step 7: Enter Metric Name");
        sendKeysToElement(metric_name_input, data.get("metric_name"));
        logPass("Entered metric name: " + data.get("metric_name"));

        String valueSource = data.get("value_source");
        logStart("Step 8: Select Value Source: " + valueSource);
        clickElement(value_source_dropdown);
        selectDropdownOptionByText(valueSource);
        logPass("Selected '" + valueSource + "' as value source.");
        sleep(ACTION_PAUSE_MS);

        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Step 9: Select First Metric for Formula");
            sendKeysToElement(formula_metric_search_input, data.get("first_metric"));
            selectDropdownOptionByText(data.get("first_metric"));
            logPass("Selected first metric: " + data.get("first_metric"));

            logStart("Step 10: Select Operator");
            String operatorXpath = String.format("//button[normalize-space()='%s']", data.get("operator"));
            clickElement(new String[]{operatorXpath});
            logPass("Selected operator: " + data.get("operator"));

            logStart("Step 11: Select Second Metric for Formula");
            sendKeysToElement(formula_metric_search_input, data.get("second_metric"));
            selectDropdownOptionByText(data.get("second_metric"));
            logPass("Selected second metric: " + data.get("second_metric"));
            
            logStart("Step 14: Confirm Formula");
            clickElement(confirm_formula_button);
            logPass("Formula confirmed.");
        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            logStart("Step 6 (ALT): Search and Select Existing Metric");
            clickElement(metric_source_dropdown);
            sleep(500);
            WebElement searchInput = findElementWithFallbacks(metric_source_search_input);
            typeSlowly(searchInput, data.get("metric_to_select"));
            selectDropdownOptionByText(data.get("metric_to_select"));
            logPass("Selected existing metric: " + data.get("metric_to_select"));
        }

        logStart("Step 12: Select Cadence");
        clickElement(cadence_dropdown);
        selectDropdownOptionByText(data.get("cadence"));
        logPass("Selected cadence: " + data.get("cadence"));

        if ("Weekly".equalsIgnoreCase(data.get("cadence"))) {
            logStart("Step 13: Select 'Resets On'");
            clickElement(resets_on_dropdown);
            selectDropdownOptionByText(data.get("resets_on"));
            logPass("Selected resets on: " + data.get("resets_on"));
        }

        logStart("Step 15: Save Metric");
        clickElement(save_button);
        logPass("Metric saved successfully.");
        sleep(PAGE_SETTLE_MS);

        // --- Add to Dashboard ---
        navigateTo("Dashboards", "My Dashboard");

        logStart("Step 17: Open Edit KPI Modal");
        clickElement(edit_kpi_icon);
        logPass("Edit KPI modal opened.");
        sleep(ACTION_PAUSE_MS);

        logStart("Step 18: Add new metric to dashboard");
        sendKeysToElement(kpi_modal_search_input, data.get("metric_name"));
        sleep(ACTION_PAUSE_MS);
        String metricInListXpath = String.format("//div[@id='availableKpis']//div[normalize-space()='%s']", data.get("metric_name"));
        clickElement(new String[]{metricInListXpath});
        logPass("Added '" + data.get("metric_name") + "' to selected KPIs.");

        logStart("Step 19: Save KPI Configuration");
        clickElement(save_button);
        logPass("KPI configuration saved.");
        sleep(PAGE_SETTLE_MS);

        // --- Configure Target (if specified) ---
        String targetType = data.get("target_type");
        if (targetType == null || targetType.isEmpty() || "None".equalsIgnoreCase(targetType)) {
            log("No target configuration required for this test case.");
            logPass("Test case completed successfully.");
            return;
        }

        logStart("Step 20-22: Locate and Edit new metric card");
        String metricCardXpath = String.format("//div[contains(@class, 'kpi-card-container')]//span[normalize-space()='%s']/ancestor::div[contains(@class, 'kpi-card-container')]", data.get("metric_name"));
        WebElement metricCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricCardXpath)));
        
        Actions actions = new Actions(driver);
        actions.moveToElement(metricCard).perform();
        sleep(500);
        
        WebElement threeDotMenu = metricCard.findElement(By.xpath(".//span[contains(@class, 'ico-threeDots1')]"));
        jsClick(threeDotMenu);
        sleep(ACTION_PAUSE_MS);
        
        WebElement editOption = driver.findElement(By.xpath("//ul[contains(@class, 'k-menu-group')]//li[contains(., 'Edit')]"));
        jsClick(editOption);
        logPass("Navigated to metric edit page.");
        sleep(PAGE_SETTLE_MS);

        logStart("Step 23: Select Target Tab");
        clickElement(target_tab);
        logPass("Target tab selected.");
        sleep(ACTION_PAUSE_MS);

        if ("Custom".equalsIgnoreCase(targetType)) {
            logStart("Step 24: Select 'Custom' Target");
            clickElement(custom_target_radio);
            logPass("Custom target type selected.");
            sleep(ACTION_PAUSE_MS);

            logStart("Step 25: Enter Custom Target Values");
            sendKeysToElement(custom_target_level1_input, data.get("custom_target_values_1"));
            sendKeysToElement(custom_target_level2_input, data.get("custom_target_values_2"));
            sendKeysToElement(custom_target_level3_input, data.get("custom_target_values_3"));
            sendKeysToElement(custom_target_level4_input, data.get("custom_target_values_4"));
            logPass("All four custom target values entered.");
        } else if ("Time-Based".equalsIgnoreCase(targetType)) {
            logStart("Step 16 (ALT): Select 'Time-Based' Target");
            clickElement(time_based_target_radio);
            logPass("Time-Based target type selected.");
            sleep(ACTION_PAUSE_MS);

            logStart("Step 17-18 (ALT): Enter Time-Based Values");
            sendKeysToElement(time_based_start_input, data.get("time_based_start_value"));
            sendKeysToElement(time_based_target_input, data.get("time_based_target_value"));
            logPass("Start and Target values entered.");
        }

        logStart("Step 26: Save Target Configuration");
        clickElement(save_button);
        logPass("Target configuration saved.");
        sleep(PAGE_SETTLE_MS);

        logStart("Step 27: Final Verification");
        WebElement finalMetricCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricCardXpath)));
        if (finalMetricCard.isDisplayed()) {
            logPass("Metric card is visible on the dashboard with new configuration.");
        } else {
            throw new RuntimeException("Verification failed: Metric card not found on dashboard after saving target.");
        }
    }

    // ========== SETUP AND TEARDOWN ==========
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Sudhir/Desktop/Align_test/Align_test/src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
            System.err.println("A critical error occurred during the test execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.cleanup();
        }
    }
}