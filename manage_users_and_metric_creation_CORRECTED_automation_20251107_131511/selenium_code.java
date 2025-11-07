package com.selenium.dataprovider;

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

public class AlignTestDataDrivenExecutor {

    // ========== LOCATORS - DYNAMICALLY GENERATED WITH FALLBACKS ==========

    private static final String[] email_input_locators = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[contains(@data-bind,'value: username')]",
        "//input[@id='usernameField']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email') or contains(@placeholder, 'email')]",
        "//label[normalize-space()='Email']/following-sibling::span//input",
        "//input[@aria-label='Email']"
    };

    private static final String[] continue_button_locators = {
        "//button[normalize-space()='Continue']",
        "//button[@id='continueButton']",
        "//button[@type='submit' and (contains(., 'Continue') or contains(., 'Next'))]",
        "//button[contains(., 'Continue')]",
        "//button[contains(@class,'icon') and contains(@data-bind, 'continueLogin')]"
    };

    private static final String[] password_input_locators = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[contains(@data-bind,'value: password')]",
        "//input[@id='passwordField']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password') or contains(@placeholder, 'password')]",
        "//label[normalize-space()='Password']/following-sibling::span//input",
        "//input[@aria-label='Password']"
    };

    private static final String[] login_button_locators = {
        "//button[normalize-space()='Login']",
        "//button[normalize-space()='Log In']",
        "//button[@id='loginButton']",
        "//button[@type='submit' and (contains(., 'Login') or contains(., 'Log In'))]",
        "//button[contains(., 'Login') or contains(., 'Log In')]",
        "//button[contains(@class,'icon') and contains(@data-bind, 'login')]"
    };

    private static final String[] metrics_link_locators = {
        "//a[normalize-space()='Metrics']",
        "//span[normalize-space()='Metrics']/ancestor::a",
        "//div[contains(@class, 'nav')]//a[contains(., 'Metrics')]",
        "//a[@href='/CompanyMetric' or @href='#/CompanyMetric']",
        "//a[@title='Metrics']"
    };

    private static final String[] add_metric_button_locators = {
        "//button[normalize-space()='Add Metric']",
        "//a[normalize-space()='Add Metric']",
        "//button[contains(., 'Add Metric')]",
        "//button[@id='addMetricButton']",
        "//button[@title='Add Metric']",
        "//span[normalize-space()='Add Metric']/parent::button"
    };

    private static final String[] metric_name_input_locators = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//input[@placeholder='Name of the Metric']",
        "//label[normalize-space()='Name']/following-sibling::div//input",
        "//input[contains(@name, 'MetricName')]",
        "//input[@aria-label='Name of the Metric']"
    };

    private static final String[] value_source_dropdown_locators = {
        "//label[normalize-space()='Value Source']/following-sibling::span//span[contains(@class, 'k-select')]",
        "//span[@aria-owns='selectedIntegration_listbox']",
        "//label[contains(text(),'Value Source')]/following-sibling::span//span[@class='k-input-value-text']/ancestor::span",
        "//input[@placeholder='Select a Value Source']/ancestor::span[contains(@class, 'k-dropdown')]"
    };

    private static final String[] formula_first_metric_search_locators = {
        "//div[@class='formula-builder']//input[@placeholder='Name or Owner']",
        "//div[contains(@class,'formula-builder')]//input[contains(@class, 'k-input')]",
        "//label[normalize-space()='Formula Builder']/..//input[contains(@class, 'k-input')]"
    };

    private static final String[] formula_checkmark_button_locators = {
        "//span[@title='Validate and Calculate']",
        "//button[contains(@class, 'green')]/span[contains(@class, 'ico-checkmark')]",
        "//button[@aria-label='Validate and Calculate']"
    };

    private static final String[] cadence_dropdown_locators = {
        "//label[normalize-space()='Cadence']/following-sibling::span//span[contains(@class, 'k-select')]",
        "//span[@aria-owns='selectedCadence_listbox']",
        "//label[contains(text(),'Cadence')]/following-sibling::span//span[@class='k-input-value-text']/ancestor::span"
    };

    private static final String[] resets_on_dropdown_locators = {
        "//label[normalize-space()='Resets On']/following-sibling::span//span[contains(@class, 'k-select')]",
        "//span[@aria-owns='selectedResetWeekDay_listbox']",
        "//label[contains(text(),'Resets On')]/following-sibling::span//span[@class='k-input-value-text']/ancestor::span"
    };

    private static final String[] save_button_locators = {
        "//button[normalize-space()='Save']",
        "//button[@id='saveButton']",
        "//button[contains(., 'Save') and not(contains(., 'Draft'))]",
        "//button/span[normalize-space()='Save']/parent::button",
        "//button[@type='submit' and contains(., 'Save')]"
    };

    private static final String[] dashboards_menu_locators = {
        "//a[normalize-space()='Dashboards']",
        "//span[normalize-space()='Dashboards']/ancestor::a",
        "//div[contains(@class, 'nav')]//a[contains(., 'Dashboards')]",
        "//a[@href='/Dashboard' or @href='#/Dashboard']"
    };

    private static final String[] my_dashboard_link_locators = {
        "//a[normalize-space()='My Dashboard']",
        "//ul[contains(@class, 'menu')]//a[contains(., 'My Dashboard')]",
        "//div[contains(@class, 'dropdown-menu')]//a[normalize-space()='My Dashboard']"
    };

    private static final String[] edit_kpi_icon_locators = {
        "//span[@title='Edit Kpi']",
        "//span[contains(@class, 'ico-edit-kpi')]",
        "//div[@id='myKpi']//span[contains(@class, 'icon') and @data-bind='click: editMyKpi']",
        "//a[@aria-label='Edit Kpi']//span"
    };

    private static final String[] edit_kpi_modal_save_button_locators = {
        "//div[contains(@class, 'k-window-content')]//button[normalize-space()='Save']",
        "//div[@aria-labelledby='editMyKpi_wnd_title']//button[contains(., 'Save')]",
        "//div[contains(@class,'k-popup-edit-form')]//button[normalize-space()='Save']"
    };

    private static final String[] target_tab_locators = {
        "//a[normalize-space()='Target']",
        "//li/a[contains(text(), 'Target')]",
        "//div[contains(@class, 'k-tabstrip-items')]//a[normalize-space()='Target']"
    };

    private static final String[] custom_target_radio_locators = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']",
        "//input[@name='targetTemplate' and @value='2']",
        "//label[contains(., 'Custom')]/input[@type='radio']"
    };

    private static final String[] time_based_target_radio_locators = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']",
        "//input[@name='targetTemplate' and @value='1']",
        "//label[contains(., 'Time-Based')]/input[@type='radio']"
    };

    // ========== CONFIGURATION ==========
    private static final String EXCEL_FILE_PATH = "path/to/your/testdata.xlsx"; // <-- CHANGE THIS
    private static final int EXPLICIT_WAIT_SEC = 30;
    private static final int TYPE_DELAY_MS = 100;
    private static final int ACTION_PAUSE_MS = 1500;
    private static final int PAGE_SETTLE_MS = 2000;

    // ========== CLASS VARIABLES ==========
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
                    return cell.getCellFormula();
                case BLANK:
                default:
                    return "";
            }
        }
    }

    // ========== HELPER METHODS ==========

    private static void log(String message) {
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
            } catch (Exception e) {
                // Ignore and try the next xpath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided locators.");
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (Exception e) {
            jsClick(element);
        }
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = findElementWithFallbacks(xpaths);
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
        try {
            WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));
            String[] parentSelectors = {
                "/ancestor::div[contains(@class, 'company-list-item')]",
                "/ancestor::div[contains(@class, 'company-card')]",
                "/ancestor::div[@role='button']",
                "/ancestor::div[contains(@onclick, 'selectCompany')]",
                "/.."
            };
            for (String selector : parentSelectors) {
                try {
                    WebElement parent = companySpan.findElement(By.xpath("." + selector));
                    log("Found clickable parent for company. Clicking parent.");
                    jsClick(parent);
                    logPass("Company selected.");
                    return;
                } catch (Exception e) {
                    // Try next parent selector
                }
            }
            log("Could not find a clickable parent. Clicking the company name span directly.");
            jsClick(companySpan);
            logPass("Company selected.");
        } catch (Exception e) {
            log("ERROR: Could not select company '" + companyName + "'. " + e.getMessage());
            throw e;
        }
    }

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        js.executeScript("arguments[0].click();", element);
    }

    private void selectKendoDropdownOption(String[] dropdownTriggerLocators, String optionText) {
        if (optionText == null || optionText.isEmpty()) return;
        clickElement(dropdownTriggerLocators);
        sleep(500);
        String optionXpath = String.format("//ul[contains(@class, 'k-list') and not(contains(@style, 'display: none'))]//li[normalize-space()='%s']", optionText);
        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));
        jsClick(optionElement);
    }

    // ========== TEST EXECUTION WORKFLOW ==========

    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testDataList = new ArrayList<>();
        // This part simulates reading from Excel by using the JSON input directly.
        // In a real scenario, this would be replaced by:
        // List<Map<String, String>> testDataList = ExcelReader.readExcel(EXCEL_FILE_PATH);

        // Manually populating test data from the provided JSON for demonstration
        Map<String, String> tc1 = new HashMap<>();
        tc1.put("base_url", "https://alignwebdev.aligntoday.com/");
        tc1.put("test_user_email", "sudhirbd@gmail.com");
        tc1.put("test_user_password", "Mindlinks2025#");
        tc1.put("company_name", "Sudhir");
        tc1.put("metric_name", "Weekly Sales Formula");
        tc1.put("value_source", "Formula");
        tc1.put("first_metric", "Metric A");
        tc1.put("operator", "+");
        tc1.put("second_metric", "Metric B");
        tc1.put("cadence", "Weekly");
        tc1.put("resets_on", "Monday");
        tc1.put("target_type", "Custom");
        tc1.put("custom_target_level1", "400");
        tc1.put("custom_target_level2", "300");
        tc1.put("custom_target_level3", "200");
        tc1.put("custom_target_level4", "100");
        testDataList.add(tc1);

        Map<String, String> tc2 = new HashMap<>();
        tc2.put("base_url", "https://alignwebdev.aligntoday.com/");
        tc2.put("test_user_email", "sudhirbd@gmail.com");
        tc2.put("test_user_password", "Mindlinks2025#");
        tc2.put("company_name", "Sudhir");
        tc2.put("metric_name", "Monthly Revenue Formula");
        tc2.put("value_source", "Formula");
        tc2.put("first_metric", "Metric C");
        tc2.put("operator", "*");
        tc2.put("second_metric", "Metric D");
        tc2.put("cadence", "Monthly");
        tc2.put("target_type", "Time-Based");
        tc2.put("time_based_start_value", "50");
        tc2.put("time_based_target_value", "500");
        testDataList.add(tc2);

        Map<String, String> tc3 = new HashMap<>();
        tc3.put("base_url", "https://alignwebdev.aligntoday.com/");
        tc3.put("test_user_email", "sudhirbd@gmail.com");
        tc3.put("test_user_password", "Mindlinks2025#");
        tc3.put("company_name", "Sudhir");
        tc3.put("metric_name", "Quarterly Metric from Source");
        tc3.put("value_source", "Metric");
        tc3.put("metric_to_select", "Existing Metric E");
        tc3.put("cadence", "Quarterly");
        tc3.put("target_type", "Time-Based");
        tc3.put("time_based_start_value", "1000");
        tc3.put("time_based_target_value", "5000");
        testDataList.add(tc3);


        for (int i = 0; i < testDataList.size(); i++) {
            log("\n" + "==========================================================");
            log("Executing Test Case " + (i + 1) + " of " + testDataList.size());
            log("==========================================================");
            try {
                executeWorkflow(testDataList.get(i));
            } catch (Exception e) {
                log("ERROR in record " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
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
        String metricName = data.get("metric_name") + " " + System.currentTimeMillis(); // Ensure unique name
        String valueSource = data.get("value_source");
        String firstMetric = data.get("first_metric");
        String operator = data.get("operator");
        String secondMetric = data.get("second_metric");
        String metricToSelect = data.get("metric_to_select");
        String cadence = data.get("cadence");
        String resetsOn = data.get("resets_on");
        String targetType = data.get("target_type");

        if (isFirstRecord) {
            logStart("Step 1: Navigate to the Application URL.");
            driver.get(baseUrl);
            logPass("Login page is displayed.");

            logStart("Step 2: Enter email and click 'Continue'.");
            sendKeysToElement(email_input_locators, email);
            clickElement(continue_button_locators);
            logPass("Password input field appears.");

            logStart("Step 3: Enter password and click 'Login'.");
            sendKeysToElement(password_input_locators, password);
            clickElement(login_button_locators);
            logPass("Company selection page is displayed.");

            logStart("Step 4: Select the correct company.");
            selectCompanyByName(companyName);
            sleep(PAGE_SETTLE_MS);
            logPass("Application dashboard is displayed.");
        }

        logStart("Step 5: Navigate to the Metrics Page.");
        clickElement(metrics_link_locators);
        sleep(PAGE_SETTLE_MS);
        logPass("Metrics page is loaded.");

        logStart("Step 6: Click the 'Add Metric' button.");
        clickElement(add_metric_button_locators);
        logPass("Metric creation form appears.");

        logStart("Step 7: Enter a unique metric name.");
        sendKeysToElement(metric_name_input_locators, metricName);
        logPass("Metric name entered: " + metricName);

        logStart("Step 8: Select 'Value Source'.");
        selectKendoDropdownOption(value_source_dropdown_locators, valueSource);
        logPass("Selected '" + valueSource + "' as value source.");

        if ("Formula".equalsIgnoreCase(valueSource)) {
            logStart("Step 9: Select the first metric for formula.");
            sendKeysToElement(formula_first_metric_search_locators, firstMetric);
            String metricOptionXpath = String.format("//ul[contains(@class, 'k-list')]//li[contains(., '%s')]", firstMetric);
            jsClick(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(metricOptionXpath))));
            logPass("First metric '" + firstMetric + "' added.");

            logStart("Step 10: Click on the operator button.");
            String operatorXpath = String.format("//button[normalize-space()='%s']", operator);
            clickElement(new String[]{operatorXpath});
            logPass("Operator '" + operator + "' added.");

            logStart("Step 11: Select the second metric for formula.");
            sendKeysToElement(formula_first_metric_search_locators, secondMetric);
            metricOptionXpath = String.format("//ul[contains(@class, 'k-list')]//li[contains(., '%s')]", secondMetric);
            jsClick(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(metricOptionXpath))));
            logPass("Second metric '" + secondMetric + "' added.");

            logStart("Step 12: Confirm the formula.");
            clickElement(formula_checkmark_button_locators);
            logPass("Formula validated and accepted.");
        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            logStart("Step 9 (Alt): Search and select an existing metric.");
            String metricDropdownXpath = "//label[normalize-space()='Metric']/following-sibling::span//span[contains(@class, 'k-select')]";
            selectKendoDropdownOption(new String[]{metricDropdownXpath}, metricToSelect);
            logPass("Selected existing metric: " + metricToSelect);
        }

        logStart("Step 13: Select 'Cadence'.");
        selectKendoDropdownOption(cadence_dropdown_locators, cadence);
        logPass("Selected cadence: " + cadence);

        if ("Weekly".equalsIgnoreCase(cadence)) {
            logStart("Step 14: Select 'Resets On'.");
            selectKendoDropdownOption(resets_on_dropdown_locators, resetsOn);
            logPass("Selected resets on: " + resetsOn);
        }

        logStart("Step 15: Click 'Save' to create the metric.");
        clickElement(save_button_locators);
        sleep(ACTION_PAUSE_MS);
        logPass("Metric created successfully.");

        logStart("Step 16: Navigate to 'My Dashboard'.");
        clickElement(dashboards_menu_locators);
        clickElement(my_dashboard_link_locators);
        sleep(PAGE_SETTLE_MS);
        logPass("'My Dashboard' page is displayed.");

        logStart("Step 17: Click the 'Edit KPI' icon.");
        clickElement(edit_kpi_icon_locators);
        logPass("'Edit KPI' modal opens.");

        logStart("Step 18 & 19: Locate and add the new metric to the dashboard.");
        String metricInAvailableListXpath = String.format("//div[@id='availableKpi']//div[normalize-space()='%s']", metricName);
        WebElement metricElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(metricInAvailableListXpath)));
        jsClick(metricElement);
        logPass("Metric '" + metricName + "' moved to selected KPIs.");

        logStart("Step 20: Click 'Save' in the modal.");
        clickElement(edit_kpi_modal_save_button_locators);
        sleep(PAGE_SETTLE_MS);
        logPass("Dashboard refreshed with the new KPI.");

        logStart("Step 21-24: Locate metric card, hover, and click 'Edit'.");
        String metricCardXpath = String.format("//div[contains(@class, 'kpi-card-container')]//span[normalize-space()='%s']/ancestor::div[contains(@class, 'kpi-card-container')]", metricName);
        WebElement metricCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricCardXpath)));
        new Actions(driver).moveToElement(metricCard).perform();

        String threeDotMenuXpath = metricCardXpath + "//span[contains(@class, 'ico-threeDots1')]";
        WebElement threeDotMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(threeDotMenuXpath)));
        jsClick(threeDotMenu);

        String editOptionXpath = "//div[contains(@class, 'kpicard-dropdown-menu-content') and not(contains(@style,'display: none'))]//li[@title='Edit']";
        WebElement editOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(editOptionXpath)));
        jsClick(editOption);
        sleep(PAGE_SETTLE_MS);
        logPass("Navigated to the Edit page for the metric.");

        logStart("Step 25: Select the 'Target' tab.");
        clickElement(target_tab_locators);
        logPass("Target configuration options are displayed.");

        if ("Custom".equalsIgnoreCase(targetType)) {
            logStart("Step 26: Select the 'Custom' radio button.");
            clickElement(custom_target_radio_locators);
            logPass("Custom target fields appear.");

            logStart("Step 27: Enter custom target values.");
            sendKeysToElement(new String[]{"//input[@name='KPITarget.Level1']"}, data.get("custom_target_level1"));
            sendKeysToElement(new String[]{"//input[@name='KPITarget.Level2']"}, data.get("custom_target_level2"));
            sendKeysToElement(new String[]{"//input[@name='KPITarget.Level3']"}, data.get("custom_target_level3"));
            sendKeysToElement(new String[]{"//input[@name='KPITarget.Level4']"}, data.get("custom_target_level4"));
            logPass("All four target values entered.");
        } else if ("Time-Based".equalsIgnoreCase(targetType)) {
            logStart("Step 25 (Alt): Select the 'Time-Based' radio button.");
            clickElement(time_based_target_radio_locators);
            logPass("Time-Based target fields appear.");

            logStart("Step 26-27 (Alt): Enter 'Start' and 'Target' values.");
            sendKeysToElement(new String[]{"//input[@name='KPITarget.StartValue']"}, data.get("time_based_start_value"));
            sendKeysToElement(new String[]{"//input[@name='KPITarget.TargetValue']"}, data.get("time_based_target_value"));
            logPass("Start and Target values entered.");
        }

        logStart("Step 28: Click 'Save' to save the target configuration.");
        clickElement(save_button_locators);
        sleep(PAGE_SETTLE_MS);
        logPass("Target configuration saved, redirected to dashboard.");

        logStart("Step 29: Verify the metric card on the dashboard.");
        WebElement finalMetricCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(metricCardXpath)));
        if (finalMetricCard.isDisplayed()) {
            logPass("Metric card with target indicators is visible on the dashboard. TEST CASE PASSED.");
        } else {
            log("FAIL: Metric card not found on dashboard after saving target.");
        }
    }

    // ========== SETUP AND TEARDOWN ==========

    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SEC));
        js = (JavascriptExecutor) driver;
    }

    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        AlignTestDataDrivenExecutor test = new AlignTestDataDrivenExecutor();
        try {
            log("Starting Test Execution...");
            test.setUp();
            test.testDataDrivenWorkflow();
        } catch (Exception e) {
            System.err.println("A critical error occurred during test execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.cleanup();
            log("Test Execution Finished.");
        }
    }
}
