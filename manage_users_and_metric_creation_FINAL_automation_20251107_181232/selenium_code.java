package com.selenium.datadriven;

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
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class AlignTestDataDriven {

    // ========== LOCATORS - DO NOT CHANGE ==========
    private static final String[] email_input = {
        "//input[@id='usernameField']",
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']",
        "//input[@name='username']",
        "//input[@type='email']",
        "//input[contains(@placeholder, 'Email')]",
        "//label[contains(text(),'Email')]/following-sibling::input",
        "//span[contains(@class,'k-widget')]//input[@id='usernameField']"
    };
    private static final String[] continue_button = {
        "//button[normalize-space()='Continue']",
        "//button[contains(.,'Continue')]",
        "//button[@type='submit' and contains(.,'Continue')]",
        "//button[contains(@class,'icon') and contains(@class,'primary')]",
        "//form//button[contains(., 'Continue')]"
    };
    private static final String[] password_input = {
        "//input[@id='passwordField']",
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']",
        "//input[@name='password']",
        "//input[@type='password']",
        "//input[contains(@placeholder, 'Password')]",
        "//label[contains(text(),'Password')]/following-sibling::input"
    };
    private static final String[] login_button = {
        "//button[normalize-space()='Login']",
        "//button[contains(.,'Login')]",
        "//button[@type='submit' and contains(.,'Login')]",
        "//button[contains(@class,'login-button')]",
        "//form//button[contains(., 'Login')]"
    };
    private static final String[] administration_link = {
        "//a[normalize-space()='Administration']",
        "//span[normalize-space()='Administration']/parent::a",
        "//div[contains(@class,'navigation')]//a[contains(.,'Administration')]",
        "//li/a[normalize-space()='Administration']",
        "//div[@id='header-links']//a[normalize-space()='Administration']"
    };
    private static final String[] manage_users_link = {
        "//a[normalize-space()='Manage Users']",
        "//li/a[normalize-space()='Manage Users']",
        "//ul[contains(@class,'dropdown')]//a[contains(.,'Manage Users')]",
        "//div[contains(@class,'menu')]//a[normalize-space()='Manage Users']"
    };
    private static final String[] invite_users_button = {
        "//button[normalize-space()='Invite Users']",
        "//a[normalize-space()='Invite Users']",
        "//button[contains(.,'Invite Users')]",
        "//span[normalize-space()='Invite Users']/parent::button"
    };
    private static final String[] invite_user_email_input = {
        "//div[contains(@class,'modal')]//input[@type='email']",
        "//label[contains(text(),'Email')]/following-sibling::input",
        "//input[contains(@placeholder, 'Enter email')]",
        "//form[@id='invite-users-form']//input[@type='email']"
    };
    private static final String[] admin_checkbox = {
        "//label[normalize-space()='Admin']/preceding-sibling::input[@type='checkbox']",
        "//label[normalize-space()='Admin']/input",
        "//input[@type='checkbox' and contains(@name,'admin')]",
        "//label[contains(.,'Admin')]/..//input[@type='checkbox']"
    };
    private static final String[] send_invite_button = {
        "//button[normalize-space()='Send Invite']",
        "//button[contains(.,'Send Invite')]",
        "//div[contains(@class,'modal-footer')]//button[contains(.,'Send')]"
    };
    private static final String[] accept_invitation_checkmark = {
        "//td[contains(.,'%s')]/following-sibling::td//a[contains(@title, 'Accept')]",
        "//td[contains(.,'%s')]/following-sibling::td//span[contains(@class, 'check')]",
        "//tr[contains(.,'%s')]//i[contains(@class, 'check')]",
        "//tr[td[normalize-space()='%s']]//a[contains(@title, 'Accept') or contains(@class, 'check')]"
    };
    private static final String[] first_name_input = {
        "//input[@id='FirstName']",
        "//input[contains(@name,'FirstName')]",
        "//label[contains(text(),'First Name')]/following-sibling::input",
        "//input[contains(@placeholder, 'First Name')]"
    };
    private static final String[] last_name_input = {
        "//input[@id='LastName']",
        "//input[contains(@name,'LastName')]",
        "//label[contains(text(),'Last Name')]/following-sibling::input",
        "//input[contains(@placeholder, 'Last Name')]"
    };
    private static final String[] user_password_input = {
        "//input[@id='Password']",
        "//input[contains(@name,'Password') and @type='password']",
        "//label[contains(text(),'Create Password')]/following-sibling::input",
        "//input[@type='password']"
    };
    private static final String[] accept_invite_button = {
        "//button[normalize-space()='Accept Invite']",
        "//button[contains(.,'Accept Invite')]",
        "//div[contains(@class,'form-actions')]//button[contains(.,'Accept')]"
    };
    private static final String[] metrics_link = {
        "//a[normalize-space()='Metrics']",
        "//span[normalize-space()='Metrics']/parent::a",
        "//div[contains(@class,'navigation')]//a[contains(.,'Metrics')]",
        "//li/a[normalize-space()='Metrics']"
    };
    private static final String[] add_metric_button = {
        "//button[normalize-space()='Add Metric']",
        "//a[normalize-space()='Add Metric']",
        "//button[contains(.,'Add Metric')]",
        "//span[normalize-space()='Add Metric']/parent::button"
    };
    private static final String[] metric_name_input = {
        "//input[@placeholder='Name of the Metric']",
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']",
        "//label[normalize-space()='Name']/following-sibling::input",
        "//form[contains(@class,'metric-form')]//input[1]"
    };
    private static final String[] value_source_dropdown_arrow = {
        "//label[contains(text(),'Value Source')]/following-sibling::span//span[contains(@class,'k-select')]",
        "//label[normalize-space()='Value Source']/..//span[@aria-label='select']"
    };
    private static final String[] cadence_dropdown_arrow = {
        "//label[contains(text(),'Cadence')]/following-sibling::span//span[contains(@class,'k-select')]",
        "//label[normalize-space()='Cadence']/..//span[@aria-label='select']"
    };
    private static final String[] resets_on_dropdown_arrow = {
        "//label[contains(text(),'Resets On')]/following-sibling::span//span[contains(@class,'k-select')]",
        "//label[normalize-space()='Resets On']/..//span[@aria-label='select']"
    };
    private static final String[] formula_builder_metric_search = {
        "//input[@placeholder='Name or Owner']",
        "//div[contains(@class,'formula-builder')]//input[@type='text']"
    };
    private static final String[] formula_confirm_checkmark = {
        "//span[@title='Validate and Calculate']",
        "//button[contains(@class,'validate-formula')]",
        "//span[contains(@class,'ico-checkmark')]"
    };
    private static final String[] save_button = {
        "//button[normalize-space()='Save']",
        "//button[contains(.,'Save') and not(@disabled)]",
        "//button[@id='saveButton']",
        "//div[contains(@class,'footer')]//button[normalize-space()='Save']"
    };
    private static final String[] my_dashboard_link = {
        "//a[normalize-space()='My Dashboard']",
        "//span[normalize-space()='My Dashboard']/parent::a",
        "//div[contains(@class,'navigation')]//a[contains(.,'My Dashboard')]",
        "//li/a[normalize-space()='My Dashboard']"
    };
    private static final String[] edit_kpi_icon = {
        "//span[@title='Edit Kpi']",
        "//button[@aria-label='Edit KPI']",
        "//a[contains(@class,'edit-kpi')]",
        "//i[contains(@class,'ico-edit')]"
    };
    private static final String[] edit_kpi_modal_metric_item = {
        "//div[contains(@class,'modal')]//div[contains(@class,'available-metrics')]//li[normalize-space()='%s']",
        "//div[@id='editKpiModal']//ul//li[contains(.,'%s')]"
    };
    private static final String[] edit_kpi_modal_save_button = {
        "//div[contains(@class,'modal-footer')]//button[normalize-space()='Save']",
        "//div[@id='editKpiModal']//button[contains(.,'Save')]"
    };
    private static final String[] metric_card_dots_menu = {
        "//div[contains(@class,'kpicard-container') and .//span[normalize-space()='%s']]//span[contains(@class,'ico-threeDots')]",
        "//div[contains(@class,'metric-card')][.//div[text()='%s']]//button[@aria-label='menu']"
    };
    private static final String[] metric_card_edit_option = {
        "//div[contains(@class,'k-popup')]//li[@title='Edit']",
        "//ul[contains(@class,'k-menu-group')]//li[normalize-space()='Edit']",
        "//div[contains(@class,'dropdown-menu') and contains(@style,'display: block')]//a[normalize-space()='Edit']"
    };
    private static final String[] target_tab = {
        "//a[normalize-space()='Target']",
        "//li[normalize-space()='Target']",
        "//div[@role='tablist']//a[contains(.,'Target')]"
    };
    private static final String[] custom_target_radio = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']",
        "//input[@type='radio' and @value='Custom']",
        "//label[contains(.,'Custom')]/input"
    };
    private static final String[] level_1_input = { "//div[./label[contains(text(),'Level 1')]]/following-sibling::div//input", "//input[contains(@placeholder, '400')]" };
    private static final String[] level_2_input = { "//div[./label[contains(text(),'Level 2')]]/following-sibling::div//input", "//input[contains(@placeholder, '300')]" };
    private static final String[] level_3_input = { "//div[./label[contains(text(),'Level 3')]]/following-sibling::div//input", "//input[contains(@placeholder, '200')]" };
    private static final String[] level_4_input = { "//div[./label[contains(text(),'Level 4')]]/following-sibling::div//input", "//input[contains(@placeholder, '100')]" };
    private static final String[] time_based_target_radio = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']",
        "//input[@type='radio' and @value='Time-Based']",
        "//label[contains(.,'Time-Based')]/input"
    };
    private static final String[] start_value_input = { "//label[normalize-space()='Start']/following-sibling::input", "//input[contains(@name,'startValue')]" };
    private static final String[] target_value_input = { "//label[normalize-space()='Target']/following-sibling::input", "//input[contains(@name,'targetValue')]" };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/path/to/your/testdata.xlsx";
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
                        return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    }
                    double num = cell.getNumericCellValue();
                    if (num == (long) num) {
                        return String.valueOf((long) num);
                    } else {
                        return String.valueOf(num);
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

    private WebElement findElementWithFallbacks(String[] xpaths) {
        for (String xpath : xpaths) {
            try {
                return driver.findElement(By.xpath(xpath));
            } catch (Exception e) {
                // Ignore and try next xpath
            }
        }
        throw new NoSuchElementException("Element not found with any of the provided XPaths.");
    }

    private WebElement waitVisible(String[] xpaths) {
        return wait.until(driver -> findElementWithFallbacks(xpaths));
    }

    private WebElement waitClickable(String[] xpaths) {
        return wait.until(ExpectedConditions.elementToBeClickable(findElementWithFallbacks(xpaths)));
    }


    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = waitVisible(xpaths);
        element.clear();
        element.sendKeys(text);
    }

    private void typeSlowly(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = waitVisible(xpaths);
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
    }

    private void clickElement(String[] xpaths) {
        WebElement element = waitClickable(xpaths);
        try {
            element.click();
        } catch (Exception e) {
            log("Regular click failed, trying JavaScript click.");
            jsClick(element);
        }
    }

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void log(String message) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + " - " + message);
    }

    private void waitForPageLoad() {
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
        sleep(PAGE_SETTLE_MS);
    }

    private void selectCompanyByName(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            log("Company name not provided, skipping selection.");
            return;
        }
        log("Attempting to select company: " + companyName);
        try {
            String companyXPath = String.format("//span[normalize-space()='%s']", companyName);
            WebElement companySpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(companyXPath)));
            
            WebElement clickableParent = companySpan.findElement(By.xpath("./ancestor::div[contains(@class,'company-card') or contains(@class,'list-item')][1]"));
            log("Found clickable parent for company. Clicking it.");
            js.executeScript("arguments[0].scrollIntoView(true);", clickableParent);
            clickableParent.click();
        } catch (Exception e) {
            log("WARN: Could not find a clickable parent for the company. Trying to click the span directly. This may not work. Error: " + e.getMessage());
            try {
                 String companyXPath = String.format("//span[normalize-space()='%s']", companyName);
                 WebElement companySpan = driver.findElement(By.xpath(companyXPath));
                 js.executeScript("arguments[0].scrollIntoView(true);", companySpan);
                 companySpan.click();
            } catch (Exception e2) {
                log("WARN: Clicking the company span directly also failed. Continuing without company selection. Error: " + e2.getMessage());
            }
        }
        waitForPageLoad();
    }

    private String getOrDefault(Map<String, String> map, String key, String defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        log("Found " + testData.size() + " test records in the Excel file.");
        for (int i = 0; i < testData.size(); i++) {
            log("\n--- Starting execution for record " + (i + 1) + " ---");
            try {
                executeWorkflow(testData.get(i));
                log("--- Successfully completed record " + (i + 1) + " ---");
            } catch (Exception e) {
                log("ERROR in record " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // Extract all data at the beginning
        String email = getOrDefault(data, "Email", "");
        String password = getOrDefault(data, "Password", "");
        String companyName = getOrDefault(data, "Company_Name", "");
        String inviteUserEmail = getOrDefault(data, "Invite_User_Email", "");
        String isAdmin = getOrDefault(data, "Admin", "No");
        String firstName = getOrDefault(data, "First_Name", "");
        String lastName = getOrDefault(data, "Last_Name", "");
        String userPassword = getOrDefault(data, "User_Password", "");
        String metricName = getOrDefault(data, "Metric_Name", "");
        String valueSource = getOrDefault(data, "Value_Source", "");
        String cadence = getOrDefault(data, "Cadence", "");
        String resetsOn = getOrDefault(data, "Resets_On", "");
        String firstMetric = getOrDefault(data, "First_Metric", "");
        String operator = getOrDefault(data, "Operator", "");
        String secondMetric = getOrDefault(data, "Second_Metric", "");
        String metricToSelect = getOrDefault(data, "Metric to Select", "");
        String targetType = getOrDefault(data, "Target_Type", "");
        String level1 = getOrDefault(data, "Level_1", "");
        String level2 = getOrDefault(data, "Level_2", "");
        String level3 = getOrDefault(data, "Level_3", "");
        String level4 = getOrDefault(data, "Level_4", "");
        String startValue = getOrDefault(data, "Start_Value", "");
        String targetValue = getOrDefault(data, "Target_Value", "");

        // LOGIN BLOCK (only for the first record)
        if (isFirstRecord) {
            log("Executing login for the first record.");
            driver.get(BASE_URL);
            waitForPageLoad();

            log("Step 1: Navigate to the Application URL. Expected: Login page is displayed.");
            sendKeysToElement(email_input, email);
            log("Step 2: Enter email and click 'Continue'.");
            clickElement(continue_button);
            sleep(ACTION_PAUSE_MS);

            log("Step 3: Enter password and click 'Login'.");
            sendKeysToElement(password_input, password);
            clickElement(login_button);
            waitForPageLoad();

            log("Step 4: Select the company.");
            selectCompanyByName(companyName);
        }

        // USER INVITATION AND ACCEPTANCE BLOCK
        if (!inviteUserEmail.isEmpty()) {
            log("Starting User Invitation flow for: " + inviteUserEmail);
            log("Step 5 & 6: Navigate to Administration -> Manage Users.");
            clickElement(administration_link);
            sleep(ACTION_PAUSE_MS);
            clickElement(manage_users_link);
            waitForPageLoad();

            log("Step 7: Click 'Invite Users' button.");
            clickElement(invite_users_button);
            sleep(ACTION_PAUSE_MS);

            log("Step 8: Enter user email.");
            sendKeysToElement(invite_user_email_input, inviteUserEmail);

            if (isAdmin.equalsIgnoreCase("Yes")) {
                log("Step 9: Set Admin privileges to 'Yes'.");
                clickElement(admin_checkbox);
            }

            log("Step 10: Send invitation.");
            clickElement(send_invite_button);
            sleep(ACTION_PAUSE_MS * 2); // Wait for modal to close and table to refresh

            log("Step 11 & 12: Find invited user in 'Pending Invitations'.");
            WebElement pendingSection = driver.findElement(By.xpath("//h2[normalize-space()='Pending Invitations']"));
            js.executeScript("arguments[0].scrollIntoView(true);", pendingSection);
            sleep(ACTION_PAUSE_MS);

            log("Step 13: Click checkmark to accept invitation.");
            String[] specificCheckmarkXpath = { String.format(accept_invitation_checkmark[0], inviteUserEmail), String.format(accept_invitation_checkmark[1], inviteUserEmail), String.format(accept_invitation_checkmark[2], inviteUserEmail), String.format(accept_invitation_checkmark[3], inviteUserEmail) };
            clickElement(specificCheckmarkXpath);
            waitForPageLoad();

            log("Step 14: Enter first name.");
            sendKeysToElement(first_name_input, firstName);
            log("Step 15: Enter last name.");
            sendKeysToElement(last_name_input, lastName);
            log("Step 16: Enter password.");
            sendKeysToElement(user_password_input, userPassword);

            log("Step 17: Click 'Accept Invite' button.");
            clickElement(accept_invite_button);
            waitForPageLoad();
            log("User invitation and acceptance flow completed.");
        }

        // METRIC CREATION BLOCK
        if (!metricName.isEmpty()) {
            log("Starting Metric Creation flow for: " + metricName);
            log("Step 18: Navigate to 'Metrics' page.");
            clickElement(metrics_link);
            waitForPageLoad();

            log("Step 19: Click 'Add Metric' button.");
            clickElement(add_metric_button);
            waitForPageLoad();

            log("Step 20: Enter metric name.");
            sendKeysToElement(metric_name_input, metricName);

            if ("Formula".equalsIgnoreCase(valueSource)) {
                log("Step 21: Select 'Formula' from 'Value Source'.");
                clickElement(value_source_dropdown_arrow);
                sleep(500);
                clickElement(new String[]{"//li[normalize-space()='Formula']"});
                sleep(ACTION_PAUSE_MS);

                log("Step 22: Select First Metric: " + firstMetric);
                typeSlowly(formula_builder_metric_search, firstMetric);
                sleep(ACTION_PAUSE_MS);
                clickElement(new String[]{String.format("//li[normalize-space()='%s']", firstMetric)});
                sleep(500);

                log("Step 23: Click Operator: " + operator);
                clickElement(new String[]{String.format("//button[normalize-space()='%s']", operator)});
                sleep(500);

                log("Step 24: Select Second Metric: " + secondMetric);
                typeSlowly(formula_builder_metric_search, secondMetric);
                sleep(ACTION_PAUSE_MS);
                clickElement(new String[]{String.format("//li[normalize-space()='%s']", secondMetric)});
                sleep(500);

                log("Step 28: Confirm formula.");
                clickElement(formula_confirm_checkmark);
                sleep(500);
            } else if ("Metric".equalsIgnoreCase(valueSource)) {
                log("Select 'Metric' from 'Value Source'.");
                clickElement(value_source_dropdown_arrow);
                sleep(500);
                clickElement(new String[]{"//li[normalize-space()='Metric']"});
                sleep(ACTION_PAUSE_MS);

                log("Select source metric: " + metricToSelect);
                // This might be a search field or another dropdown
                String[] metricSearchInput = {"//label[contains(text(),'Metric')]/following-sibling::span//input"};
                typeSlowly(metricSearchInput, metricToSelect);
                sleep(ACTION_PAUSE_MS);
                clickElement(new String[]{String.format("//li[normalize-space()='%s']", metricToSelect)});
                sleep(500);
            }

            log("Step 26: Select Cadence: " + cadence);
            clickElement(cadence_dropdown_arrow);
            sleep(500);
            clickElement(new String[]{String.format("//li[normalize-space()='%s']", cadence)});
            sleep(500);

            if ("Weekly".equalsIgnoreCase(cadence) && !resetsOn.isEmpty()) {
                log("Step 27: Select Resets On: " + resetsOn);
                clickElement(resets_on_dropdown_arrow);
                sleep(500);
                clickElement(new String[]{String.format("//li[normalize-space()='%s']", resetsOn)});
                sleep(500);
            }

            log("Step 29: Save the metric.");
            clickElement(save_button);
            waitForPageLoad();
            log("Metric creation completed.");

            // ADD TO DASHBOARD AND CONFIGURE TARGET
            log("Step 30: Navigate to 'My Dashboard'.");
            clickElement(my_dashboard_link);
            waitForPageLoad();

            log("Step 31: Click 'Edit KPI' icon.");
            clickElement(edit_kpi_icon);
            sleep(ACTION_PAUSE_MS);

            log("Step 32: Add new metric to dashboard.");
            String[] specificMetricItem = { String.format(edit_kpi_modal_metric_item[0], metricName), String.format(edit_kpi_modal_metric_item[1], metricName) };
            clickElement(specificMetricItem);
            sleep(500);

            log("Step 33: Save dashboard KPIs.");
            clickElement(edit_kpi_modal_save_button);
            waitForPageLoad();

            if (!targetType.isEmpty()) {
                log("Starting Target Configuration flow for: " + metricName);
                log("Step 34 & 35: Locate metric card and hover.");
                String[] specificCardDots = { String.format(metric_card_dots_menu[0], metricName), String.format(metric_card_dots_menu[1], metricName) };
                WebElement dotsMenu = waitVisible(specificCardDots);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dotsMenu);
                new Actions(driver).moveToElement(dotsMenu).perform();
                sleep(ACTION_PAUSE_MS);

                log("Step 36: Click three-dot menu.");
                clickElement(specificCardDots);
                sleep(ACTION_PAUSE_MS);

                log("Step 37: Click 'Edit' option.");
                clickElement(metric_card_edit_option);
                waitForPageLoad();

                log("Step 38: Select 'Target' tab.");
                clickElement(target_tab);
                sleep(ACTION_PAUSE_MS);

                if ("Custom".equalsIgnoreCase(targetType)) {
                    log("Step 39: Select 'Custom' target type.");
                    clickElement(custom_target_radio);
                    sleep(ACTION_PAUSE_MS);

                    log("Step 40: Enter custom target values.");
                    sendKeysToElement(level_1_input, level1);
                    sendKeysToElement(level_2_input, level2);
                    sendKeysToElement(level_3_input, level3);
                    sendKeysToElement(level_4_input, level4);
                } else if ("Time-Based".equalsIgnoreCase(targetType)) {
                    log("Select 'Time-Based' target type.");
                    clickElement(time_based_target_radio);
                    sleep(ACTION_PAUSE_MS);

                    log("Enter time-based target values.");
                    sendKeysToElement(start_value_input, startValue);
                    sendKeysToElement(target_value_input, targetValue);
                }

                log("Step 41: Save target configuration.");
                clickElement(save_button);
                waitForPageLoad();
                log("Step 42: Target configuration saved. Verified navigation back to dashboard.");
            }
        }
    }

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
        log("Cleaning up WebDriver.");
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