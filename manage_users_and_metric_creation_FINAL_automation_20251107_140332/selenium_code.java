package com.selenium.datagenerator;

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

public class SeleniumDataDrivenTest {

    // ========== LOCATORS - GENERATED WITH HYBRID STRATEGY ==========
    private static final String[] EMAIL_INPUT_XPATHS = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']", "//input[contains(@data-bind,'value: username')]", "//input[@id='usernameField']", "//span[contains(@class,'k-widget')]//input[@id='usernameField']", "//span[contains(@class,'k-dropdown')]//input[@id='usernameField']", "//span[contains(@class,'k-combobox')]//input[@id='usernameField']", "//input[@name='username']", "//input[@type='email']", "//input[@id='email']", "//input[contains(@placeholder, 'Email')]"
    };
    private static final String[] CONTINUE_BUTTON_XPATHS = {
        "//button[contains(@class,'icon')]", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[normalize-space()='Continue']", "//button[contains(text(),'Continue')]", "//button[@id='continue-button']"
    };
    private static final String[] PASSWORD_INPUT_XPATHS = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']", "//input[contains(@data-bind,'value: password')]", "//input[@id='passwordField']", "//span[contains(@class,'k-widget')]//input[@id='passwordField']", "//span[contains(@class,'k-dropdown')]//input[@id='passwordField']", "//span[contains(@class,'k-combobox')]//input[@id='passwordField']", "//input[@name='password']", "//input[@type='password']", "//input[@id='password']", "//input[contains(@placeholder, 'Password')]"
    };
    private static final String[] LOGIN_BUTTON_XPATHS = {
        "//button[contains(@class,'icon')]", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[normalize-space()='Login']", "//button[contains(text(),'Login')]", "//button[@type='submit']"
    };
    private static final String[] ADMINISTRATION_MENU_XPATHS = {
        "//input[@data-bind='value: headerNavigation.selected, source: headerNavigation.headers, events: { change: headerNavigation.select }']", "//input[contains(@data-bind,'value: headerNavigation')]", "//input[@id='navigation-header-dropdown']", "//span[contains(@class,'k-widget')]//input[@id='navigation-header-dropdown']", "//span[contains(@class,'k-dropdown')]//input[@id='navigation-header-dropdown']", "//span[contains(@class,'k-combobox')]//input[@id='navigation-header-dropdown']", "//input[contains(@class,'header-dropdown')]", "//input[contains(@class,'header-dropdown') and contains(@class,'setMaxWidth')]", "//a[normalize-space()='Administration']", "//span[normalize-space()='Administration']"
    };
    private static final String[] MANAGE_USERS_LINK_XPATHS = {
        "//ul[contains(@style,'display: block')]//li[normalize-space()='Manage Users']", "//a[normalize-space()='Manage Users']", "//li/a[contains(text(),'Manage Users')]"
    };
    private static final String[] INVITE_USERS_BUTTON_XPATHS = {
        "//button[contains(@class,'icon')]", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[normalize-space()='Invite Users']", "//button[contains(text(),'Invite')]"
    };
    private static final String[] INVITE_USER_EMAIL_INPUT_XPATHS = {
        "//div[contains(@class,'modal-body')]//input[@type='email']", "//input[@id='inviteUserContentPopup_input_email']", "//label[contains(text(),'Email')]/following-sibling::input"
    };
    private static final String[] ADMIN_CHECKBOX_XPATHS = {
        "//label[normalize-space()='Admin']/preceding-sibling::input[@type='checkbox']", "//input[@id='inviteUserContentPopup_input_isAdmin']"
    };
    private static final String[] SEND_INVITE_BUTTON_XPATHS = {
        "//button[normalize-space()='Send Invite']", "//button[contains(text(),'Send')]"
    };
    private static final String[] ACCEPT_INVITE_ICON_XPATHS = {
        "//td[contains(text(),'%s')]/following-sibling::td//span[contains(@class,'ico-checkmark')]"
    };
    private static final String[] FIRST_NAME_INPUT_XPATHS = {
        "//input[@data-bind='value: promoCode.code']", "//input[contains(@data-bind,'value: promoCode')]", "//input[@id='inviteUserContentPopup_input_promoCode']", "//input[@type='text']", "//input[contains(@class,'input')]", "//label[@for='inviteUserContentPopup_input_promoCode']/following-sibling::input", "//label[@for='inviteUserContentPopup_input_promoCode']/..//input", "//input[@name='FirstName']", "//input[contains(@placeholder,'First Name')]"
    };
    private static final String[] LAST_NAME_INPUT_XPATHS = {
        "//input[@data-bind='value: promoCode.code']", "//input[contains(@data-bind,'value: promoCode')]", "//input[@id='inviteUserContentPopup_input_promoCode']", "//input[@type='text']", "//input[contains(@class,'input')]", "//label[@for='inviteUserContentPopup_input_promoCode']/following-sibling::input", "//label[@for='inviteUserContentPopup_input_promoCode']/..//input", "//input[@name='LastName']", "//input[contains(@placeholder,'Last Name')]"
    };
    private static final String[] USER_PASSWORD_INPUT_XPATHS = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']", "//input[contains(@data-bind,'value: password')]", "//input[@id='passwordField']", "//span[contains(@class,'k-widget')]//input[@id='passwordField']", "//span[contains(@class,'k-dropdown')]//input[@id='passwordField']", "//span[contains(@class,'k-combobox')]//input[@id='passwordField']", "//input[@name='password']", "//input[@type='password']", "//input[contains(@placeholder,'Create Password')]"
    };
    private static final String[] ACCEPT_INVITE_BUTTON_XPATHS = {
        "//button[contains(@class,'icon')]", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[normalize-space()='Accept Invite']"
    };
    private static final String[] METRICS_MENU_XPATHS = {
        "//a[normalize-space()='Metrics']", "//span[normalize-space()='Metrics']"
    };
    private static final String[] ADD_METRIC_BUTTON_XPATHS = {
        "//button[contains(@class,'icon')]", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[normalize-space()='Add Metric']"
    };
    private static final String[] METRIC_NAME_INPUT_XPATHS = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']", "//input[contains(@data-bind,'value: companyMetric')]", "//input[@type='text']", "//input[@placeholder='Name of the Metric']", "//input[contains(@placeholder,'Name of th')]", "//input[contains(@class,'autocomplete-off')]"
    };
    private static final String[] VALUE_SOURCE_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Value Source']/following-sibling::span//span[contains(@class,'k-select')]"
    };
    private static final String[] FORMULA_BUILDER_SEARCH_INPUT_XPATHS = {
        "//div[contains(@class,'formula-builder')]//input[@placeholder='Name or Owner']"
    };
    private static final String[] CADENCE_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Cadence']/following-sibling::span//span[contains(@class,'k-select')]"
    };
    private static final String[] RESETS_ON_DROPDOWN_XPATHS = {
        "//label[normalize-space()='Resets On']/following-sibling::span//span[contains(@class,'k-select')]"
    };
    private static final String[] FORMULA_CONFIRM_BUTTON_XPATHS = {
        "//span[@title='Validate and Calculate']"
    };
    private static final String[] SAVE_BUTTON_XPATHS = {
        "//button[contains(@class,'icon')]", "//button[contains(@class,'icon') and contains(@class,'ico-edit-')]", "//button[normalize-space()='Save']", "//button[@id='saveButton']"
    };
    private static final String[] DASHBOARDS_MENU_XPATHS = {
        "//a[normalize-space()='Dashboards']", "//span[normalize-space()='Dashboards']"
    };
    private static final String[] MY_DASHBOARD_LINK_XPATHS = {
        "//ul[contains(@style,'display: block')]//li[normalize-space()='My Dashboard']", "//a[normalize-space()='My Dashboard']"
    };
    private static final String[] EDIT_KPI_ICON_XPATHS = {
        "//span[contains(@class,'icon')]", "//span[@title='Edit Kpi']", "//div[@id='myKpi']//span[contains(@class,'ico-edit')]"
    };
    private static final String[] EDIT_KPI_MODAL_SEARCH_XPATHS = {
        "//div[contains(@class,'modal-body')]//input[@placeholder='Search']"
    };
    private static final String[] EDIT_KPI_MODAL_LEFT_PANEL_METRIC_XPATHS = {
        "//div[contains(@class,'left-panel')]//div[contains(@class,'metric-name') and normalize-space()='%s']"
    };
    private static final String[] EDIT_KPI_MODAL_SAVE_BUTTON_XPATHS = {
        "//div[contains(@class,'modal-footer')]//button[normalize-space()='Save']"
    };
    private static final String[] METRIC_CARD_THREE_DOT_MENU_XPATHS = {
        "//div[contains(@class,'kpi-card-container') and .//span[normalize-space()='%s']]//span[contains(@class,'ico-threeDots')]"
    };
    private static final String[] METRIC_CARD_EDIT_OPTION_XPATHS = {
        "//div[contains(@class,'kpicard-dropdown-menu-content') and contains(@style,'display: block')]//li[@title='Edit']"
    };
    private static final String[] TARGET_TAB_XPATHS = {
        "//a[normalize-space()='Target']"
    };
    private static final String[] CUSTOM_TARGET_RADIO_XPATHS = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']"
    };
    private static final String[] TIME_BASED_TARGET_RADIO_XPATHS = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']"
    };
    private static final String[] LEVEL_1_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.Level1']"};
    private static final String[] LEVEL_2_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.Level2']"};
    private static final String[] LEVEL_3_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.Level3']"};
    private static final String[] LEVEL_4_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.Level4']"};
    private static final String[] START_VALUE_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.StartValue']"};
    private static final String[] TARGET_VALUE_INPUT_XPATHS = {"//input[@data-bind='value: companyMetric.KPITarget.TargetValue']"};

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/path/to/your/test-data.xlsx";
    // ==================================================
    private static final String BASE_URL = "https://alignwebdev.aligntoday.com/";
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
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return new DecimalFormat("#.##").format(numericValue);
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
        throw new NoSuchElementException("Element not found with any of the provided XPaths.");
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
        log("Clicked element located by: " + xpaths[0]);
    }


    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) {
            log("Skipping sendKeys as text is null or empty for element: " + xpaths[0]);
            return;
        }
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
        log("Entered text '" + text + "' into element: " + xpaths[0]);
    }
    
    private void typeSlowly(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) {
            log("Skipping typeSlowly as text is null or empty for element: " + xpaths[0]);
            return;
        }
        WebElement element = findElementWithFallbacks(xpaths);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
        log("Slowly typed text '" + text + "' into element: " + xpaths[0]);
    }

    private void selectCompanyByName(String companyName) {
        log("Attempting to select company: " + companyName);
        String companySpanXpath = String.format("//span[@data-bind='text:getCompanyNameText' and normalize-space()='%s']", companyName);
        String[] parentCardXpaths = {
            "/ancestor::div[contains(@class,'card') and contains(@class,'company')]",
            "/ancestor::div[contains(@class,'company-item')]",
            "/ancestor::div[@role='button']",
            "/ancestor::a",
            "/.."
        };

        try {
            WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companySpanXpath)));
            WebElement clickableParent = null;
            for (String parentXpath : parentCardXpaths) {
                try {
                    clickableParent = companySpan.findElement(By.xpath("." + parentXpath));
                    if (clickableParent != null && clickableParent.isDisplayed() && clickableParent.isEnabled()) {
                        log("Found clickable parent card for company.");
                        clickableParent.click();
                        return;
                    }
                } catch (Exception e) {
                    // Parent not found, try next strategy
                }
            }
            log("WARNING: Could not find a clickable parent card. Clicking the company name span directly.");
            companySpan.click();
        } catch (Exception e) {
            log("ERROR: Could not find or click company: " + companyName);
            throw new RuntimeException("Failed to select company: " + companyName, e);
        }
    }

    private void selectDropdownOptionByText(String[] dropdownTriggerXpaths, String optionText) {
        if (optionText == null || optionText.isEmpty() || "N/A".equalsIgnoreCase(optionText)) {
            log("Skipping dropdown selection as option text is invalid for: " + dropdownTriggerXpaths[0]);
            return;
        }
        clickElement(dropdownTriggerXpaths);
        sleep(500);
        String optionXpath = String.format("//ul[contains(@style,'display: block')]//li[normalize-space()='%s']", optionText);
        clickElement(new String[]{optionXpath});
        log("Selected option '" + optionText + "' from dropdown.");
    }

    // ========== TEST EXECUTION WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        log("Found " + testData.size() + " records in the Excel file.");

        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("\n--- Starting Test Case Execution for Record #" + (i + 1) + " (ID: " + data.get("id") + ") ---");
            try {
                executeWorkflow(data);
                log("--- Test Case for Record #" + (i + 1) + " PASSED ---\n");
            } catch (Exception e) {
                log("XXX--- Test Case for Record #" + (i + 1) + " FAILED: " + e.getMessage() + " ---XXX\n");
                e.printStackTrace();
                // Reset state for the next test
                isFirstRecord = true; 
                cleanup();
                setUp();
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract data from map
        String testCaseId = data.get("id");
        String email = data.getOrDefault("Email", data.get("Email (Admin user)"));
        String password = data.getOrDefault("Password", data.get("Password (Admin user)"));
        String companyName = data.get("Company_Name");

        // Step 1-4: Login and Company Selection (only for the first record)
        if (isFirstRecord) {
            log("Executing login sequence for the first record.");
            driver.get(BASE_URL);
            sendKeysToElement(EMAIL_INPUT_XPATHS, email);
            clickElement(CONTINUE_BUTTON_XPATHS);
            sleep(ACTION_PAUSE_MS);
            sendKeysToElement(PASSWORD_INPUT_XPATHS, password);
            clickElement(LOGIN_BUTTON_XPATHS);
            sleep(PAGE_SETTLE_MS);
            selectCompanyByName(companyName);
            sleep(PAGE_SETTLE_MS);
        } else {
            log("Skipping login, assuming user is already logged in.");
            // Ensure we are on a dashboard page before proceeding
            if (!driver.getCurrentUrl().contains("dashboard")) {
                log("Not on dashboard, navigating to My Dashboard.");
                clickElement(DASHBOARDS_MENU_XPATHS);
                clickElement(MY_DASHBOARD_LINK_XPATHS);
                sleep(PAGE_SETTLE_MS);
            }
        }

        // Execute steps based on Test Case ID
        switch (Objects.requireNonNull(testCaseId)) {
            case "TC001":
            case "TC004":
                performUserOnboarding(data);
                if ("TC001".equals(testCaseId)) {
                    performMetricCreation(data);
                    performAddMetricToDashboard(data);
                    performTargetConfiguration(data);
                }
                break;
            case "TC002":
                performMetricCreation(data);
                break;
            case "TC003":
                performTargetConfiguration(data);
                break;
            default:
                log("ERROR: Unknown Test Case ID: " + testCaseId);
        }
    }

    private void performUserOnboarding(Map<String, String> data) {
        log("Starting User Onboarding flow.");
        String inviteUserEmail = data.get("Invite_User_Email");
        String isAdmin = data.get("Admin");
        String firstName = data.get("First_Name");
        String lastName = data.get("Last_Name");
        String userPassword = data.get("User_Password");

        clickElement(ADMINISTRATION_MENU_XPATHS);
        clickElement(MANAGE_USERS_LINK_XPATHS);
        sleep(PAGE_SETTLE_MS);
        clickElement(INVITE_USERS_BUTTON_XPATHS);
        sleep(ACTION_PAUSE_MS);
        sendKeysToElement(INVITE_USER_EMAIL_INPUT_XPATHS, inviteUserEmail);
        
        WebElement adminCheckbox = findElementWithFallbacks(ADMIN_CHECKBOX_XPATHS);
        if ("Yes".equalsIgnoreCase(isAdmin) && !adminCheckbox.isSelected()) {
            adminCheckbox.click();
            log("Set user as Admin.");
        } else if ("No".equalsIgnoreCase(isAdmin) && adminCheckbox.isSelected()) {
            adminCheckbox.click();
            log("Set user as non-Admin.");
        }

        clickElement(SEND_INVITE_BUTTON_XPATHS);
        sleep(PAGE_SETTLE_MS);
        
        String acceptIconXpath = String.format(ACCEPT_INVITE_ICON_XPATHS[0], inviteUserEmail);
        clickElement(new String[]{acceptIconXpath});
        sleep(ACTION_PAUSE_MS);

        sendKeysToElement(FIRST_NAME_INPUT_XPATHS, firstName);
        sendKeysToElement(LAST_NAME_INPUT_XPATHS, lastName);
        sendKeysToElement(USER_PASSWORD_INPUT_XPATHS, userPassword);
        clickElement(ACCEPT_INVITE_BUTTON_XPATHS);
        sleep(PAGE_SETTLE_MS * 2); // Wait for dashboard to load after invite
        log("User Onboarding flow completed successfully.");
    }

    private void performMetricCreation(Map<String, String> data) {
        log("Starting Metric Creation flow.");
        String metricName = data.get("Metric_Name");
        String valueSource = data.get("Value_Source");
        String cadence = data.get("Cadence");
        String resetsOn = data.get("Resets_On");

        clickElement(METRICS_MENU_XPATHS);
        sleep(PAGE_SETTLE_MS);
        clickElement(ADD_METRIC_BUTTON_XPATHS);
        sleep(ACTION_PAUSE_MS);

        sendKeysToElement(METRIC_NAME_INPUT_XPATHS, metricName);
        selectDropdownOptionByText(VALUE_SOURCE_DROPDOWN_XPATHS, valueSource);
        sleep(ACTION_PAUSE_MS);

        if ("Formula".equalsIgnoreCase(valueSource)) {
            String firstMetric = data.get("First_Metric");
            String operator = data.get("Operator");
            String secondMetric = data.get("Second_Metric");

            typeSlowly(FORMULA_BUILDER_SEARCH_INPUT_XPATHS, firstMetric);
            String firstMetricOptionXpath = String.format("//ul[contains(@style,'display: block')]//li[normalize-space()='%s']", firstMetric);
            clickElement(new String[]{firstMetricOptionXpath});
            sleep(500);

            String operatorXpath = String.format("//div[contains(@class,'formula-builder')]//button[normalize-space()='%s']", operator);
            clickElement(new String[]{operatorXpath});
            sleep(500);

            typeSlowly(FORMULA_BUILDER_SEARCH_INPUT_XPATHS, secondMetric);
            String secondMetricOptionXpath = String.format("//ul[contains(@style,'display: block')]//li[normalize-space()='%s']", secondMetric);
            clickElement(new String[]{secondMetricOptionXpath});
            sleep(500);
            
            clickElement(FORMULA_CONFIRM_BUTTON_XPATHS);
            sleep(500);

        } else if ("Metric".equalsIgnoreCase(valueSource)) {
            String metricToSelect = data.get("Metric_to_Select");
            String metricDropdownTriggerXpath = "//label[normalize-space()='Metric']/following-sibling::span//span[contains(@class,'k-select')]";
            selectDropdownOptionByText(new String[]{metricDropdownTriggerXpath}, metricToSelect);
        }

        selectDropdownOptionByText(CADENCE_DROPDOWN_XPATHS, cadence);
        if ("Weekly".equalsIgnoreCase(cadence)) {
            selectDropdownOptionByText(RESETS_ON_DROPDOWN_XPATHS, resetsOn);
        }

        clickElement(SAVE_BUTTON_XPATHS);
        sleep(PAGE_SETTLE_MS);
        log("Metric Creation flow completed successfully.");
    }

    private void performAddMetricToDashboard(Map<String, String> data) {
        log("Starting 'Add Metric to Dashboard' flow.");
        String metricName = data.get("Metric_Name");

        clickElement(DASHBOARDS_MENU_XPATHS);
        clickElement(MY_DASHBOARD_LINK_XPATHS);
        sleep(PAGE_SETTLE_MS);

        clickElement(EDIT_KPI_ICON_XPATHS);
        sleep(ACTION_PAUSE_MS);

        sendKeysToElement(EDIT_KPI_MODAL_SEARCH_XPATHS, metricName);
        sleep(500);

        String metricInPanelXpath = String.format(EDIT_KPI_MODAL_LEFT_PANEL_METRIC_XPATHS[0], metricName);
        clickElement(new String[]{metricInPanelXpath});
        sleep(500);

        clickElement(EDIT_KPI_MODAL_SAVE_BUTTON_XPATHS);
        sleep(PAGE_SETTLE_MS);
        log("'Add Metric to Dashboard' flow completed successfully.");
    }

    private void performTargetConfiguration(Map<String, String> data) {
        log("Starting Target Configuration flow.");
        String metricName = data.getOrDefault("Metric_Name", data.get("Metric_Name (to find on dashboard)"));
        String targetType = data.get("Target_Type");

        // Navigate to dashboard if not already there
        if (!driver.getCurrentUrl().contains("dashboard")) {
            clickElement(DASHBOARDS_MENU_XPATHS);
            clickElement(MY_DASHBOARD_LINK_XPATHS);
            sleep(PAGE_SETTLE_MS);
        }

        String threeDotMenuXpath = String.format(METRIC_CARD_THREE_DOT_MENU_XPATHS[0], metricName);
        WebElement threeDotMenu = findElementWithFallbacks(new String[]{threeDotMenuXpath});
        new Actions(driver).moveToElement(threeDotMenu).perform();
        sleep(500);
        clickElement(new String[]{threeDotMenuXpath});
        sleep(ACTION_PAUSE_MS);

        clickElement(METRIC_CARD_EDIT_OPTION_XPATHS);
        sleep(PAGE_SETTLE_MS);

        clickElement(TARGET_TAB_XPATHS);
        sleep(ACTION_PAUSE_MS);

        if ("Custom".equalsIgnoreCase(targetType)) {
            clickElement(CUSTOM_TARGET_RADIO_XPATHS);
            sleep(500);
            sendKeysToElement(LEVEL_1_INPUT_XPATHS, data.get("Level_1"));
            sendKeysToElement(LEVEL_2_INPUT_XPATHS, data.get("Level_2"));
            sendKeysToElement(LEVEL_3_INPUT_XPATHS, data.get("Level_3"));
            sendKeysToElement(LEVEL_4_INPUT_XPATHS, data.get("Level_4"));
        } else if ("Time-Based".equalsIgnoreCase(targetType)) {
            clickElement(TIME_BASED_TARGET_RADIO_XPATHS);
            sleep(500);
            sendKeysToElement(START_VALUE_INPUT_XPATHS, data.get("Start_Value"));
            sendKeysToElement(TARGET_VALUE_INPUT_XPATHS, data.get("Target_Value"));
        }

        clickElement(SAVE_BUTTON_XPATHS);
        sleep(PAGE_SETTLE_MS);
        log("Target Configuration flow completed successfully.");
    }

    // ========== SETUP, CLEANUP, AND MAIN ==========
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
            log("Cleaning up WebDriver...");
            driver.quit();
            driver = null;
            log("WebDriver cleanup complete.");
        }
    }

    public static void main(String[] args) {
        SeleniumDataDrivenTest test = new SeleniumDataDrivenTest();
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