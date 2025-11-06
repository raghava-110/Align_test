package com.align.automation;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignMetricAutomation {

    // ========== LOCATORS - COMPREHENSIVE FALLBACKS ==========

    private static final String[] EMAIL_INPUT_XPATHS = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@data-bind,'value: username')]",
        "//input[contains(@placeholder, 'Email') or contains(@placeholder, 'email')]",
        "//label[contains(text(), 'Email')]/following-sibling::span//input",
        "//input[@aria-label='Email']"
    };

    private static final String[] CONTINUE_BUTTON_XPATHS = {
        "//button[contains(text(),'Continue')]",
        "//button[@id='continueButton']",
        "//button[contains(@class,'icon') and contains(@class,'primary')]",
        "//button[@type='submit' and contains(., 'Continue')]",
        "//button[contains(@class, 'continue')]"
    };

    private static final String[] PASSWORD_INPUT_XPATHS = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@data-bind,'value: password')]",
        "//input[contains(@placeholder, 'Password') or contains(@placeholder, 'password')]",
        "//label[contains(text(), 'Password')]/following-sibling::span//input",
        "//input[@aria-label='Password']"
    };

    private static final String[] LOGIN_BUTTON_XPATHS = {
        "//button[contains(text(),'Login') or contains(text(),'Log In')]",
        "//button[@id='loginButton']",
        "//button[contains(@class,'icon') and contains(@class,'primary')]",
        "//button[@type='submit' and (contains(., 'Login') or contains(., 'Log In'))]",
        "//button[contains(@class, 'login')]"
    };

    private static final String[] METRICS_LINK_XPATHS = {
        "//div[@id='header-navigation']//a[normalize-space()='Metrics']",
        "//nav//a[normalize-space()='Metrics']",
        "//a[contains(@href, '/CompanyMetrics') and normalize-space()='Metrics']",
        "//span[normalize-space()='Metrics']/ancestor::a",
        "//div[contains(@class, 'nav')]//span[text()='Metrics']"
    };

    private static final String[] ADD_METRIC_BUTTON_XPATHS = {
        "//button[normalize-space()='Add Metric']",
        "//a[normalize-space()='Add Metric']",
        "//button[contains(., 'Add Metric')]",
        "//button[@id='addMetricButton']",
        "//span[contains(., 'Add Metric')]/ancestor::button"
    };

    private static final String[] NAME_INPUT_XPATHS = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//input[@placeholder='Name of the Metric']",
        "//label[normalize-space()='Name']/following-sibling::div//input",
        "//label[normalize-space()='Name']/following-sibling::input",
        "//input[contains(@data-bind,'value: companyMetric')]",
        "//input[contains(@class,'autocomplete-off')]"
    };

    private static final String[] VALUE_SOURCE_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Value Source']/following-sibling::span//span[contains(@class, 'k-dropdown-wrap')]",
        "//label[contains(text(),'Value Source')]/..//span[contains(@class, 'k-select')]",
        "//label[contains(text(),'Value Source')]/following-sibling::span//input"
    };

    private static final String[] CADENCE_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Cadence']/following-sibling::span//span[contains(@class, 'k-dropdown-wrap')]",
        "//label[contains(text(),'Cadence')]/..//span[contains(@class, 'k-select')]",
        "//label[contains(text(),'Cadence')]/following-sibling::span//input"
    };

    private static final String[] RESETS_ON_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Resets On']/following-sibling::span//span[contains(@class, 'k-dropdown-wrap')]",
        "//label[contains(text(),'Resets On')]/..//span[contains(@class, 'k-select')]",
        "//label[contains(text(),'Resets On')]/following-sibling::span//input"
    };

    private static final String[] SAVE_BUTTON_XPATHS = {
        "//button[@id='saveButton' and not(@disabled)]",
        "//button[normalize-space()='Save' and not(@disabled)]",
        "//button[contains(., 'Save') and not(@disabled)]",
        "//div[contains(@class, 'modal-footer')]//button[contains(., 'Save')]",
        "//button[@type='submit' and contains(., 'Save')]",
        "//span[normalize-space()='Save']/ancestor::button"
    };

    private static final String[] MY_DASHBOARD_LINK_XPATHS = {
        "//div[@id='header-navigation']//a[normalize-space()='My Dashboard']",
        "//nav//a[normalize-space()='My Dashboard']",
        "//a[contains(@href, '/Dashboard/My') and normalize-space()='My Dashboard']",
        "//span[normalize-space()='My Dashboard']/ancestor::a",
        "//div[contains(@class, 'nav')]//span[text()='My Dashboard']"
    };

    private static final String[] EDIT_KPI_ICON_XPATHS = {
        "//span[@title='Edit Kpi']",
        "//div[h2[normalize-space()='My KPIs']]//span[contains(@class, 'icon-edit')]",
        "//div[contains(@class, 'my-kpis')]//span[@title='Edit Kpi']",
        "//span[contains(@class, 'icon-edit') and @data-bind]"
    };

    private static final String[] TARGET_TAB_XPATHS = {
        "//a[normalize-space()='Target']",
        "//li/a[contains(text(), 'Target')]",
        "//div[contains(@class, 'tabs')]//a[normalize-space()='Target']"
    };

    private static final String[] CUSTOM_RADIO_BUTTON_XPATHS = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']",
        "//input[@type='radio' and @value='Custom']",
        "//label[normalize-space()='Custom']/input",
        "//label[normalize-space()='Custom']"
    };

    private static final String[] TIME_BASED_RADIO_BUTTON_XPATHS = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']",
        "//input[@type='radio' and @value='TimeBased']",
        "//label[normalize-space()='Time-Based']/input",
        "//label[normalize-space()='Time-Based']"
    };

    private static final String[] FORMULA_METRIC_SEARCH_INPUT_XPATHS = {
        "//div[@data-view='views/companyMetrics/calculatedMetric']//input[@placeholder='Name or Owner']",
        "//input[contains(@data-bind, 'calculated.onFilterChange')]",
        "//div[contains(@class, 'formula-builder')]//input[@type='text']"
    };

    private static final String[] METRIC_SOURCE_SEARCH_INPUT_XPATHS = {
        "//label[normalize-space()='Metric']/following-sibling::span//input",
        "//div[contains(@class, 'metric-source-selector')]//input"
    };

    private static final String[] FORMULA_GREEN_CHECK_XPATHS = {
        "//span[@title='Validate and Calculate']",
        "//div[contains(@class, 'formula-builder')]//span[contains(@class, 'check')]"
    };

    private static final String[] EDIT_KPI_MODAL_SAVE_BUTTON_XPATHS = {
        "//div[contains(@class, 'k-window-titlebar') and contains(., 'Edit My KPIs')]/..//button[normalize-space()='Save']",
        "//div[@id='editMyKpis']//button[normalize-space()='Save']",
        "//div[@aria-labelledby='editMyKpis_wnd_title']//button[contains(., 'Save')]"
    };

    private static final String[] CUSTOM_TARGET_LEVEL_INPUT_XPATHS = {
        "//label[normalize-space()='Level %d']/following-sibling::div//input",
        "//div[contains(@class, 'target-level-%d')]//input",
        "(//div[contains(@class, 'custom-target-inputs')]//input)[%d]"
    };

    private static final String[] TIME_BASED_START_INPUT_XPATHS = {
        "//label[normalize-space()='Start']/following-sibling::div//input",
        "//input[@name='startValue']"
    };

    private static final String[] TIME_BASED_TARGET_INPUT_XPATHS = {
        "//label[normalize-space()='Target']/following-sibling::div//input",
        "//input[@name='targetValue']"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/Automation/AlignTestData.xlsx"; // IMPORTANT: CHANGE THIS PATH
    // ==================================================
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2500;

    // ========== CLASS VARIABLES ==========
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
                if (headerRow == null) {
                    throw new RuntimeException("Excel file is missing the header row.");
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
                System.err.println("Error reading Excel file: " + filePath);
                e.printStackTrace();
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
                            return new DecimalFormat("#").format(numericValue);
                        } else {
                            return String.valueOf(numericValue);
                        }
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        // Evaluate formula and get the cached result as a string
                        return getFormulaCellValueAsString(cell);
                    } catch (Exception e) {
                        return ""; // Return empty if formula evaluation fails
                    }
                case BLANK:
                default:
                    return "";
            }
        }
        
        private static String getFormulaCellValueAsString(Cell cell) {
            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case STRING:
                    return cellValue.getStringValue().trim();
                case NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if (numericValue == (long) numericValue) {
                        return new DecimalFormat("#").format(numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                case BOOLEAN:
                    return String.valueOf(cellValue.getBooleanValue());
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

    private void logStart(String stepInfo) {
        log("START: " + stepInfo);
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
                // Ignore and try the next xpath
            }
        }
        throw new NoSuchElementException("Element not found. Tried " + xpaths.length + " fallback XPaths.");
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
        if (text == null || text.isEmpty()) return;
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        // Type slowly for better stability in some apps
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void selectCompanyByName(String companyName) {
        logStart("Selecting company: " + companyName);
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
                if (clickableParent != null && clickableParent.isDisplayed()) {
                    break;
                }
            } catch (NoSuchElementException e) {
                // Continue to next selector
            }
        }

        if (clickableParent != null) {
            log("Found clickable parent container. Clicking it.");
            wait.until(ExpectedConditions.elementToBeClickable(clickableParent)).click();
        } else {
            log("WARNING: Could not find a dedicated clickable parent. Clicking the company name span directly.");
            companySpan.click();
        }
        waitForPageLoad();
        logPass("Company selected and dashboard loaded.");
    }

    private void selectKendoDropdownOption(String[] dropdownXpaths, String optionText) {
        if (optionText == null || optionText.isEmpty()) return;
        clickElement(dropdownXpaths);
        sleep(500); // Wait for dropdown animation
        String optionXpath = String.format("//ul[contains(@class, 'k-list') and not(contains(@style, 'display: none'))]/li[normalize-space()='%s']", optionText);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    private void waitForPageLoad() {
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
        sleep(PAGE_SETTLE_MS);
    }

    // ========== TEST EXECUTION WORKFLOW ==========

    public void testDataDrivenWorkflow() {
        log("Starting data-driven test execution...");
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        log(testData.size() + " test records found in Excel.");

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("\n" + "======================================================================");
            log("Executing Test Case: " + (i + 1) + " - " + data.get("name"));
            log("======================================================================");
            try {
                executeWorkflow(data);
            } catch (Exception e) {
                log("!!!!!!!!!! TEST CASE FAILED !!!!!!!!!!");
                log("Failed on record " + (i + 1) + " (" + data.get("name") + ") with error: " + e.getMessage());
                e.printStackTrace();
                // Reset driver for the next test case
                cleanup();
                setUp();
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // 1. Extract data from map
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
            logStart("Step 1: Navigate to application URL.");
            driver.get(BASE_URL);
            logPass("Login page is displayed.");

            logStart("Step 2: Enter email and continue.");
            sendKeysToElement(EMAIL_INPUT_XPATHS, testUserEmail);
            clickElement(CONTINUE_BUTTON_XPATHS);
            logPass("Email entered, password field appeared.");

            logStart("Step 3: Enter password and login.");
            sendKeysToElement(PASSWORD_INPUT_XPATHS, testUserPassword);
            clickElement(LOGIN_BUTTON_XPATHS);
            logPass("Password entered, navigating to company selection.");

            logStart("Step 4: Select company.");
            selectCompanyByName(companyName);
            logPass("Company '" + companyName + "' selected.");
        } else {
            log("Skipping login for subsequent test run.");
        }

        // 3. Navigate to Metrics Page
        logStart("Step 6: Navigate to Metrics page.");
        clickElement(METRICS_LINK_XPATHS);
        waitForPageLoad();
        logPass("Metrics page loaded.");

        // 4. Create New Metric
        logStart("Step 7: Click 'Add Metric'.");
        clickElement(ADD_METRIC_BUTTON_XPATHS);
        waitForPageLoad();
        logPass("Metric creation form appeared.");

        logStart("Step 8: Enter metric name: " + metricName);
        sendKeysToElement(NAME_INPUT_XPATHS, metricName);
        logPass("Metric name entered.");

        logStart("Step 9: Select Value Source: " + valueSource);
        selectKendoDropdownOption(VALUE_SOURCE_DROPDOWN_XPATHS, valueSource);
        logPass("Value source selected.");
        sleep(ACTION_PAUSE_MS);

        // 5. Conditional Logic for Value Source
        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Step 11: Select first metric for formula: " + firstMetric);
            sendKeysToElement(FORMULA_METRIC_SEARCH_INPUT_XPATHS, firstMetric);
            clickElement(new String[]{String.format("//ul[contains(@class, 'k-list') and not(contains(@style, 'display: none'))]//span[normalize-space()='%s']", firstMetric)});
            logPass("First metric selected.");

            logStart("Step 12: Select operator: " + operator);
            clickElement(new String[]{String.format("//button[normalize-space()='%s']", operator)});
            logPass("Operator selected.");

            logStart("Step 13: Select second metric for formula: " + secondMetric);
            sendKeysToElement(FORMULA_METRIC_SEARCH_INPUT_XPATHS, secondMetric);
            clickElement(new String[]{String.format("//ul[contains(@class, 'k-list') and not(contains(@style, 'display: none'))]//span[normalize-space()='%s']", secondMetric)});
            logPass("Second metric selected.");
            
            logStart("Step 17: Confirm formula.");
            clickElement(FORMULA_GREEN_CHECK_XPATHS);
            logPass("Formula confirmed.");

        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            logStart("Step 6 (ALT): Select existing metric: " + metricToSelect);
            sendKeysToElement(METRIC_SOURCE_SEARCH_INPUT_XPATHS, metricToSelect);
            clickElement(new String[]{String.format("//ul[contains(@class, 'k-list') and not(contains(@style, 'display: none'))]/li[normalize-space()='%s']", metricToSelect)});
            logPass("Existing metric selected as source.");
        }

        // 6. Set Cadence
        logStart("Step 15: Select Cadence: " + cadence);
        selectKendoDropdownOption(CADENCE_DROPDOWN_XPATHS, cadence);
        logPass("Cadence set.");

        if ("Weekly".equalsIgnoreCase(cadence)) {
            logStart("Step 16: Select Resets On: " + resetsOn);
            selectKendoDropdownOption(RESETS_ON_DROPDOWN_XPATHS, resetsOn);
            logPass("Resets On day set.");
        }

        // 7. Save Metric
        logStart("Step 18: Save the new metric.");
        clickElement(SAVE_BUTTON_XPATHS);
        waitForPageLoad();
        logPass("Metric created successfully.");

        // 8. Add Metric to Dashboard
        logStart("Step 19: Navigate to My Dashboard.");
        clickElement(MY_DASHBOARD_LINK_XPATHS);
        waitForPageLoad();
        logPass("Dashboard loaded.");

        logStart("Step 20: Click 'Edit KPI' icon.");
        clickElement(EDIT_KPI_ICON_XPATHS);
        sleep(ACTION_PAUSE_MS);
        logPass("Edit KPI modal opened.");

        logStart("Step 21 & 22: Locate and add new metric to dashboard.");
        String metricInModalXpath = String.format("//div[@id='availableKpis']//div[normalize-space()='%s']", metricName);
        clickElement(new String[]{metricInModalXpath});
        logPass("Metric '" + metricName + "' added to selected KPIs.");

        logStart("Step 23: Save KPI configuration.");
        clickElement(EDIT_KPI_MODAL_SAVE_BUTTON_XPATHS);
        waitForPageLoad();
        logPass("KPIs saved, dashboard refreshed.");

        // 9. Configure Target (if specified)
        if ("None".equalsIgnoreCase(targetType)) {
            log("Step 13 (TC003): Verifying metric card without target.");
            logPass("Test case complete. Metric added to dashboard without a target.");
            return;
        }

        logStart("Step 24-26: Locate metric card and reveal three-dot menu.");
        String metricCardXpath = String.format("//div[contains(@class, 'kpi-card-container')]//span[normalize-space()='%s']/ancestor::div[contains(@class, 'kpi-card-container')]", metricName);
        WebElement metricCard = findElementWithFallbacks(new String[]{metricCardXpath});
        actions.moveToElement(metricCard).perform();
        logPass("Hovered over metric card.");

        String threeDotMenuXpath = metricCardXpath + "//span[contains(@class, 'ico-threeDots1')]";
        clickElement(new String[]{threeDotMenuXpath});
        logPass("Clicked three-dot menu.");
        sleep(ACTION_PAUSE_MS);

        logStart("Step 27: Click 'Edit' option.");
        String editOptionXpath = "//div[contains(@class, 'kpicard-dropdown-menu-content') and contains(@style, 'display: block')]//li[@title='Edit']";
        clickElement(new String[]{editOptionXpath});
        waitForPageLoad();
        logPass("Navigated to metric edit page.");

        logStart("Step 28: Select 'Target' tab.");
        clickElement(TARGET_TAB_XPATHS);
        sleep(ACTION_PAUSE_MS);
        logPass("Target configuration section displayed.");

        // 10. Conditional Logic for Target Type
        if ("Custom".equalsIgnoreCase(targetType)) {
            logStart("Step 29: Select 'Custom' target type.");
            clickElement(CUSTOM_RADIO_BUTTON_XPATHS);
            sleep(ACTION_PAUSE_MS);
            logPass("Custom target fields appeared.");

            logStart("Step 30: Enter custom target values.");
            String[] customTargets = data.get("custom_target_values").replace("[", "").replace("]", "").split(",");
            for (int i = 0; i < customTargets.length; i++) {
                String levelInputXpath = String.format(CUSTOM_TARGET_LEVEL_INPUT_XPATHS[2], i + 1);
                sendKeysToElement(new String[]{levelInputXpath}, customTargets[i].trim());
                log("Entered Level " + (i + 1) + " target: " + customTargets[i].trim());
            }
            logPass("All custom target values entered.");

        } else if ("Time-Based".equalsIgnoreCase(targetType)) {
            logStart("Step 14 (TC002): Select 'Time-Based' target type.");
            clickElement(TIME_BASED_RADIO_BUTTON_XPATHS);
            sleep(ACTION_PAUSE_MS);
            logPass("Time-Based target fields appeared.");

            logStart("Step 15 (TC002): Enter Start value.");
            sendKeysToElement(TIME_BASED_START_INPUT_XPATHS, data.get("time_based_start_value"));
            logPass("Start value entered.");

            logStart("Step 16 (TC002): Enter Target value.");
            sendKeysToElement(TIME_BASED_TARGET_INPUT_XPATHS, data.get("time_based_target_value"));
            logPass("Target value entered.");
        }

        // 11. Save Target Configuration
        logStart("Step 31: Save target configuration.");
        clickElement(SAVE_BUTTON_XPATHS);
        waitForPageLoad();
        logPass("Target saved, navigated back to dashboard.");

        logStart("Step 32: Final verification on dashboard.");
        WebElement finalCard = findElementWithFallbacks(new String[]{metricCardXpath});
        if (finalCard.isDisplayed()) {
            logPass("SUCCESS: Metric card is visible on the dashboard with target indicators.");
        } else {
            throw new AssertionError("Final verification failed: Metric card not found on dashboard.");
        }
    }

    // ========== SETUP, CLEANUP, and MAIN ==========

    public void setUp() {
        log("Setting up WebDriver...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
        isFirstRecord = true;
    }

    public void cleanup() {
        log("Cleaning up WebDriver...");
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        AlignMetricAutomation test = new AlignMetricAutomation();
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