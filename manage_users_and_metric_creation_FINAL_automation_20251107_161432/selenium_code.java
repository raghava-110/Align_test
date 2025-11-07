package com.selenium.datadriven;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignTestDataDriven {

    // ========== LOCATORS - GENERATED WITH FALLBACKS ==========
    private static final String[] email_input_locators = {
        "//input[@data-bind='value: username, events: { keyup: emailKeyUp }']", "//input[contains(@data-bind,'value: username')]", "//input[@id='usernameField']", "//span[contains(@class,'k-widget')]//input[@id='usernameField']", "//input[@name='username']", "//input[@type='email']", "//input[contains(@id, 'email') or contains(@id, 'user')]", "//input[@placeholder='Email']", "//label[contains(normalize-space(), 'Email')]/following-sibling::input", "//input[@aria-label='Email Address']"
    };
    private static final String[] continue_button_locators = {
        "//button[contains(@class,'icon')]", "//button[contains(normalize-space(), 'Continue')]", "//button[@id='continue-button']", "//button[@type='submit' and contains(., 'Continue')]", "//button[contains(@class, 'continue')]", "//button[@title='Continue']"
    };
    private static final String[] password_input_locators = {
        "//input[@data-bind='value: password, displaynone: showClearText, events: { keyup: passwordKeyUp }']", "//input[contains(@data-bind,'value: password')]", "//input[@id='passwordField']", "//span[contains(@class,'k-widget')]//input[@id='passwordField']", "//input[@name='password']", "//input[@type='password']", "//input[contains(@id, 'password')]", "//input[@placeholder='Password']", "//label[contains(normalize-space(), 'Password')]/following-sibling::input", "//input[@aria-label='Password']"
    };
    private static final String[] login_button_locators = {
        "//button[contains(@class,'icon')]", "//button[normalize-space()='Login']", "//button[normalize-space()='Log In']", "//button[@id='login-button']", "//button[@type='submit' and (contains(., 'Login') or contains(., 'Sign In'))]", "//button[contains(@class, 'login')]", "//button[@title='Login']"
    };
    private static final String[] administration_dropdown_locators = {
        "//label[contains(text(),'Administration')]/following-sibling::input", "//label[contains(text(),'Administration')]/..//input", "//span[contains(@class,'k-dropdown')]//input", "//a[normalize-space()='Administration']", "//span[normalize-space()='Administration']", "//div[contains(@class, 'nav')]//a[contains(., 'Administration')]", "//button[contains(., 'Administration')]"
    };
    private static final String[] manage_users_link_locators = {
        "//input[@id='navigation-header-dropdown']", "//a[normalize-space()='Manage Users']", "//li/a[contains(., 'Manage Users')]", "//div[contains(@class, 'dropdown-menu')]//a[normalize-space()='Manage Users']", "//span[normalize-space()='Manage Users']"
    };
    private static final String[] invite_users_button_locators = {
        "//button[contains(@class,'icon')]", "//button[normalize-space()='Invite Users']", "//button[contains(., 'Invite')]", "//a[normalize-space()='Invite Users']", "//button[contains(@class, 'invite')]", "//button[@title='Invite Users']"
    };
    private static final String[] invite_user_email_input_locators = {
        "//div[contains(@class, 'modal')]//input[@type='email']", "//form[contains(@id, 'invite')]//input[@type='email']", "//label[contains(., 'Email')]/following-sibling::input[@type='email']", "//input[@placeholder='Enter email']"
    };
    private static final String[] admin_checkbox_locators = {
        "//label[normalize-space()='Admin']/preceding-sibling::input[@type='checkbox']", "//input[@type='checkbox' and contains(@name, 'admin')]", "//label[contains(., 'Admin')]/input", "//div[contains(., 'Admin')]/input[@type='checkbox']"
    };
    private static final String[] send_invite_button_locators = {
        "//button[normalize-space()='Send Invite']", "//button[contains(., 'Send') and contains(., 'Invite')]", "//div[contains(@class, 'modal')]//button[@type='submit']", "//button[@id='send-invite-btn']"
    };
    private static final String[] first_name_input_locators = {
        "//input[@data-bind='value: promoCode.code']", "//input[@id='inviteUserContentPopup_input_promoCode']", "//input[@name='FirstName']", "//input[contains(@id, 'firstName')]", "//input[@placeholder='First Name']", "//label[contains(., 'First Name')]/following-sibling::input"
    };
    private static final String[] last_name_input_locators = {
        "//input[@data-bind='value: promoCode.code']", "//input[@id='inviteUserContentPopup_input_promoCode']", "//input[@name='LastName']", "//input[contains(@id, 'lastName')]", "//input[@placeholder='Last Name']", "//label[contains(., 'Last Name')]/following-sibling::input"
    };
    private static final String[] create_password_input_locators = {
        "//input[@id='passwordField']", "//input[@name='password']", "//input[@type='password' and contains(@id, 'create')]", "//input[@placeholder='Create Password']", "//label[contains(., 'Create Password')]/following-sibling::input"
    };
    private static final String[] accept_invite_button_locators = {
        "//button[normalize-space()='Accept Invite']", "//button[contains(., 'Accept')]", "//button[@type='submit' and contains(., 'Accept')]", "//button[contains(@class, 'accept')]"
    };
    private static final String[] metrics_link_locators = {
        "//input[@id='navigation-header-dropdown']", "//a[normalize-space()='Metrics']", "//span[normalize-space()='Metrics']", "//div[contains(@class, 'nav')]//a[contains(., 'Metrics')]", "//li/a[normalize-space()='Metrics']"
    };
    private static final String[] add_metric_button_locators = {
        "//button[contains(@class,'icon')]", "//button[normalize-space()='Add Metric']", "//a[normalize-space()='Add Metric']", "//button[contains(., 'Add Metric')]", "//button[@title='Add Metric']"
    };
    private static final String[] metric_name_input_locators = {
        "//input[@data-bind='value: companyMetric.MetricName, events: { change: onChangeMetricName }']", "//input[@placeholder='Name of the Metric']", "//label[normalize-space()='Metric Name']/following-sibling::input", "//input[contains(@id, 'metricName')]"
    };
    private static final String[] value_source_dropdown_locators = {
        "//label[contains(text(),'Value Source')]/following-sibling::span//input", "//label[normalize-space()='Value Source']/..//span[contains(@class, 'k-dropdown')]", "//input[@aria-label='Value Source']"
    };
    private static final String[] formula_search_metric_input_locators = {
        "//input[@placeholder='Name or Owner']", "//div[contains(@class, 'formula-builder')]//input[@type='text']"
    };
    private static final String[] formula_confirm_button_locators = {
        "//span[@title='Validate and Calculate']", "//button[contains(@class, 'green') and .//span[contains(@class, 'check')]]", "//div[contains(@class, 'formula-builder')]//button[contains(., 'Confirm')]"
    };
    private static final String[] cadence_dropdown_locators = {
        "//label[contains(text(),'Cadence')]/following-sibling::span//input", "//label[normalize-space()='Cadence']/..//span[contains(@class, 'k-dropdown')]", "//input[@aria-label='Cadence']"
    };
    private static final String[] resets_on_dropdown_locators = {
        "//input[contains(@data-bind,'value: selectedResetWeekDay')]", "//label[normalize-space()='Resets On']/..//span[contains(@class, 'k-dropdown')]", "//input[@aria-label='Resets On']"
    };
    private static final String[] save_button_locators = {
        "//button[contains(@class,'icon') and contains(., 'Save')]", "//button[normalize-space()='Save']", "//button[@id='saveButton']", "//button[@type='submit' and contains(., 'Save')]", "//button[contains(@class, 'save')]"
    };
    private static final String[] dashboards_link_locators = {
        "//input[@id='navigation-header-dropdown']", "//a[normalize-space()='Dashboards']", "//span[normalize-space()='Dashboards']", "//div[contains(@class, 'nav')]//a[contains(., 'Dashboards')]"
    };
    private static final String[] my_dashboard_link_locators = {
        "//input[@id='navigation-header-dropdown']", "//a[normalize-space()='My Dashboard']", "//div[contains(@class, 'dropdown-menu')]//a[normalize-space()='My Dashboard']", "//span[normalize-space()='My Dashboard']"
    };
    private static final String[] edit_kpi_icon_locators = {
        "//span[@title='Edit Kpi']", "//span[contains(@class, 'edit') and contains(@class, 'kpi')]", "//div[contains(., 'My KPIs')]//button[contains(@title, 'Edit')]"
    };
    private static final String[] edit_kpi_modal_save_button_locators = {
        "//div[contains(@class, 'modal')]//button[normalize-space()='Save']", "//div[@id='editKpiModal']//button[contains(., 'Save')]"
    };
    private static final String[] target_tab_locators = {
        "//a[normalize-space()='Target']", "//li/a[contains(., 'Target')]", "//div[@role='tablist']//a[contains(., 'Target')]"
    };
    private static final String[] custom_target_radio_locators = {
        "//label[normalize-space()='Custom']/preceding-sibling::input[@type='radio']", "//input[@type='radio' and @value='Custom']", "//label[contains(., 'Custom')]/input[@type='radio']"
    };
    private static final String[] time_based_target_radio_locators = {
        "//label[normalize-space()='Time-Based']/preceding-sibling::input[@type='radio']", "//input[@type='radio' and @value='Time-Based']", "//label[contains(., 'Time-Based')]/input[@type='radio']"
    };
    private static final String[] start_value_input_locators = {
        "//label[normalize-space()='Start Value']/following-sibling::input", "//input[contains(@id, 'startValue')]", "//input[@name='StartValue']"
    };
    private static final String[] target_value_input_locators = {
        "//label[normalize-space()='Target Value']/following-sibling::input", "//input[contains(@id, 'targetValue')]", "//input[@name='TargetValue']"
    };

    // ========== CONFIGURATION - CHANGE THIS ==========
    private static final String EXCEL_FILE_PATH = "C:/path/to/your/Align_Test_Data.xlsx";
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
                    if (numericValue == (long) numericValue) {
                        return new DecimalFormat("#").format(numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (IllegalStateException e) {
                        // Fallback for formula resulting in a number
                        double formulaNumericValue = cell.getNumericCellValue();
                        if (formulaNumericValue == (long) formulaNumericValue) {
                            return new DecimalFormat("#").format(formulaNumericValue);
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

    // ========== SELENIUM HELPER METHODS ==========
    private void log(String message) {
        System.out.println(message);
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
        throw new NoSuchElementException("Element not found with any of the provided locators.");
    }

    private void clickElement(String[] xpaths) {
        WebElement element = findElementWithFallbacks(xpaths);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            sleep(200);
            element.click();
            log("Clicked element successfully.");
        } catch (Exception e) {
            log("Standard click failed, trying JavaScript click.");
            try {
                js.executeScript("arguments[0].click();", element);
                log("JavaScript click successful.");
            } catch (Exception jsE) {
                throw new ElementClickInterceptedException("Both standard and JS clicks failed for element.", jsE);
            }
        }
    }

    private void sendKeysToElement(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) {
            log("Skipping sendKeys as text is null or empty.");
            return;
        }
        WebElement element = findElementWithFallbacks(xpaths);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            sleep(200);
            element.click(); // Click to focus
            element.clear();
            element.sendKeys(text);
            sleep(500); // Wait for UI to react
            String value = element.getAttribute("value");
            if (!text.equals(value)) {
                log("Standard sendKeys failed to set value correctly. Trying JS fallback.");
                js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input')); arguments[0].dispatchEvent(new Event('change'));", element, text);
            }
            log("Entered text: '" + text + "'");
        } catch (Exception e) {
            throw new InvalidElementStateException("Failed to send keys to element.", e);
        }
    }
    
    private void typeSlowly(String[] xpaths, String text) {
        if (text == null || text.isEmpty()) return;
        WebElement element = findElementWithFallbacks(xpaths);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(TYPE_DELAY_MS);
        }
        log("Slowly typed text: '" + text + "'");
    }

    private void selectCompanyByName(String companyName) {
        log("Attempting to select company: " + companyName);
        String spanXpath = String.format("//span[@data-bind='text:getCompanyNameText' and text()='%s']", companyName);
        String[] parentCardXpaths = {
            spanXpath + "/ancestor::div[contains(@class,'card')]",
            spanXpath + "/ancestor::div[contains(@class,'company') or contains(@class,'organization')]",
            spanXpath + "/ancestor::div[@role='button']",
            spanXpath + "/parent::div[contains(@onclick,'select')]",
            spanXpath + "/ancestor::*[contains(@class,'clickable')]"
        };

        for (String parentXpath : parentCardXpaths) {
            try {
                WebElement parentCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(parentXpath)));
                parentCard.click();
                log("Clicked company card for: " + companyName);
                return;
            } catch (TimeoutException e) {
                // Try next parent strategy
            }
        }

        log("WARNING: Could not find a clickable parent card. Clicking the company name span directly.");
        try {
            WebElement companySpan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(spanXpath)));
            companySpan.click();
            log("Clicked company span for: " + companyName);
        } catch (Exception e) {
            throw new NoSuchElementException("Failed to select company: " + companyName, e);
        }
    }
    
    private void selectDropdownOptionByText(String text) {
        if (text == null || text.isEmpty()) return;
        String optionXpath = String.format("//li[normalize-space()='%s']", text);
        try {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));
            option.click();
            log("Selected dropdown option: " + text);
        } catch (Exception e) {
            throw new NoSuchElementException("Could not select dropdown option: " + text, e);
        }
    }

    // ========== MAIN TEST WORKFLOW ==========
    public void testDataDrivenWorkflow() {
        List<Map<String, String>> testData = ExcelReader.readExcel(EXCEL_FILE_PATH);
        if (testData.isEmpty()) {
            log("No test data found in the Excel file. Exiting.");
            return;
        }
        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> data = testData.get(i);
            log("\n=============================================================");
            log("Executing Test Record " + (i + 1) + " | Metric: " + data.get("Metric_Name"));
            log("=============================================================");
            try {
                executeWorkflow(data);
            } catch (Exception e) {
                log("!!!!!!!!!! ERROR in record " + (i + 1) + ": " + e.getMessage() + " !!!!!!!!!!");
                e.printStackTrace();
            } finally {
                isFirstRecord = false;
            }
        }
    }

    private void executeWorkflow(Map<String, String> data) {
        // STEP 1: LOGIN & COMPANY SELECTION (First Record Only)
        if (isFirstRecord) {
            log("--- Step: Navigate and Login ---");
            driver.get(BASE_URL);
            sendKeysToElement(email_input_locators, data.get("Email"));
            clickElement(continue_button_locators);
            sleep(ACTION_PAUSE_MS);
            sendKeysToElement(password_input_locators, data.get("Password"));
            clickElement(login_button_locators);
            sleep(PAGE_SETTLE_MS);

            log("--- Step: Select Company ---");
            selectCompanyByName(data.get("Company_Name"));
            sleep(PAGE_SETTLE_MS * 2);
        } else {
            log("--- Step: Skipping Login (already logged in) ---");
            driver.get(BASE_URL + "Dashboard/My"); // Navigate to a known starting point
            sleep(PAGE_SETTLE_MS);
        }

        // STEP 2: INVITE NEW USER (if data is present)
        String inviteUserEmail = data.get("Invite_User_Email");
        if (inviteUserEmail != null && !inviteUserEmail.isEmpty()) {
            log("--- Feature: Invite New User ---");
            clickElement(administration_dropdown_locators);
            selectDropdownOptionByText("Administration");
            sleep(ACTION_PAUSE_MS);
            clickElement(manage_users_link_locators);
            selectDropdownOptionByText("Manage Users");
            sleep(PAGE_SETTLE_MS);

            clickElement(invite_users_button_locators);
            sleep(ACTION_PAUSE_MS);
            sendKeysToElement(invite_user_email_input_locators, inviteUserEmail);
            
            if ("Yes".equalsIgnoreCase(data.get("Admin"))) {
                clickElement(admin_checkbox_locators);
            }
            clickElement(send_invite_button_locators);
            sleep(PAGE_SETTLE_MS);

            log("--- Step: Accept Invitation ---");
            String acceptInviteXpath = String.format("//td[text()='%s']/following-sibling::td//span[contains(@class, 'check')]", inviteUserEmail);
            clickElement(new String[]{acceptInviteXpath});
            sleep(PAGE_SETTLE_MS);

            sendKeysToElement(first_name_input_locators, data.get("First_Name"));
            sendKeysToElement(last_name_input_locators, data.get("Last_Name"));
            sendKeysToElement(create_password_input_locators, data.get("User_Password"));
            clickElement(accept_invite_button_locators);
            sleep(PAGE_SETTLE_MS * 2);
        }

        // STEP 3: CREATE METRIC (if data is present)
        String metricName = data.get("Metric_Name");
        if (metricName != null && !metricName.isEmpty()) {
            log("--- Feature: Create New Metric ---");
            clickElement(metrics_link_locators);
            selectDropdownOptionByText("Metrics");
            sleep(PAGE_SETTLE_MS);
            clickElement(add_metric_button_locators);
            sleep(ACTION_PAUSE_MS);

            sendKeysToElement(metric_name_input_locators, metricName);
            
            String valueSource = data.get("Value_Source");
            if ("Formula".equalsIgnoreCase(valueSource)) {
                clickElement(value_source_dropdown_locators);
                selectDropdownOptionByText("Formula");
                sleep(ACTION_PAUSE_MS);
                
                typeSlowly(formula_search_metric_input_locators, data.get("First_Metric"));
                selectDropdownOptionByText(data.get("First_Metric"));
                
                String operatorButtonXpath = String.format("//button[normalize-space()='%s']", data.get("Operator"));
                clickElement(new String[]{operatorButtonXpath});
                
                typeSlowly(formula_search_metric_input_locators, data.get("Second_Metric"));
                selectDropdownOptionByText(data.get("Second_Metric"));
                
                clickElement(formula_confirm_button_locators);
            }
            
            String cadence = data.get("Cadence");
            clickElement(cadence_dropdown_locators);
            selectDropdownOptionByText(cadence);
            
            if ("Weekly".equalsIgnoreCase(cadence)) {
                clickElement(resets_on_dropdown_locators);
                selectDropdownOptionByText(data.get("Resets_On"));
            }
            
            clickElement(save_button_locators);
            sleep(PAGE_SETTLE_MS);
        }

        // STEP 4: ADD METRIC TO DASHBOARD (if metric was created)
        if (metricName != null && !metricName.isEmpty()) {
            log("--- Feature: Add Metric to 'My KPIs' ---");
            clickElement(dashboards_link_locators);
            selectDropdownOptionByText("Dashboards");
            sleep(ACTION_PAUSE_MS);
            clickElement(my_dashboard_link_locators);
            selectDropdownOptionByText("My Dashboard");
            sleep(PAGE_SETTLE_MS);

            clickElement(edit_kpi_icon_locators);
            sleep(ACTION_PAUSE_MS);
            
            String metricInListXpath = String.format("//div[contains(@class, 'left-panel')]//div[normalize-space()='%s']", metricName);
            clickElement(new String[]{metricInListXpath});
            sleep(ACTION_PAUSE_MS);
            
            clickElement(edit_kpi_modal_save_button_locators);
            sleep(PAGE_SETTLE_MS * 2);
        }

        // STEP 5: CONFIGURE METRIC TARGET (if data is present)
        String targetType = data.get("Target_Type");
        if (targetType != null && !targetType.isEmpty()) {
            log("--- Feature: Configure Metric Target ---");
            String metricCardXpath = String.format("//div[contains(@class, 'kpi-card') and .//span[normalize-space()='%s']]", metricName);
            WebElement metricCard = findElementWithFallbacks(new String[]{metricCardXpath});
            
            new Actions(driver).moveToElement(metricCard).perform();
            sleep(500);
            
            String threeDotMenuXpath = metricCardXpath + "//span[contains(@class, 'threeDots')]";
            clickElement(new String[]{threeDotMenuXpath});
            sleep(ACTION_PAUSE_MS);
            
            String editOptionXpath = "//div[contains(@class, 'k-popup')]//li[@title='Edit']";
            clickElement(new String[]{editOptionXpath});
            sleep(PAGE_SETTLE_MS);

            clickElement(target_tab_locators);
            sleep(ACTION_PAUSE_MS);

            if ("Custom".equalsIgnoreCase(targetType)) {
                clickElement(custom_target_radio_locators);
                sleep(ACTION_PAUSE_MS);
                String[] levelInputs = {
                    "//input[@data-kendo-numerictextbox and contains(@name, 'Level1')]",
                    "//input[@data-kendo-numerictextbox and contains(@name, 'Level2')]",
                    "//input[@data-kendo-numerictextbox and contains(@name, 'Level3')]",
                    "//input[@data-kendo-numerictextbox and contains(@name, 'Level4')]"
                };
                sendKeysToElement(new String[]{levelInputs[0]}, data.get("Level_1"));
                sendKeysToElement(new String[]{levelInputs[1]}, data.get("Level_2"));
                sendKeysToElement(new String[]{levelInputs[2]}, data.get("Level_3"));
                sendKeysToElement(new String[]{levelInputs[3]}, data.get("Level_4"));
            } else if ("Time-Based".equalsIgnoreCase(targetType)) {
                clickElement(time_based_target_radio_locators);
                sleep(ACTION_PAUSE_MS);
                sendKeysToElement(start_value_input_locators, data.get("Start_Value"));
                sendKeysToElement(target_value_input_locators, data.get("Target_Value"));
            }
            
            clickElement(save_button_locators);
            sleep(PAGE_SETTLE_MS * 2);
            log("--- Target Configuration Complete ---");
        }
    }

    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
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